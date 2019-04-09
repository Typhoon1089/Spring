package com.typhoon.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource; // available in 2.0.0 Release if 2.2.0, use other library
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJpaResource {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/jpa/users")
  public List<User> retrieveAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/jpa/users/{id}")
  public Resource<User> retrieveUser(@PathVariable int id) {
    // Optional means that user could be null
    Optional<User> user = userRepository.findById(id);

    // if user is not null
    if (!user.isPresent())
      throw new UserNotFoundException("id-" + id);

    Resource<User> resource = new Resource<User>(user.get());
    ControllerLinkBuilder linkTo = 
        linkTo(methodOn(this.getClass()).retrieveAllUsers());
    resource.add(linkTo.withRel("all-user"));
    
    return resource;
  }

  @DeleteMapping("/jpa/users/{id}")
  public void deleteUser(@PathVariable int id) {
    userRepository.deleteById(id);
  }

  @PostMapping("/jpa/users")
  public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
  }
  
  @GetMapping("/jpa/users/{id}/posts")
  public List<Post> retrieveAllUsers(@PathVariable int id) {
    Optional<User> userOptional = userRepository.findById(id);

    if (!userOptional.isPresent()) {
      throw new UserNotFoundException("id-"+id);
    }

    // getPosts refers to the function getPosts in class User
    // to return to json without User information of each Post => add JsonIgnore
    return userOptional.get().getPosts();
  }
}	
