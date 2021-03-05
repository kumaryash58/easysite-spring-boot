package com.easybuild.site.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easybuild.site.constants.Constant;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
 
public class GoogleDriveUtils {
	
	public static final Logger logger = LogManager.getLogger(GoogleDriveUtils.class);
 
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
 
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
 
    // Global instance of the {@link FileDataStoreFactory}.
    private static FileDataStoreFactory DATA_STORE_FACTORY;
 
    // Global instance of the HTTP transport.
    private static HttpTransport HTTP_TRANSPORT;
 
    private static Drive _driveService;
 
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(new File("\\src\\main\\resources"+Constant.CREDENTIALS_FILE_PATH));
            logger.info("DATA_STORE_FACTORY Path {}", DATA_STORE_FACTORY);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
 
    public static Credential getCredentials() throws IOException {
    	URL res = GoogleDriveUtils.class.getResource(Constant.CREDENTIALS_FILE_PATH);
    	File clientSecretFilePath = null;
		try {
			clientSecretFilePath = Paths.get(res.toURI()).toFile();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     //   java.io.File clientSecretFilePath = new java.io.File(Constant.RESOURCE_PATH + Constant.CREDENTIALS_FILE_PATH);
    	
        if (!clientSecretFilePath.exists()) {
            throw new FileNotFoundException("Please copy " + Constant.CREDENTIALS_FILE_PATH //
                    + " to folder: " + new File(Constant.CREDENTIALS_FILE_PATH).getAbsolutePath());
        }
 
        InputStream in = new FileInputStream(clientSecretFilePath);
 
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
 
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, Constant.SCOPES_DRIVE).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
 
        return credential;
    }
 
    public static Drive getDriveService() throws IOException {
        if (_driveService != null) {
            return _driveService;
        }
        Credential credential = getCredentials();
        //
        _driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) //
                .setApplicationName(APPLICATION_NAME).build();
        return _driveService;
    }
 
}
