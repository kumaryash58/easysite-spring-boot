package com.easybuild.site.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppConfig {

	public static final Logger logger = LogManager.getLogger(AppConfig.class.getName());

	public static String getConfigValue(String name) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
			properties.load(resourceStream);
			return properties.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
