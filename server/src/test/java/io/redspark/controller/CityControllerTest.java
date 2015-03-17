package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.city;
import static io.redspark.compose.Compose.hotel;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import io.redspark.ApplicationTest;
import io.redspark.controller.dto.CityDTO;
import io.redspark.domain.City;
import io.redspark.domain.Hotel;
import io.redspark.domain.User;
import io.redspark.repository.CityRepository;
import io.redspark.utils.MapperUtils;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CityControllerTest extends ApplicationTest {
    
    private MapperUtils<City, CityDTO> convert = new MapperUtils<City, CityDTO>(City.class, CityDTO.class);
    
    @Autowired
    private CityRepository cityRepository;
    
    @Test
    public void testList() {
	User bruno = admin("bruno").build();
	City c1 = city("B-city").build();
	City c2 = city("A-city").build();
	saveall(c1, c2, bruno);
	
	signIn(bruno);
	
	Page<CityDTO> page = get("/city").status(HttpStatus.OK).getPage(CityDTO.class);
	
	assertThat(page.getNumber(), equalTo(0));
	assertThat(page.getSize(), equalTo(50));
	assertThat(page.getContent(), contains(convert.toDTO(c2), convert.toDTO(c1)));
    }
    
    @Test
    public void testeListWithPagination() {
	User bruno = admin("bruno").build();
    	City c1 = city("A-city").build();
	City c2 = city("B-city").build();
	City c3 = city("C-city").build();
	City c4 = city("D-city").build();
	City c5 = city("E-city").build();
	saveall(c1, c2, c3, c4, c5, bruno);
	
	signIn(bruno);
	
	Page<CityDTO> page = get("/city").queryParam("page", "0").queryParam("size", "2").status(HttpStatus.OK).getPage(CityDTO.class);
	
	assertThat(page.getNumber(), equalTo(0));
	assertThat(page.getSize(), equalTo(2));
	assertThat(page.getTotalElements(), equalTo(5l));
	assertThat(page.getTotalPages(), equalTo(3));
	assertThat(page.getContent(), hasSize(2));
	assertThat(page.getContent(), contains(convert.toDTO(c1), convert.toDTO(c2)));
	
	page = get("/city").queryParam("size", "2").queryParam("page", "2").status(HttpStatus.OK).getPage(CityDTO.class);
	
	assertThat(page.getNumber(), equalTo(2));
	assertThat(page.getSize(), equalTo(2));
	assertThat(page.getTotalElements(), equalTo(5l));
	assertThat(page.getTotalPages(), equalTo(3));
	assertThat(page.getContent(), hasSize(1));
	assertThat(page.getContent(), contains(convert.toDTO(c5)));
    }
    
    @Test
    public void testSearchByName() {
	User bruno = admin("bruno").build();
	City c1 = city("São Paulo").build();
	City c2 = city("Rio de Janeiro").build();
	saveall(c1, c2, bruno);
	
	signIn(bruno);
	
	Page<CityDTO> page = get("/city").queryParam("search", "paulo").status(HttpStatus.OK).getPage(CityDTO.class);
	
	assertThat(page.getNumber(), equalTo(0));
	assertThat(page.getSize(), equalTo(50));
	assertThat(page.getTotalElements(), equalTo(1L));
	assertThat(page.getContent(), contains(convert.toDTO(c1)));
    }
    
    @Test
    public void testSearchByState() {
	User bruno = admin("bruno").build();
	City c1 = city("São Paulo").state("SP").build();
	City c2 = city("Rio de Janeiro").state("RJ").build();
	saveall(c1, c2, bruno);
	
	signIn(bruno);
	
	Page<CityDTO> page = get("/city").queryParam("search", "rj").status(HttpStatus.OK).getPage(CityDTO.class);
	
	assertThat(page.getNumber(), equalTo(0));
	assertThat(page.getSize(), equalTo(50));
	assertThat(page.getTotalElements(), equalTo(1L));
	assertThat(page.getContent(), contains(convert.toDTO(c2)));
    }
    
    @Test
    public void testSearchByCountry() {
	User bruno = admin("bruno").build();
	City c1 = city("São Paulo").country("Brazil").build();
	City c2 = city("Rio de Janeiro").country("USA").build();
	saveall(c1, c2, bruno);
	
	signIn(bruno);
	
	Page<CityDTO> page = get("/city").queryParam("search", "azil").status(HttpStatus.OK).getPage(CityDTO.class);
	
	assertThat(page.getNumber(), equalTo(0));
	assertThat(page.getSize(), equalTo(50));
	assertThat(page.getTotalElements(), equalTo(1L));
	assertThat(page.getContent(), contains(convert.toDTO(c1)));
    }
    
    @Test
    public void testRead() {
	User bruno = admin("bruno").build();
	City c1 = city("A-city").build();
	saveall(c1, bruno);
	
	signIn(bruno);
	
	ResponseEntity<CityDTO> response = get("/city/%s", c1.getId()).status(HttpStatus.OK).getResponse(CityDTO.class);

	assertThat(response.getBody(), equalTo(convert.toDTO(c1)));
    }
    
    @Test
    public void testCreate() throws JsonProcessingException, IOException {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	
	String name = "name";
	String state = "state";
	String country = "country";
	
	ResponseEntity<CityDTO> response = post("/city")
		.formParam("name", name)
		.formParam("state", state)
		.formParam("country", country)
		.status(HttpStatus.CREATED)
		.getResponse(CityDTO.class);
	
	assertThat(response.getBody().getId(), notNullValue());
	assertThat(response.getBody().getName(), equalTo(name));
	assertThat(response.getBody().getState(), equalTo(state));
	assertThat(response.getBody().getCountry(), equalTo(country));
	assertThat(cityRepository.findAll(), hasSize(1));
    }
    
    @Test
    public void testUpdate() {
	User bruno = admin("bruno").build();
	City c1 = city("A-city").build();
	saveall(c1, bruno);
	signIn(bruno);
	
	String name = "newname";
	String state = "newstate";
	String country = "newcountry";
	
	ResponseEntity<CityDTO> response = put("/city/%s", c1.getId())
		.formParam("name", name)
		.formParam("state", state)
		.formParam("country", country)
		.status(HttpStatus.OK)
		.getResponse(CityDTO.class);
	
	assertThat(response.getBody().getName(), equalTo(name));
	assertThat(response.getBody().getState(), equalTo(state));
	assertThat(response.getBody().getCountry(), equalTo(country));
	
	City c = cityRepository.findOne(c1.getId());
	
	assertThat(c.getName(), equalTo(name));
	assertThat(c.getState(), equalTo(state));
	assertThat(c.getCountry(), equalTo(country));
    }
    
    @Test
    public void testDelete() {
	User bruno = admin("bruno").build();
	City c1 = city("A-city").build();
	saveall(c1, bruno);
	signIn(bruno);
	
	assertThat(cityRepository.findAll(), hasSize(1));
	
	ResponseEntity<CityDTO> response = delete("/city/%s", c1.getId()).status(HttpStatus.OK).getResponse(CityDTO.class);
	
	assertThat(response.getBody().getId(), equalTo(c1.getId()));
	assertThat(cityRepository.findAll(), hasSize(0));
    }
    
    @Test
    public void testDeleteWithHotel() {
	User bruno = admin("bruno").build();
	City c1 = city("A-city").build();
	Hotel hotel = hotel("Days Inn", c1).build();
	saveall(c1, hotel, bruno);
	signIn(bruno);
	
	assertThat(cityRepository.findAll(), hasSize(1));
	
	delete("/city/%s", c1.getId()).status(HttpStatus.PRECONDITION_FAILED).getResponse();
    }
    
    @Test
    public void testReadNotFound() {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	get("/city/1").status(HttpStatus.NOT_FOUND).getResponse();
    }
    
    @Test
    public void testDeleteNotFound() {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	delete("/city/1").status(HttpStatus.NOT_FOUND).getResponse();
    }
    
    @Test
    public void testUpdateNotFound() {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	put("/city/1")
		.formParam("name", "name")
		.formParam("state", "state")
		.formParam("country", "country")
		.status(HttpStatus.NOT_FOUND)
		.getResponse();
    }
    
    @Test
    public void testeCreateInvalidParam() {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	
	post("/city")
		.status(HttpStatus.BAD_REQUEST)
		.getResponse();
    }
}
