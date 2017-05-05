package io.redspark.controller;

import static io.redspark.controller.ControllerConstants.CITY;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.dto.CityDTO;
import io.redspark.domain.City;
import io.redspark.exception.NotFoundException;
import io.redspark.exception.WebException;
import io.redspark.repository.CityRepository;
import io.redspark.repository.HotelRepository;
import io.redspark.security.Roles;
import io.redspark.utils.MapperUtils;
import io.redspark.utils.SQLLikeUtils;

@RestController
@RequestMapping(CITY)
@Secured(Roles.ROLE_ADMIN)
public class CityController {

  private MapperUtils<City, CityDTO> convert = new MapperUtils<City, CityDTO>(City.class, CityDTO.class);

  @Autowired
  private CityRepository repository;

  @Autowired
  private HotelRepository hotelRepository;

  @Transactional(readOnly = true)
  @GetMapping
  public Page<CityDTO> list(@PageableDefault(page = 0, size = 50, sort = "name") Pageable page,
      @RequestParam(value = "search", required = false) String search) {

    Page<City> result;

    if (StringUtils.hasText(search)) {
      result = repository.search(SQLLikeUtils.like(search), page);
    } else {
      result = repository.findAll(page);
    }

    return new PageImpl<CityDTO>(result.getContent().stream().map(c -> convert.toDTO(c)).collect(Collectors.toList()),
        page, result.getTotalElements());
  }

  @Transactional(readOnly = true)
  @GetMapping(value = "/{id}")
  public CityDTO read(@PathVariable("id") Long id) {

    City city = repository.findOne(id);
    if (city == null) {
      throw new NotFoundException(City.class);
    }
    return convert.toDTO(city);
  }

  @Transactional
  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public CityDTO create(@Valid @RequestBody CityDTO dto) {

    City entity = convert.toEntity(dto);
    entity = repository.save(entity);
    return convert.toDTO(entity);
  }

  @Transactional
  @PutMapping(value = "/{ref}")
  @ResponseBody
  public CityDTO update(@PathVariable("ref") Long ref, @Valid @RequestBody CityDTO dto) {
    City entity = repository.findOne(ref);
    if (entity == null) {
      throw new NotFoundException(City.class);
    }
    convert.updateEntity(entity, dto, "id", "natures");
    entity = repository.save(entity);
    return convert.toDTO(entity);
  }

  @Transactional
  @DeleteMapping(value = "/{id}")
  public CityDTO delete(@PathVariable("id") Long id) {

    City city = repository.findOne(id);

    if (city == null) {
      throw new NotFoundException(City.class);
    }
    if (hotelRepository.countByCity(city) > 0) {
      throw new WebException(HttpStatus.PRECONDITION_FAILED, "city.hasHotel");
    }

    this.repository.delete(city);

    return convert.toDTO(city);
  }

}
