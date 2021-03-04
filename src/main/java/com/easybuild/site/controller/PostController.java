package com.easybuild.site.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.easybuild.site.bean.Post;
import com.easybuild.site.dto.PostDto;
import com.easybuild.site.dto.UserDto;
import com.easybuild.site.service.PostService;
import com.easybuild.site.utility.Utils;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

//	@PostMapping("/upload")
//	 @ResponseStatus(HttpStatus.CREATED)
//	public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file, PostDto postDto)
//			throws IOException {
//		LOGGER.info("Original Image Byte Size - {}", postDto.getTitle());
//		Post post = new Post(postDto.getTitle(), postDto.getDescription(), file.getOriginalFilename(),
//				file.getContentType(), compressBytes(file.getBytes()));
//		return	null;//postService.post(post, userDto.getEmail()).orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"User already exists"));
//		//return ResponseEntity.status(HttpStatus.OK);
//	}
	
	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.CREATED)
	public String uplaodImage(@RequestParam("imageFile") MultipartFile file, PostDto postDto, UserDto userDto)
			throws IOException {
		LOGGER.info("Original Image Byte Size - {}", postDto.getTitle(), userDto.getEmail());
//		Post post = new Post(postDto.getTitle(), postDto.getDescription(), file.getOriginalFilename(),
//				file.getContentType(), compressBytes(file.getBytes()));
		return postService
				.post(postDto.getTitle(), postDto.getDescription(), file.getOriginalFilename(), file.getContentType(),
						Utils.compressBytes(file.getBytes()), userDto.getEmail())
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
		// return ResponseEntity.status(HttpStatus.OK);
	}
	
	@GetMapping(path = { "/getAllPosts" })
	public List<Post> getAllPosts() {
		LOGGER.info("GET /post");
		return postService.getAllPosts();
	}


}
