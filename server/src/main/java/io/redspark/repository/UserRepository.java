package io.redspark.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.redspark.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByLogin(String login);

  @Query("select u from User u where UPPER(u.login) like UPPER(?1) or UPPER(u.name) like UPPER(?1)")
	Page<User> search(String like, Pageable page);

}
