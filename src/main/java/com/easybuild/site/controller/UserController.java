package com.easybuild.site.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.easybuild.site.constants.Code;
import com.easybuild.site.constants.Constant;
import com.easybuild.site.constants.Message;
import com.easybuild.site.dto.Response;
import com.easybuild.site.dto.UserDto;
import com.easybuild.site.entity.User;
import com.easybuild.site.service.UserService;
import com.easybuild.site.utility.Utils;

import net.minidev.json.JSONObject;

import java.io.IOException;
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
	
	private Response response;

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public User signup(@RequestBody @Valid UserDto userDto) {
		logger.info("POST /users/signup");
		return userService.signup(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword(), userDto.getGender(),
				userDto.getCountry(), userDto.getCity())
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
	}
	
	@PostMapping("/updateAdminProfile")
	@ResponseStatus(HttpStatus.CREATED)
	public Response updateProfile(@RequestBody UserDto userDto) {
		logger.info("POST /users/updateProfile");
		try {
			response = new Response();
			JSONObject responseDetails = userService.updateProfile(userDto.getFirstName(), userDto.getLastName(), userDto.getId(), userDto.getMobileNo(), userDto.getAddress(),
					userDto.getCountry(), userDto.getCity());
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
		} catch (HttpServerErrorException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return response;
	}

	@GetMapping
	public List<User> getAllUsers() {
		logger.info("GET /users");
		return userService.getAll();
	}

	@GetMapping(path = { "/adminDetails/{email}" })
	public Optional<User> getAdminDetail(@PathVariable("email") String email) {
		logger.info("GET /users");
		return userService.getAdminDetail(email);
	}
	
	@PostMapping("/updateImage/{email}/{isProfilePic}")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateImage(@RequestParam("imageFile") MultipartFile file, 
			@PathVariable("email") String email, 
			@PathVariable("isProfilePic") String isProfilePic) {
		logger.info("POST /users//updateImage/{email}");
		try {
			logger.info("Original Image Byte Size - {}", file.getBytes().length);
			return userService.updateProfileImage(email, isProfilePic, file.getOriginalFilename(), file.getContentType(),
					file.getBytes());
		} catch (HttpServerErrorException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}

}
