package com.foursoft.gpa.clientfx;

import java.io.File;
import java.io.IOException;

import com.foursoft.gpa.utils.ApplicationConstants;

public class SqlBrowserApp{
	
	
	public SqlBrowserApp(){
	}
	
	public void start(){
		
		ExternalApplication ea = new ExternalApplication();
		
		ea.setExeFile(ApplicationConstants.BROWSER);
		ea.setPath(getDatabasePath());
		ea.start();
		
	}
	
	public String getDatabasePath(){
		File currentFile = new File(".");
		String path="";
		
		try {
			path=currentFile.getCanonicalPath()+"\\"+ApplicationConstants.CONF_DIR+"\\"+ApplicationConstants.DB_FILE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return path;
	}

}
