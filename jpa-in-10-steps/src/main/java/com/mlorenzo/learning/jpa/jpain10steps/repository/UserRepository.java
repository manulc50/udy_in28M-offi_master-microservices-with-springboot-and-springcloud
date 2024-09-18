package com.mlorenzo.learning.jpa.jpain10steps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlorenzo.learning.jpa.jpain10steps.entity.User;

// Usando Spring Data JPA

public interface UserRepository extends JpaRepository<User, Long> {

}
