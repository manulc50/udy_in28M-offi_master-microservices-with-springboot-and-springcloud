package com.mlorenzo.rest.webservices.restfullwebservices.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

@Validated // Para que funcione la anotación "@Valid"
public interface UserService {

	List<User> findAll();
	User findOne(int id);
	User save(@Valid User user);
	Post savePost(int userId, @Valid Post post);
	void deleteById(int id);
}
