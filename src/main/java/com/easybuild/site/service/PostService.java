package com.easybuild.site.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybuild.site.bean.Post;
import com.easybuild.site.repository.PostRepository;
import com.easybuild.site.repository.UserRepository;
import com.easybuild.site.utility.Utils;


@Service
public class PostService {

	public static final Logger logger = LogManager.getLogger(PostService.class);

	@Autowired
	private PostRepository postRepository;
	
	 @Autowired
	 private UserRepository userRepository;
	
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
	
	String username = null;
	

//	public Optional<Post> post(Post post, String email) {
//		logger.info("User attempting to post");
//		 Optional<User> user = userRepository.findByEmail(email);
//	//	username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//		return Optional.of(postRepository.save(new Post(post));
//	}
	
	public Optional<String> post(String title, String description, String originalFilename, String contentType,
			byte[] compressBytes, String email) {
		try {
			if (userRepository.findByEmail(email).isPresent()) {
				Optional.of(postRepository.save(new Post(title, description, originalFilename, contentType,
						compressBytes, userRepository.findByEmail(email).get(), Timestamp.valueOf(LocalDateTime.now()),
						Timestamp.valueOf(LocalDateTime.now()))));
				return Optional.of(Utils.generateJsonResponse("success", 200, "Email Sent", null));
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return Optional.empty();
	}

	public String filterToken(String token) {
		return token.replace("Bearer", "").trim();
	}

	public List<Post> getAllPosts() {
		logger.info("User attempting to retrieve all posts");
		List<Post> list = postRepository.findAll();
		List<Post> newList = new ArrayList<Post>();
		for(int x = 0; x<list.size(); x++) {
			Post post = new Post(list.get(x).getTitle(), 
					list.get(x).getDescription(), list.get(x).getFileName(), 
					list.get(x).getFileType(), decompressBytes(list.get(x).getPicByte()));
			newList.add(post);
		}
		return newList;
	}
	
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}


}
