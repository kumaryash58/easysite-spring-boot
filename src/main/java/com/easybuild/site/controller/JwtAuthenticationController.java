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

import com.easybuild.site.dto.Response;
import com.easybuild.site.dto.UserDto;
import com.easybuild.site.entity.JwtResponse;
import com.easybuild.site.service.UserService;


import com.easybuild.site.constants.Code;
import com.easybuild.site.constants.Constant;
import com.easybuild.site.constants.Message;

import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@RequestMapping("/userAuth")
public class JwtAuthenticationController {

	private static final Logger logger = LogManager.getLogger(JwtAuthenticationController.class);

	@Autowired
	private UserService userService;
	
	//@Autowired
	private Response response;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public Response createAuthenticationToken(@RequestBody @Valid UserDto userDto) throws Exception {
		logger.info(userDto.getEmail());
		response = new Response();
		try {
			JSONObject responseDetails = userService.authenicate(userDto.getEmail(), userDto.getPassword());
			if (responseDetails != null && !responseDetails.isEmpty()) {
				response.setCode(Code.SUCCESS);
				response.setStatus(Constant.STATUS_SUCCESS);
				response.setMessage(Message.ACCEPTED);
				response.setData(responseDetails);
			} else {
				response.setCode(Code.BAD_REQUEST);
				response.setStatus(Constant.STATUS_FAILURE);
				response.setMessage(Message.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return response;
		// return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@GetMapping(path = { "/forgotPasswerd/{email}" })
	public Optional<String> forgotPassword(@PathVariable("email") String email) {
		logger.info("User Forgot Passwerd {}", email);
		return userService.forgotPasswerd(email);
	}
	
	@RequestMapping(value = "/loginWithGoogle", method = RequestMethod.POST)
	public Response loginWithGoogle(@RequestBody @Valid UserDto userDto) throws Exception {
		logger.info(userDto.getEmail());
		response = new Response();
		try {
			JSONObject responseDetails = userService.authenicate(userDto.getEmail(), userDto.getPassword());
			if (responseDetails != null && !responseDetails.isEmpty()) {
				response.setCode(Code.SUCCESS);
				response.setStatus(Constant.STATUS_SUCCESS);
				response.setMessage(Message.ACCEPTED);
				response.setData(responseDetails);
			} else {
				response.setCode(Code.BAD_REQUEST);
				response.setStatus(Constant.STATUS_FAILURE);
				response.setMessage(Message.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return response;
		// return ResponseEntity.ok(new JwtResponse(token));
	}
}
