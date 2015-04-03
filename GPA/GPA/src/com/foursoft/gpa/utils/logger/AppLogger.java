package com.foursoft.gpa.utils.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.foursoft.gpa.utils.Resources;

public class AppLogger {

	private static SimpleFormatter formatter;
	private static FileHandler fileFileHandler;
	public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static public void setup() throws IOException {		
		// Get the global logger to configure it
		 
		try{
			logger.setLevel(Level.parse(Resources.getSetting("log.level").toUpperCase()));
		}catch(Exception ex){
			//If the log level couldn't be parsed set to ERROR
			logger.setLevel(Level.SEVERE);
		}
		
		String fileName=Resources.getSetting("log.file");
		fileFileHandler = new FileHandler(fileName);
		// Create text Formatter
	    formatter = new SimpleFormatter();
	    fileFileHandler.setFormatter(formatter);
	    logger.addHandler(fileFileHandler);
	    
	    logger.severe("general");
	}	

}
