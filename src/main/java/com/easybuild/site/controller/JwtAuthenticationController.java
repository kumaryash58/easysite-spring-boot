package com.easybuild.site.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.easybuild.site.bean.JwtResponse;
import com.easybuild.site.dto.UserDto;
import com.easybuild.site.service.UserService;

import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping("/userAuth")
public class JwtAuthenticationController {

	private static final Logger logger = LogManager.getLogger(JwtAuthenticationController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public JSONObject createAuthenticationToken(@RequestBody @Valid UserDto userDto) throws Exception {
		logger.info(userDto.getPassword());
		logger.info(userDto.getEmail());
		try {
			return userService.authenicate(userDto.getEmail(), userDto.getPassword());
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	//	return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@GetMapping(path = { "/forgotPasswerd/{email}" })
	public Optional<String> forgotPassword(@PathVariable("email") String email) {
		logger.info("User Forgot Passwerd {}", email);
		return userService.forgotPasswerd(email);
	}
}
