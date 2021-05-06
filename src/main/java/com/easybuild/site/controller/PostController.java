package com.easybuild.site.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.easybuild.site.constants.Code;
import com.easybuild.site.constants.Constant;
import com.easybuild.site.constants.Message;
import com.easybuild.site.dto.PostDto;
import com.easybuild.site.dto.Response;
import com.easybuild.site.dto.TemplateDto;
import com.easybuild.site.entity.Post;
import com.easybuild.site.entity.Templates;
import com.easybuild.site.service.PostService;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

	public static final Logger logger = LogManager.getLogger(PostController.class);

	@Autowired
	private PostService postService;
	
	private Response response;

	@PostMapping("/upload/{email}")
	@ResponseStatus(HttpStatus.CREATED)
	public String post(@RequestBody PostDto postDto, @PathVariable("email") String email)
			throws IOException {
		return postService
				.post(postDto.getTitle(), postDto.getDescription(), postDto.getPostBody(), email)
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Post already exists"));
	}
	
	@PostMapping("/updatePost/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public String updatePost(@RequestBody PostDto postDto, @PathVariable("id") String id)
			throws IOException {
		return postService
				.updatePost(postDto.getTitle(), postDto.getDescription(), postDto.getPostBody(), id)
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Error"));
	}
	
	@GetMapping(path = { "/getAllPosts/{email}" })
	public List<Post> getAllPosts(@PathVariable("email") String email) {
		logger.info("GET /post/getAllPosts");
		return postService.getAllPosts(email);
	}
	
	@GetMapping(path = { "/getPostDetail/{id}" })
	public Optional<Post> getPostDetail(@PathVariable("id") String id) {
		logger.info("GET /post/getPostDetail");
		return postService.getPostDetailById(id);
	}
	
	@PostMapping("/updatePostImage/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateImage(@RequestParam("imageFile") MultipartFile file, 
			@PathVariable("id") String id) {
		logger.info("POST post/updatePostImage/{id}");
		try {
			logger.info("Original Image Byte Size - {}", file.getBytes().length);
			return postService.updatePostImage(Long.parseLong(id), file.getOriginalFilename(), file.getContentType(),
					file.getBytes());
		} catch (HttpServerErrorException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}
	
	@PostMapping("/createTemplate")
	@ResponseStatus(HttpStatus.CREATED)
	public String post(@RequestParam("imageFile") MultipartFile file, TemplateDto templateDto)
			throws IOException {
		return postService
				.createTemplate(templateDto.getTempName(), templateDto.getTempStructure(),
						file.getOriginalFilename(), file.getContentType(), file.getBytes())
				.orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Template already exists"));
	}
	
	@GetMapping(path = { "/getAllTemplates" })
	public List<Templates> getAllTemplates() {
		logger.info("GET /post/getAllTemplates");
		return postService.getAllTemplates();
	}
	
	@GetMapping(path = { "/getTemplateDetails/{id}" })
	public Optional<Templates> getTemplateDetails(@PathVariable("id") String id) {
		logger.info("GET /post/getTemplateDetails");
		return postService.getTemplateDetails(id);
	}
	
	@PostMapping(path = { "/deletePost/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public Response deletePost(@PathVariable("id") String id) throws Exception {
		logger.info("GET /post/deletePost");
		postService.deletePost(id);
		response = new Response();
		response.setCode(Code.SUCCESS);
		response.setStatus(Constant.STATUS_SUCCESS);
		response.setMessage(Message.ACCEPTED);
		return response;
	}
	
	

}
