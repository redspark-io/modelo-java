package io.redspark.controller;

import static io.redspark.controller.ControllerConstants.HOTEL;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.redspark.controller.dto.HotelDTO;
import io.redspark.domain.City;
import io.redspark.domain.Hotel;
import io.redspark.exception.NotFoundException;
import io.redspark.repository.CityRepository;
import io.redspark.repository.HotelRepository;
import io.redspark.security.Roles;
import io.redspark.utils.MapperUtils;
import io.redspark.utils.SQLLikeUtils;

@RestController
@RequestMapping(HOTEL)
@Secured(Roles.ROLE_ADMIN)
public class HotelController {

  private MapperUtils<Hotel, HotelDTO> convert = new MapperUtils<Hotel, HotelDTO>(Hotel.class, HotelDTO.class);

  @Autowired
  private HotelRepository repository;

  @Autowired
  private CityRepository cityRepository;

  @Transactional(readOnly = true)
  @GetMapping
  public Page<HotelDTO> list(@PageableDefault(page = 0, size = 50, sort = "name") Pageable page,
      @RequestParam(value = "search", required = false) String search) {

    Page<Hotel> result;
    if (StringUtils.hasText(search)) {
      result = repository.search(SQLLikeUtils.like(search), page);
    } else {
      result = repository.findAll(page);
    }

    return new PageImpl<HotelDTO>(result.getContent().stream().map(c -> convert.toDTO(c)).collect(Collectors.toList()),
        page, result.getTotalElements());
  }

  @Transactional(readOnly = true)
  @GetMapping(value = "/{ref}")
  public HotelDTO read(@PathVariable("ref") Long ref) {

    Hotel entity = repository.findByIdWithCity(ref);

    if (entity == null) {
      throw new NotFoundException(Hotel.class);
    }

    return convert.toDTO(entity);
  }

  @Transactional
  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public HotelDTO create(@Valid @RequestBody HotelDTO dto) {

    City city = cityRepository.findOne(dto.getCity().getId());
    if (city == null) {
      throw new NotFoundException(City.class);
    }

    Hotel entity = convert.toEntity(dto);
    entity.setCity(city);

    entity = repository.save(entity);
    return convert.toDTO(entity);
  }

  @Transactional
  @PutMapping(value = "/{ref}")
  public HotelDTO update(@PathVariable("ref") Long ref, @Valid @RequestBody HotelDTO dto) {

    City city = cityRepository.findOne(dto.getCity().getId());
    if (city == null) {
      throw new NotFoundException(City.class);
    }

    Hotel entity = repository.findOne(ref);
    if (entity == null) {
      throw new NotFoundException(Hotel.class);
    }

    convert.updateEntity(entity, dto, "id", "city");
    entity.setCity(city);

    entity = repository.save(entity);
    return convert.toDTO(entity);
  }

  @Transactional
  @DeleteMapping(value = "/{ref}")
  public HotelDTO delete(@PathVariable("ref") Long ref) {

    Hotel entity = repository.findOne(ref);
    if (entity == null) {
      throw new NotFoundException(Hotel.class);
    }

    this.repository.delete(entity);

    return convert.toDTO(entity);
  }
}
