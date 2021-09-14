package com.demchenko.restservice.controller;

import com.demchenko.restservice.exception.UserNotFoundException;
import com.demchenko.restservice.model.User;
import com.demchenko.restservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUserById(@PathVariable Integer id) {
        WebMvcLinkBuilder linksToUsers = linkTo(methodOn(this.getClass()).getAllUsers());

        return userService.findById(id)
                .map(EntityModel::of)
                .stream().peek(model -> model.add(linksToUsers.withRel("all-users")))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userService.deleteById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }
}
