package com.mlorenzo.rest.webservices.restfullwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;



@Service
public class UserServiceListImpl implements UserService {
	
	private static List<User> users;
	private static int usersCount;
	
	static {
		users = new ArrayList<User>();
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Jack", new Date()));
		
		usersCount = 3;
	}
	
	@Override
	public List<User> findAll() {
		return users;
	}
	
	@Override
	public User findOne(int id) {
		return users.stream().filter(u -> u.getId() == id).findFirst() // Otra opción equivalente es usar el método "findAny"
				.orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", id)));
	}
	
	@Override
	public User save(User user) {
		if(user.getId() == null)
			user.setId(++usersCount);
		
		users.add(user);
		return user;
	}
	
	@Override
	public Post savePost(int userId, @Valid Post post) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void deleteById(int id) {
		users.stream().filter(u -> u.getId() == id).findAny() // Otra opción equivalente es usar el método "findFirst"
		.ifPresentOrElse(users::remove, // Versión simplificada de la expresión "u -> users.remove(u)"
					() -> {throw new UserNotFoundException(String.format("User with id %d not found", id));});

	}

}
