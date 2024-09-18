package com.mlorenzo.learning.jpa.jpain10steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mlorenzo.learning.jpa.jpain10steps.entity.User;
import com.mlorenzo.learning.jpa.jpain10steps.repository.UserDAO;

@Component
public class UserDAOCommandLineRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(UserDAOCommandLineRunner.class);
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public void run(String... args) throws Exception {
		User user = new User("Jack", "Admin");
		userDAO.insert(user);
		log.info("New user is created: {}", user);
	}

}
