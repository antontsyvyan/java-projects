package com.foursoft.gpa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class Resources {

	public static final String SETTINGS_BUNDLE="com.foursoft.gpa.Settings";
	private static String PROPERTIES_FILE="Settings.properties";
	
	public Resources() {
	}
	public static String getSettingInternal(String input){
		ResourceBundle set=ResourceBundle.getBundle(Resources.SETTINGS_BUNDLE);		
		try {
			input = set.getString(input);
        } catch (MissingResourceException mre) {
            //Logger.error(Resources.class, "Please setup " + input + " property in " + SETTINGS_BUNDLE);
        }		
		return input;
	}
	
	
	public static String getSetting(String input){
		String output="";
		File currentFile = new File(".");
		File propertyFile=null;
		try {
			propertyFile = new File(currentFile.getCanonicalPath()+"\\conf\\", PROPERTIES_FILE);
			Properties prop = new Properties();
            prop.load(new FileInputStream(propertyFile.getPath()));
            output=prop.getProperty(input);
            
		} catch (IOException e) {
			System.out.println(propertyFile.getPath());
			System.err.println("Could not load settings: "	+ e.toString());
		}
		
		if(output==null){
			output=input;
		}
		
		return output.trim();
	}
	public static String getPropertiesFile() {
		return PROPERTIES_FILE;
	}
	
	public static void setPropertiesFile(String propertiesFile) {
		// Configure the logging
		File propertyFile=null;
		try {
			File currentFile = new File(".");
			propertyFile= new File(currentFile.getCanonicalPath()+"\\conf\\", propertiesFile);
			
			if(propertyFile.exists()){
				PROPERTIES_FILE = propertiesFile;
			}else{
				System.out.println("Properties file "+propertiesFile+ " doesn't exist. Using "+Resources.getPropertiesFile());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	

}
