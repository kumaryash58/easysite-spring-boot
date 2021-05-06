package com.easybuild.site.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easybuild.site.constants.Constant;
import com.easybuild.site.dao.PostDAO;
import com.easybuild.site.dao.TemplateDAO;
import com.easybuild.site.dao.UserDAO;
import com.easybuild.site.entity.Post;
import com.easybuild.site.entity.Templates;
import com.easybuild.site.entity.User;
import com.easybuild.site.utility.CreateGoogleFile;
import com.easybuild.site.utility.ShareGoogleFile;
import com.easybuild.site.utility.Utils;
import com.google.api.services.drive.model.File;

@Service
public class PostService {

	public static final Logger logger = LogManager.getLogger(PostService.class);

	@Autowired
	private PostDAO postDAO;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private TemplateDAO templateDAO;

	/**
	 * 
	 * @param title
	 * @param description
	 * @param postBody
	 * @param originalFilename
	 * @param contentType
	 * @param compressBytes
	 * @param email
	 * @details: Add New Post
	 */
	public Optional<String> post(String title, String description, String postBody, String email) {
		try {
			if (userDAO.findByEmail(email).isPresent()) {
				User userDetail = userDAO.findByEmail(email).get();
//				String folderParentId = userDetail.getFolderParentId();
//				if (folderParentId == null || folderParentId.isEmpty()) {
//					File folder = Utils.createUserFolderInDrive(email,
//							userRepository.findByEmail(email).get().getFirstName());
//					String userFolderName = folder.getName();
//					folderParentId = folder.getId();
//					userDetail.setFolder(userFolderName);
//					userDetail.setFolderParentId(folderParentId);
//					userDetail.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
//				}
//				contentType = Utils.getContentTypeByFileName(originalFilename);
//				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
//				String newFileName = userDetail.getFirstName() + "_"
//						+ (Timestamp.valueOf(LocalDateTime.now())).toString() + ext;
//				File googleFile = CreateGoogleFile.createGoogleFile(folderParentId, contentType, newFileName,
//						compressBytes);
//				ShareGoogleFile.createPublicPermission(googleFile.getId());
//				String fileUrl = Utils.getDriveImgSrcLinkByFileId(googleFile.getId());
				Optional<Post> post = Optional.of(postDAO.save(new Post(title, description, postBody, null, null,
						null, userDAO.findByEmail(email).get(), false, Timestamp.valueOf(LocalDateTime.now()),
						Timestamp.valueOf(LocalDateTime.now()))));
				return Optional.of(Utils.generateJsonResponse("success", 200, (post.get().getId()).toString(), null));
			}

		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return Optional.empty();
	}

	public String filterToken(String token) {
		return token.replace("Bearer", "").trim();
	}

	/**
	 * @param email
	 * @detail: Get Post By User Email
	 */
	public List<Post> getAllPosts(String email) {
		try {
			logger.info("User attempting to retrieve all posts");
			Optional<User> user = userDAO.findByEmail(email);
			List<Post> newList = new ArrayList<Post>();
			if (user.isPresent()) {
				User userDetail = user.get();
				List<Post> list = postDAO.findPostByUser(userDetail);
				for (int x = 0; x < list.size(); x++) {
					Post post = new Post(list.get(x).getTitle(), list.get(x).getDescription(),
							list.get(x).getFileName(), list.get(x).getFileType(), list.get(x).getFileUrl(), false);
					if(list.get(x).getIsDeleted()) {
						continue;
					}
					post.setId(list.get(x).getId());
					newList.add(post);
				}
			}
			return newList;
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return Collections.emptyList();
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

	public String updatePostImage(Long id, String originalFilename, String contentType, byte[] compressBytes) {
		logger.info("Updating New User Account");
		try {
			Post post = postDAO.findById(id).get();
			String email = post.getUser().getEmail();
			if (userDAO.findByEmail(email).isPresent()) {
				User userDetail = userDAO.findByEmail(email).get();
				String folderParentId = userDetail.getFolderParentId();
				if (folderParentId == null || folderParentId.isEmpty()) {
					File folder = Utils.createUserFolderInDrive(email,
							userDAO.findByEmail(email).get().getFirstName());
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
				post.setFileName(newFileName);
				post.setFileType(contentType);
				post.setFileUrl(imgSrc);
				postDAO.save(post);
				return Utils.generateJsonResponse("Success", 200, imgSrc, null);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}

	public Optional<Post> getPostDetailById(String id) {
		logger.info("Get Post detail by id");
		return postDAO.findById(Long.parseLong(id));
	}

	public Optional<String> updatePost(String title, String description, String postBody, String id) {
		try {
			Post post = postDAO.findById(Long.parseLong(id)).get();
			post.setTitle(title);
			post.setDescription(description);
			post.setPostBody(postBody);
			post.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
			postDAO.save(post);
			return Optional.of(Utils.generateJsonResponse("success", 200, "Post Updated!", null));
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return Optional.empty();
	}
	
	/**
	 * @param templateName
	 * @param tempStructure
	 * @param originalFilename
	 * @param contentType
	 * @param compressBytes
	 * @details This function On-board site template -By Super ADMIN
	 */
	public Optional<String> createTemplate(String templateName, String tempStructure, String originalFilename,
			String contentType, byte[] compressBytes) {
		logger.info("Updating New User Account");
		try {
			String folderId = Constant.TEMPLATE_SNAPSHOT_FOLDER_PARENT_ID;
			contentType = Utils.getContentTypeByFileName(originalFilename);
			String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
			String newFileName = templateName + "_" + (Timestamp.valueOf(LocalDateTime.now())).toString() + ext;
			File googleFile = CreateGoogleFile.createGoogleFile(folderId, contentType, newFileName, compressBytes);
			ShareGoogleFile.createPublicPermission(googleFile.getId());
			String tempSnapshotUrl = Utils.getDriveImgSrcLinkByFileId(googleFile.getId());

			Optional.of(templateDAO.save(new Templates(templateName, folderId, tempSnapshotUrl, tempStructure,
					Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()))));
			return Optional.of(Utils.generateJsonResponse("success", 200, "Template Updated!", null));
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return Optional.empty();
	}
	
	public List<Templates> getAllTemplates() {
		try {
			logger.info("User attempting to retrieve all templates");
			return templateDAO.findAll();
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	public Optional<Templates> getTemplateDetails(String id) {
		logger.info("Get Temp detail by id");
		return templateDAO.findById(Long.parseLong(id));
	}

	public void deletePost(String id) {
		try {
			logger.info("User attempting to Delete Post");
//			 EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
//		     entityManager.getTransaction().begin();
//		     Post post = entityManager.find(Post.class, 1);
			Post post = postDAO.findById(Long.valueOf(id)).get();
			post.setIsDeleted(true);
			post.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
//			entityManager.getTransaction().commit();
//	        entityManager.close();
			postDAO.save(post);
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
	}

}
