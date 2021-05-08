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
import org.springframework.transaction.annotation.Transactional;

import com.easybuild.site.constants.Constant;
import com.easybuild.site.dao.GenderDAO;
import com.easybuild.site.dao.PostDAO;
import com.easybuild.site.dao.RoleDAO;
import com.easybuild.site.dao.UserDAO;
import com.easybuild.site.entity.Post;
import com.easybuild.site.entity.User;
import com.easybuild.site.security.JwtTokenUtil;
import com.easybuild.site.utility.CreateGoogleFile;
import com.easybuild.site.utility.ShareGoogleFile;
import com.easybuild.site.utility.Utils;
import com.google.api.services.drive.model.File;

import net.minidev.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

	public static final Logger logger = LogManager.getLogger(UserService.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private GenderDAO genderDAO;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private PostDAO postDAO;

	/**
	 * Sign in a user into the application, with JWT-enabled authentication
	 *
	 * @param email    email
	 * @param password password
	 * @return Optional of the Java Web Token, empty otherwise
	 */
	public JSONObject authenicate(String email, String password) {
		logger.info("User Authenticating...");
		Optional<String> token = Optional.empty();
		Optional<User> user = userDAO.findByEmail(email);
		JSONObject userProfileDetails = new JSONObject();
		if (user.isPresent()) {
			try {
				// authenticationManager.authenticate(new
				// UsernamePasswordAuthenticationToken(email, password));
				token = Optional.of(jwtTokenUtil.createToken(email));
				userProfileDetails.put("token", token);
				userProfileDetails.put("userId", user.get().getId());
				userProfileDetails.put("firstName", user.get().getFirstName());
				userProfileDetails.put("lastName", user.get().getLastName());
				userProfileDetails.put("profileImgPath", user.get().getProfilePic());
				userProfileDetails.put("gender", user.get().getGender().getGenderName());

			} catch (AuthenticationException e) {
				logger.log(Level.ERROR, e.getMessage(), e);
			}
		}
		return userProfileDetails;
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param gender
	 * @param country
	 * @param city
	 * @details: Create New User
	 */
	public Optional<User> signup(String firstName, String lastName, String email, String password, String gender,
			String country, String city) {
		logger.info("Creating New User Account");
		String roleName = Constant.DEFAULT_ROLE;
		Optional<User> user = Optional.empty();
		if (!userDAO.findByEmail(email).isPresent()) {
			try {
				String path = "src/main/resources";

				java.io.File file = new java.io.File(path);
				String absolutePath = file.getAbsolutePath();
				System.out.println("absolutePath"+absolutePath);
				File folder = Utils.createUserFolderInDrive(email, firstName);
				String userFolderName = folder.getName();
				String folderParentId = folder.getId();
//				String contentType = Utils.getContentTypeByFileName(profileImgPath);
//
//				String ext = profileImgPath.substring(profileImgPath.lastIndexOf("."));
//				String newFileName = firstName + "_" + (Timestamp.valueOf(LocalDateTime.now())).toString() + ext;
//				BufferedImage bImage = ImageIO.read(new java.io.File(profileImgPath));
//				ByteArrayOutputStream bos = new ByteArrayOutputStream();
//				ImageIO.write(bImage, ext, bos);
//				byte[] data = bos.toByteArray();
//				File googleFile = CreateGoogleFile.createGoogleFile(folderParentId, contentType, newFileName,
//						Utils.compressBytes(data));
//				String profilePic = googleFile.getWebViewLink();

				user = Optional.of(userDAO.save(new User(firstName, lastName, email, password, country, city, null,
						null, genderDAO.findByGenderName(gender).get(), roleDAO.findByRoleName(roleName).get(),
						userFolderName, folderParentId, null, Timestamp.valueOf(LocalDateTime.now()),
						Timestamp.valueOf(LocalDateTime.now()))));
			} catch (Exception e) {
				logger.log(Level.ERROR, e.getMessage(), e);
			}
		}
		return user;
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param mobileNo
	 * @param address
	 * @param country
	 * @param city
	 * @param originalFilename
	 * @param contentType
	 * @param compressBytes
	 * @details Update User Details
	 */
	public JSONObject updateProfile(String firstName, String lastName, Long id, String mobileNo, String address,
			String country, String city) {
		logger.info("Updating New User Account");
		try {
			Optional<User> user = userDAO.findById(id);
			if (user.isPresent()) {
				User userDetail = user.get();
				userDetail.setFirstName(firstName);
				userDetail.setLastName(lastName);
				userDetail.setCountry(country);
				userDetail.setCity(city);
				userDetail.setMobileNo(mobileNo);
				userDetail.setAddress(address);
				userDetail.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
				userDAO.save(userDetail);
				JSONObject userJOBJ = new JSONObject();
				userJOBJ.put("firstName", userDetail.getFirstName());
				userJOBJ.put("lastName", userDetail.getLastName());
				return userJOBJ;
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}

	public UserDetails loadUserByEmailAndPass(String email, String password) throws UsernameNotFoundException {
		if (userDAO.findByEmail(email).isPresent()) {
			return new org.springframework.security.core.userdetails.User(email, password, new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
	}

	public List<User> getAll() {
		logger.info("Finding All Users");
		return userDAO.findAll();
	}

	public String filterToken(String token) {
		return token.replace("Bearer", "").trim();
	}

	public Optional<User> getAdminDetail(String email) {
		logger.info("Get Partner Detial by email");
		return userDAO.findByEmail(email);
	}

	/**
	 * 
	 * @param email
	 * @details: Forgot password send mail
	 */
	public Optional<String> forgotPasswerd(String email) {
		try {
			Optional<User> user = userDAO.findByEmail(email);

			if (user.isPresent()) {
				User userDetail = user.get();
				userDetail.setPassword(generateToken());
				userDetail = userDAO.save(userDetail);
				String newPassword = userDetail.getPassword();
				Boolean isSent = isMailSent(email, newPassword);
				if (Boolean.TRUE.equals(isSent)) {
					return Optional.of(Utils.generateJsonResponse("success", 200, "Email Sent", null));
				}
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return Optional.empty();
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

	public String updateProfileImage(String email, String isProfilePic, String originalFilename, String contentType,
			byte[] compressBytes) {
		logger.info("Updating New User Account");
		try {
			if (userDAO.findByEmail(email).isPresent()) {
				User userDetail = userDAO.findByEmail(email).get();
				String folderParentId = userDetail.getFolderParentId();
				if (folderParentId == null || folderParentId.isEmpty()) {
					File folder = Utils.createUserFolderInDrive(email, userDAO.findByEmail(email).get().getFirstName());
					String userFolderName = folder.getName();
					folderParentId = folder.getId();
					userDetail.setFolder(userFolderName);
					userDetail.setFolderParentId(folderParentId);
					userDetail.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
				}
				contentType = Utils.getContentTypeByFileName(originalFilename);
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				String newFileName = userDetail.getFirstName() + "_"
						+ (Timestamp.valueOf(LocalDateTime.now())).toString() + ext;
				File googleFile = CreateGoogleFile.createGoogleFile(folderParentId, contentType, newFileName,
						compressBytes);
				ShareGoogleFile.createPublicPermission(googleFile.getId());
				String imgSrc = Utils.getDriveImgSrcLinkByFileId(googleFile.getId());
				if (isProfilePic.equalsIgnoreCase("true")) {
					userDetail.setProfilePic(imgSrc);
					userDetail.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
					userDAO.save(userDetail);
				} else {
					Post post = postDAO.findByUser(userDetail);
					post.setFileName(newFileName);
					post.setFileType(contentType);
					post.setFileUrl(imgSrc);
					postDAO.save(post);
				}
				return Utils.generateJsonResponse("Success", 200, imgSrc, null);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}

}
