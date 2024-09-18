package com.mlorenzo.learning.jpa.jpain10steps.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mlorenzo.learning.jpa.jpain10steps.entity.User;

@Repository
@Transactional
public class UserDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Long insert(User user) {
		// Open transaction
		entityManager.persist(user);
		// Close transaction
		return user.getId();
	}

}
