package com.mlorenzo.rest.webservices.restfullwebservices.user;

import java.net.URI;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {
	
	// Nota: En vez de usar la anotación "@Qualifier", otra opción es indicar la implementación de la capa de servicio que queremos usar mediante el nombre de la propiedad donde se inyecta el bean de Spring
	//       Para ello, el nombre de esa propiedad debe ser exactamente el nombre del bean de Spring de la implementación que queremos usar
	
	@Qualifier("userServiceJpaImpl")
	@Autowired
	private UserService service;
	
	// GET
	// URI - /users
	@GetMapping("/users") // Versión simplificada de la expresión "@RequestMapping(method = RequestMethod.GET, path = "/users")"
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
	// GET
	// URI - /users/{id}
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable(value = "id") int userId) {
		
		EntityModel<User> model = EntityModel.of(service.findOne(userId));
		
		// Creamos un enlace al método "retrieveAllUsers"(URI - /users) de esta clase
		WebMvcLinkBuilder linktoUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		model.add(linktoUsers.withRel("all-users"));
		
		return model;
	}
	
	// GET
	// URI - /users/{id}/posts
	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveUserPosts(@PathVariable(value = "id") int userId) { 
		User user = service.findOne(userId);
		return user.getPosts();
	}
	
	// POST
	// URI - /users
	@PostMapping("/users") // Versión simplificada de la expresión "@RequestMapping(method = RequestMethod.POST, path = "/users")"
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = service.save(user);
		
		// Uri con la localización del nuevo recurso User creado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		// Crea la cabecera "Location" con la uri anterior como valor
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, location.toString());
		
		return new ResponseEntity<User>(savedUser, headers, HttpStatus.CREATED);
	}
	
	// POST
	// URI - /users/{id}/posts
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Void> createUserPost(@PathVariable(value = "id") int userId, @RequestBody Post post) {
		Post postSaved = service.savePost(userId, post);
		
		// Uri con la localización del nuevo recurso Post creado
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(postSaved.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	// DELETE
	// URI - /users/{id}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/users/{id}")  // Versión simplificada de la expresión @RequestMapping(method = RequestMethod.DELETE, path = "/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}

}
