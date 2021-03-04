package com.easybuild.site.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.easybuild.site.bean.User;
import com.easybuild.site.repository.GenderRepository;
import com.easybuild.site.repository.RoleRepository;
import com.easybuild.site.repository.UserRepository;
import com.easybuild.site.security.JwtTokenUtil;
import com.easybuild.site.utility.CreateGoogleFile;
import com.easybuild.site.utility.Utils;
import com.google.api.services.drive.model.File;

import net.minidev.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;


@Service
public class UserService {

	public static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private GenderRepository genderRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * Sign in a user into the application, with JWT-enabled authentication
	 *
	 * @param username username
	 * @param password password
	 * @return Optional of the Java Web Token, empty otherwise
	 */
	public JSONObject authenicate(String email, String password) {
		logger.info("User Authenticating...");
		Optional<String> token = Optional.empty();
		Optional<User> user = userRepository.findByEmail(email);
		JSONObject userProfileDetails = new JSONObject();
		if (user.isPresent()) {
			try {
				// authenticationManager.authenticate(new
				// UsernamePasswordAuthenticationToken(email, password));
				token = Optional.of(jwtTokenUtil.createToken(email));
				userProfileDetails.put("token", token);
				userProfileDetails.put("username", user.get().getUsername());
				userProfileDetails.put("gender", user.get().getGender().getGenderName());
			} catch (AuthenticationException e) {
				logger.log(Level.ERROR, e.getMessage(), e);
			}
		}
		return userProfileDetails;
	}

	/**
	 * Create a new user in the database.
	 *
	 * @param username username
	 * @param password password
	 * @return Optional of user, empty if the user already exists.
	 */
	public Optional<User> signup(String username, String email, String password, String gender) {
		logger.info("Creating New User Account");
		String roleName = "ADMIN";
		Optional<User> user = Optional.empty();
		if (!userRepository.findByEmail(email).isPresent()) {
			try {
				File folder = Utils.createUserFolderInDrive(email, username);
				String userFolderName = folder.getName();
				String folderParentId = folder.getId();
				String profileImgPath = Utils.getDefaultProfilePathByGender(gender);
//				String contentType = Utils.getContentTypeByFileName(profileImgPath);
//
//				String ext = profileImgPath.substring(profileImgPath.lastIndexOf("."));
//				String newFileName = username + "_" + (Timestamp.valueOf(LocalDateTime.now())).toString() + ext;
//				BufferedImage bImage = ImageIO.read(new java.io.File(profileImgPath));
//				ByteArrayOutputStream bos = new ByteArrayOutputStream();
//				ImageIO.write(bImage, ext, bos);
//				byte[] data = bos.toByteArray();
//				File googleFile = CreateGoogleFile.createGoogleFile(folderParentId, contentType, newFileName,
//						Utils.compressBytes(data));
//				String profilePic = googleFile.getWebViewLink();

				user = Optional.of(userRepository.save(new User(username, email, password, 
						genderRepository.findByGenderName(gender).get(),
						roleRepository.findByRoleName(roleName).get(), userFolderName, folderParentId, profileImgPath,
						Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()))));
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getMessage(), e);
			}
		}
		return user;
	}
	
	/**
	 * Create a new user in the database.
	 *
	 * @param username username
	 * @param password password
	 * @return Optional of user, empty if the user already exists.
	 */
	public Optional<User> updateProfile(String username, String email, String mobileNo, String gender,
			String originalFilename, String contentType, byte[] compressBytes) {
		logger.info("Creating New User Account");
		String roleName = "ADMIN";
		Optional<User> user = Optional.empty();
		if (!userRepository.findByEmail(email).isPresent()) {
			try {
				User userDetail = userRepository.findByEmail(email).get();
				String folderParentId = userDetail.getFolderParentId();
				if(folderParentId == null || folderParentId.isEmpty()) {
					File folder = Utils.createUserFolderInDrive(email, userRepository.findByEmail(email).get().getUsername());
					String userFolderName = folder.getName();
					folderParentId = folder.getId();
					userDetail.setFolder(userFolderName);
					userDetail.setFolderParentId(folderParentId);
					userDetail.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
					userDetail = userRepository.save(userDetail);
				}
				contentType = Utils.getContentTypeByFileName(originalFilename);
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				String newFileName = userDetail.getUsername() + "_" + (Timestamp.valueOf(LocalDateTime.now())).toString() + ext;
				File googleFile = CreateGoogleFile.createGoogleFile(folderParentId, contentType, newFileName, compressBytes);
				String profilePic = googleFile.getWebViewLink();
				user = Optional.of(userRepository.save(new User(username, email,
						roleRepository.findByRoleName(roleName).get(),  mobileNo,  
						genderRepository.findByGenderName(gender).get(),
						profilePic,
						Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()))));
			
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getMessage(), e);
			}
		}
		return user;
	}

	public UserDetails loadUserByEmailAndPass(String email, String password) throws UsernameNotFoundException {
		if (userRepository.findByEmail(email).isPresent()) {
			return new org.springframework.security.core.userdetails.User(email, password, new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}
	}

	public List<User> getAll() {
		logger.info("Finding All Users");
		return userRepository.findAll();
	}

	public String filterToken(String token) {
		return token.replace("Bearer", "").trim();
	}

	public Optional<User> getAdminDetail(String email) {
		logger.info("Get Partner Detial by email");
		return userRepository.findByEmail(email);
	}

	public Optional<String> forgotPasswerd(String email) {
		try {
			Optional<User> user = userRepository.findByEmail(email);

			if (user.isPresent()) {
				User userDetail = user.get();
				userDetail.setPassword(generateToken());
				userDetail = userRepository.save(userDetail);
				String newPassword = userDetail.getPassword();
				Boolean isSent = isMailSent(email, newPassword);
				if (Boolean.TRUE.equals(isSent)) {
					return Optional.of(Utils.generateJsonResponse("success", 200, "Email Sent", null));
				}
			}
			
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}

	private String generateToken() {
		StringBuilder token = new StringBuilder();
		return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
	}

	private Boolean isMailSent(String email, String passwerd) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(email);

			msg.setSubject("EasySite Passwerd Request");
			msg.setText("Hi, YOUR NEW PASSWORD IS " + passwerd);

			javaMailSender.send(msg);
			return true;
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return false;
	}

}
