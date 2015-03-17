package io.redspark.repository;

import io.redspark.domain.City;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Long> {

	@Query("select c from City c where UPPER(c.name) like UPPER(?1) or UPPER(c.state) like UPPER(?1) or UPPER(c.country) like UPPER(?1)")
	Page<City> search(String value, Pageable page);

}
