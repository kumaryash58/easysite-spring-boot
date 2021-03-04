package com.easybuild.site.utility;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.zip.Deflater;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easybuild.site.constants.Constant;
import com.google.api.services.drive.model.File;

import net.minidev.json.JSONObject;

public class Utils {
	
	public static final Logger logger = LogManager.getLogger(Utils.class);

	
	
	public static String generateJsonResponse(String status, Integer statusCode, String message, JSONObject jsonObject) {
		JSONObject respJOBJ = new JSONObject();
		JSONObject childData = new JSONObject();
		respJOBJ.put("result", childData);
		childData.put("status", status);
		childData.put("code", statusCode);

		if (message != null) {
			childData.put("message", message);
		}

		if (jsonObject != null) {
			childData.putAll(jsonObject);
		}

		return respJOBJ.toJSONString();
	}
	
	public static File createUserFolderInDrive(String email, String userName) {
		try {
			String folder = "User_" + userName + "_"
					+ (Timestamp.valueOf(LocalDateTime.now())).toString();
			File userFolder = CreateFolder.createGoogleFolder(null, folder);
			return userFolder;
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}
	
	public static String getContentTypeByFileName(String fileName) {
		try {
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String contentType = "";
			if (ext.equals(".ZIP") || ext.equals(".zip")) {
				contentType = "application/zip";
			} else if (ext.equals(".PDF") || ext.equals(".pdf")) {
				contentType = "application/pdf";
			} else if (ext.equals(".JPG") || ext.equals(".JPEG") || ext.equals(".jpg") || ext.equals(".jpeg")) {
				contentType = "image/jpeg";
			} else if (ext.equals(".TXT") || ext.equals(".txt")) {
				contentType = "text/plain";
			} else if (ext.equals(".PNG") || ext.equals(".png")) {
				contentType = "image/png";
			} else if (ext.equals(".DOC") || ext.equals(".doc")) {
				contentType = "application/msword";
			} else if (ext.equals(".DOCX") || ext.equals(".docx")) {
				contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			} else if (ext.equals(".GIF") || ext.equals(".gif")) {
				contentType = "image/gif";
			} else if (ext.equals(".VCF") || ext.equals(".vcf")) {
				contentType = "text/x-vcard";
			} else if (ext.equals(".XLS") || ext.equals(".xls") || ext.equals(".XLSX") || ext.equals(".xlsx")) {
				contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			} else if (ext.equals(".KEY") || ext.equals(".key")) {
				contentType = "application/x-iWork-keynote-sffkey";
			} else if (ext.equals(".PPT") || ext.equals(".ppt")) {
				contentType = "application/vnd.ms-powerpoint";
			} else if (ext.equals(".PPTX") || ext.equals(".pptx")) {
				contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
			} else if (ext.equals(".CSV") || ext.equals(".csv")) {
				contentType = "text/x-csv";
			}
			return contentType;
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}
	
	public static String getDefaultProfilePathByGender(String gender) {
		try {
			String profilePath = Constant.BASE_UI_PATH + "Profile_Pic";
			if(gender.equalsIgnoreCase("Female")) {
				profilePath = profilePath+ "/female-1354358_1280.png";
			} else {
				profilePath = profilePath+ "/male-1354358_128.png";
			}
			return profilePath;
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e);
		}
		return null;
	}
	
	// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			logger.info("Compressed Image Byte Size - {}", outputStream.toByteArray().length);
			return outputStream.toByteArray();
		}

}
