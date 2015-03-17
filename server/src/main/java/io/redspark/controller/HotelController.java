package io.redspark.controller;

import io.redspark.controller.dto.HotelDTO;
import io.redspark.domain.City;
import io.redspark.domain.Hotel;
import io.redspark.exception.NotFoundException;
import io.redspark.repository.CityRepository;
import io.redspark.repository.HotelRepository;
import io.redspark.security.Roles;
import io.redspark.utils.MapperUtils;
import io.redspark.utils.SQLLikeUtils;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/hotel")
@Secured(Roles.ROLE_ADMIN)
public class HotelController {

    private MapperUtils<Hotel, HotelDTO> convert = new MapperUtils<Hotel, HotelDTO>(Hotel.class, HotelDTO.class);
    
    @Autowired
    private HotelRepository repository;
    
    @Autowired
    private CityRepository cityRepository;
    
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Page<HotelDTO> list(@PageableDefault(page = 0, size = 50, sort = "name") Pageable page,
	    @RequestParam(value = "search", required = false) String search) {
	Page<Hotel> result;
	if (StringUtils.hasText(search)) {
	    result = repository.search(SQLLikeUtils.like(search), page);
	} else {
	    result = repository.findAll(page);
	}

	return new PageImpl<HotelDTO>(result.getContent().stream().map(c -> convert.toDTO(c))
		.collect(Collectors.toList()), page, result.getTotalElements());
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/{ref}", method = RequestMethod.GET)
    @ResponseBody
    public HotelDTO read(@PathVariable("ref") Long ref) {
	Hotel entity = repository.findByIdWithCity(ref);
	if (entity == null) {
	    throw new NotFoundException(Hotel.class);
	}
	return convert.toDTO(entity);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public HotelDTO create(@Valid @ModelAttribute("hotel") HotelDTO dto) {
	
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
    @RequestMapping(value = "/{ref}", method = RequestMethod.PUT)
    @ResponseBody
    public HotelDTO update(@PathVariable("ref") Long ref, 
    	@Valid @ModelAttribute("hotel") HotelDTO dto) {
	
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
    @RequestMapping(value = "/{ref}", method = RequestMethod.DELETE)
    @ResponseBody
    public HotelDTO delete(@PathVariable("ref") Long ref) {
	Hotel entity = repository.findOne(ref);
	if (entity == null) {
	    throw new NotFoundException(Hotel.class);
	}
	
	this.repository.delete(entity);
	return convert.toDTO(entity);
    }
}
