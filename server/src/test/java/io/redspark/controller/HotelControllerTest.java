package io.redspark.controller;

import static io.redspark.compose.Compose.admin;
import static io.redspark.compose.Compose.city;
import static io.redspark.compose.Compose.hotel;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import io.redspark.ApplicationTest;
import io.redspark.controller.dto.HotelDTO;
import io.redspark.domain.City;
import io.redspark.domain.Hotel;
import io.redspark.domain.User;
import io.redspark.repository.CityRepository;
import io.redspark.repository.HotelRepository;
import io.redspark.utils.MapperUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HotelControllerTest extends ApplicationTest {
    
    private MapperUtils<Hotel, HotelDTO> convert = new MapperUtils<Hotel, HotelDTO>(Hotel.class, HotelDTO.class);
    
    @Autowired
    private HotelRepository repository;
    
    @Autowired
    private CityRepository cityRepository;
    
    @Test
    public void testList() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c1 = hotel("Macbook", cidade).build();
	Hotel c2 = hotel("IPhone", cidade).build();
	saveall(cidade, c1, c2, bruno);
	
	signIn(bruno);
	
	Page<HotelDTO> page = get("/hotel").status(HttpStatus.OK).getPage(HotelDTO.class);
	
	assertThat(page.getNumber(), equalTo(0));
	assertThat(page.getSize(), equalTo(50));
	assertThat(page.getContent(), contains(convert.toDTO(c2), convert.toDTO(c1)));
    }
    
    @Test
    public void testeListWithPagination() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c1 = hotel("A-Hotel", cidade).build();
	Hotel c2 = hotel("B-Hotel", cidade).build();
	Hotel c3 = hotel("C-Hotel", cidade).build();
	Hotel c4 = hotel("D-Hotel", cidade).build();
	Hotel c5 = hotel("E-Hotel", cidade).build();
	saveall(cidade, c1, c2, c3, c4, c5, bruno);
	
	signIn(bruno);
	
	Page<HotelDTO> page = get("/hotel").queryParam("page", "0").queryParam("size", "2").status(HttpStatus.OK).getPage(HotelDTO.class);
	
	assertThat(page.getNumber(), equalTo(0));
	assertThat(page.getSize(), equalTo(2));
	assertThat(page.getTotalElements(), equalTo(5l));
	assertThat(page.getTotalPages(), equalTo(3));
	assertThat(page.getContent(), hasSize(2));
	assertThat(page.getContent(), contains(convert.toDTO(c1), convert.toDTO(c2)));
	
	page = get("/hotel").queryParam("size", "2").queryParam("page", "2").status(HttpStatus.OK).getPage(HotelDTO.class);
	
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
	City cidade = city("São Paulo").build();
	Hotel c1 = hotel("Days Inn", cidade).build();
	Hotel c2 = hotel("Hilton", cidade).build();
	saveall(cidade, c1, c2, bruno);
	
	signIn(bruno);
	
	Page<HotelDTO> page = get("/hotel").queryParam("search", "inn").status(HttpStatus.OK).getPage(HotelDTO.class);
	
	assertThat(page.getTotalElements(), equalTo(1L));
	assertThat(page.getContent(), contains(convert.toDTO(c1)));
    }
    
    @Test
    public void testSearchByAddress() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c1 = hotel("Days Inn", cidade).address("2512 Orange Blossom").build();
	Hotel c2 = hotel("Hilton", cidade).build();
	saveall(cidade, c1, c2, bruno);
	
	signIn(bruno);
	
	Page<HotelDTO> page = get("/hotel").queryParam("search", "orange").status(HttpStatus.OK).getPage(HotelDTO.class);
	
	assertThat(page.getTotalElements(), equalTo(1L));
	assertThat(page.getContent(), contains(convert.toDTO(c1)));
    }
    
    @Test
    public void testSearchByZip() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c1 = hotel("Days Inn", cidade).build();
	Hotel c2 = hotel("Hilton", cidade).zip("07105").build();
	saveall(cidade, c1, c2, bruno);
	
	signIn(bruno);
	
	Page<HotelDTO> page = get("/hotel").queryParam("search", "07105").status(HttpStatus.OK).getPage(HotelDTO.class);
	
	assertThat(page.getTotalElements(), equalTo(1L));
	assertThat(page.getContent(), contains(convert.toDTO(c2)));
    }
    
    @Test
    public void testSearchByCityName() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c1 = hotel("Days Inn", cidade).build();
	Hotel c2 = hotel("Hilton", cidade).build();
	saveall(cidade, c1, c2, bruno);
	
	signIn(bruno);
	
	Page<HotelDTO> page = get("/hotel").queryParam("search", "paulo").status(HttpStatus.OK).getPage(HotelDTO.class);
	
	assertThat(page.getTotalElements(), equalTo(2L));
	assertThat(page.getContent(), contains(convert.toDTO(c1), convert.toDTO(c2)));
    }
    
    @Test
    public void testRead() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c1 = hotel("Days Inn", cidade).build();
	saveall(cidade, c1, bruno);
	
	signIn(bruno);
	
	ResponseEntity<HotelDTO> response = get("/hotel/"+c1.getId()).status(HttpStatus.OK).getResponse(HotelDTO.class);
	HotelDTO dto = convert.toDTO(c1);
	assertThat(response.getBody(), equalTo(dto));
	assertThat(response.getBody().getCity(), equalTo(dto.getCity()));
    }
    
    @Test
    public void testCreate(){
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	saveall(bruno, cidade);
	signIn(bruno);
	
	Hotel c = hotel("Days Inn", cidade).build();
	
	ResponseEntity<HotelDTO> response = post("/hotel")
		.formParam("name", c.getName())
		.formParam("address", c.getAddress())
		.formParam("zip", c.getZip())
		.formParam("city.id", cidade.getId())
		.formParam("city.name", cidade.getName())
		.formParam("city.state", cidade.getState())
		.formParam("city.country", cidade.getCountry())
		.status(HttpStatus.CREATED)
		.getResponse(HotelDTO.class);
	
	c.setId(response.getBody().getId());
	HotelDTO dto = convert.toDTO(c);
	assertThat(response.getBody(), equalTo(dto));
	assertThat(response.getBody().getCity(), equalTo(dto.getCity()));
	assertThat(repository.findAll(), hasSize(1));
    }
    
    @Test
    public void testCreateNotPersistyCity(){
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	saveall(bruno, cidade);
	signIn(bruno);
	
	Hotel c = hotel("Days Inn", cidade).build();
	
	ResponseEntity<HotelDTO> response = post("/hotel")
		.formParam("name", c.getName())
		.formParam("address", c.getAddress())
		.formParam("zip", c.getZip())
		.formParam("city.id", cidade.getId())
		.formParam("city.name", "newname")
		.formParam("city.state", cidade.getState())
		.formParam("city.country", cidade.getCountry())
		.status(HttpStatus.CREATED)
		.getResponse(HotelDTO.class);
	
	assertThat(response.getBody().getCity().getName(), not("newname"));
	assertThat(cityRepository.findOne(cidade.getId()).getName(), not("newname"));
    }
    
    @Test
    public void testCreateInvalidCity(){
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	saveall(bruno, cidade);
	signIn(bruno);
	
	Hotel c = hotel("Days Inn", cidade).build();
	
	post("/hotel")
		.formParam("name", c.getName())
		.formParam("address", c.getAddress())
		.formParam("zip", c.getZip())
		.formParam("city.id", 9999)
		.formParam("city.name", "newname")
		.formParam("city.state", cidade.getState())
		.formParam("city.country", cidade.getCountry())
		.status(HttpStatus.NOT_FOUND)
		.getResponse();
	
	assertThat(repository.findAll(), hasSize(0));
	
    }
    
    @Test
    public void testUpdate() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c = hotel("Days Inn", cidade).build();
	saveall(cidade, c, bruno);
	signIn(bruno);
	
	String name = "newname";
	
	ResponseEntity<HotelDTO> response = put("/hotel/%s", c.getId())
		.formParam("name", name)
		.formParam("address", c.getAddress())
		.formParam("zip", c.getZip())
		.formParam("city.id", cidade.getId())
		.formParam("city.name", "teste")
		.formParam("city.state", cidade.getState())
		.formParam("city.country", cidade.getCountry())
		.status(HttpStatus.OK)
		.getResponse(HotelDTO.class);
	
	assertThat(response.getBody().getName(), equalTo(name));
	assertThat(response.getBody().getCity().getName(), not("teste"));
	
	Hotel entity = repository.findByIdWithCity(c.getId());
	
	assertThat(entity.getName(), equalTo(name));
	assertThat(entity.getCity().getName(), not("teste"));
    }
    
    @Test
    public void testUpdateWithInvalidCity() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c = hotel("Days Inn", cidade).build();
	saveall(cidade, c, bruno);
	signIn(bruno);
	
	String name = "newname";
	
	put("/hotel/%s", c.getId())
		.formParam("name", name)
		.formParam("address", c.getAddress())
		.formParam("zip", c.getZip())
		.formParam("city.id", 9999)
		.formParam("city.name", "teste")
		.formParam("city.state", cidade.getState())
		.formParam("city.country", cidade.getCountry())
		.status(HttpStatus.NOT_FOUND)
		.getResponse();
	
	Hotel entity = repository.findByIdWithCity(c.getId());
	assertThat(entity.getName(), not(name));
	assertThat(entity.getCity().getName(), not("teste"));
    }
    
    @Test
    public void testDelete() {
	User bruno = admin("bruno").build();
	City cidade = city("São Paulo").build();
	Hotel c1 = hotel("Days Inn", cidade).build();
	saveall(cidade, c1, bruno);
	signIn(bruno);
	
	assertThat(repository.findAll(), hasSize(1));
	
	ResponseEntity<HotelDTO> response = delete("/hotel/%s", c1.getId()).status(HttpStatus.OK).getResponse(HotelDTO.class);
	
	assertThat(response.getBody().getId(), equalTo(c1.getId()));
	assertThat(repository.findAll(), hasSize(0));
	assertThat(cityRepository.findAll(), hasSize(1));
    }
    
    @Test
    public void testReadNotFound() {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	get("/hotel/1").status(HttpStatus.NOT_FOUND).getResponse();
    }
    
    @Test
    public void testDeleteNotFound() {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	delete("/hotel/1").status(HttpStatus.NOT_FOUND).getResponse();
    }
    
    @Test
    public void testUpdateNotFound() {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	put("/hotel/1")
		.formParam("name", "name")
		.status(HttpStatus.NOT_FOUND);
    }
    
    @Test
    public void testeCreateInvalidParam() {
	User bruno = admin("bruno").build();
	saveall(bruno);
	signIn(bruno);
	
	post("/hotel")
		.status(HttpStatus.BAD_REQUEST)
		.getResponse();
    }
}
