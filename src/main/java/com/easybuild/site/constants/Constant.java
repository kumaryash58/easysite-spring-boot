package com.easybuild.site.constants;

import java.util.Collections;
import java.util.List;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

public class Constant {
	
	 	public static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	    public static final String TOKENS_DIRECTORY_PATH = "tokens";
	    
	    /**
	     * Global instance of the scopes required by this quickstart.
	     * If modifying these scopes, delete your previously saved tokens/ folder.
	     */
	    public static final List<String> SCOPES_DRIVE_METADATA_READONLY = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
	    public static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	    
	    public static final List<String> SCOPES_DRIVE = Collections.singletonList(DriveScopes.DRIVE);
		public static final String RESOURCE_PATH = "resources";
		
		public static final String BASE_UI_PATH = "C:/Users/Lenovo/Desktop/Personal Blog/Site/Site/src/main/ui/";
		
		public static final String DEFAULT_ROLE = "ADMIN";
		
		public static final String TEMPLATE_SNAPSHOT_FOLDER_PARENT_ID = "1T8GSfjqapMfLt4bHPsWLr8VvYOS3sWLo";
		
		public static final String STATUS_SUCCESS = "success";
		public static final String STATUS_FAILURE = "failure";
		public static final String RESULT = "result";
		public static final String MESSAGE = "message";
		public static final String STATUS = "status";
		public static final String CODE = "code";
		public static final String DATA = "data";

}
