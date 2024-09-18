package com.mlorenzo.rest.webservices.restfullwebservices.user;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceJpaImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findOne(int id) {
		return userRepository.findById(id)
				.orElseThrow(() ->new UserNotFoundException(String.format("User with id %d not found", id)));
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public Post savePost(int userId, @Valid Post post) {
		User user = findOne(userId);
		post.setUser(user);
		postRepository.save(post);
		return post;
	}

	@Override
	public void deleteById(int id) {
		User user = findOne(id);
		userRepository.delete(user);
	}

}
