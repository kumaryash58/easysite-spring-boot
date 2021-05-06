package com.easybuild.site.utility;

import java.io.IOException;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;

public class ChilkatExample {
	//String fileId = "1sTWaJ_j7PkjzaBWtNc3IzovK5hQf21FbOw9yLeeLPNQ";

	public static void createPermission(String fileId) {
		Drive driveService = null;
		try {
			driveService = GoogleDriveUtils.getDriveService();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {

			@Override
			public void onSuccess(Permission t, HttpHeaders responseHeaders) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
				// TODO Auto-generated method stub

			}

		};
		BatchRequest batch = driveService.batch();
		Permission userPermission = new Permission().setType("user").setRole("writer")
				.setEmailAddress("user@example.com");
		try {
			driveService.permissions().create(fileId, userPermission).setFields("id").queue(batch, callback);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Permission domainPermission = new Permission().setType("domain").setRole("reader").setDomain("example.com");
		try {
			driveService.permissions().create(fileId, domainPermission).setFields("id").queue(batch, callback);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			batch.execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
