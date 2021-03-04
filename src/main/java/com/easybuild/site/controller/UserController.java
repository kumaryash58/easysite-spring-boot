package com.easybuild.site.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import com.easybuild.site.bean.User;
import com.easybuild.site.dto.UserDto;
import com.easybuild.site.service.UserService;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

	public static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public User signup(@RequestBody @Valid UserDto userDto) {
		logger.info("POST /users/signup");
		return userService.signup(userDto.getUsername(), userDto.getEmail(), userDto.getPassword(), userDto.getGender())
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
	}

	@GetMapping
	public List<User> getAllUsers() {
		logger.info("GET /users");
		return userService.getAll();
	}

	@GetMapping(path = "/{email}")
	public Optional<User> getAllUsers(@RequestBody @Valid UserDto userDto) {
		logger.info("GET /users");
		return userService.getAdminDetail(userDto.getEmail());
	}

}
