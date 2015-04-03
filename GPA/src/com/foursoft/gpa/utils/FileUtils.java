package com.foursoft.gpa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

	public static final String DISK="DISK";
	public static final String LOG="LOG";
	
	public static synchronized String getFileName(String folder){
		String fileName="";
		File currentFile = new File(".");	
		try {
			String saveDir=currentFile.getCanonicalPath()+"\\"+folder;
			File dir=new File(saveDir);  
			if(!dir.isDirectory()){
				//Create directory
				dir.mkdir();
			}	
		fileName=folder+"_"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); 
		
		fileName=saveDir+"\\"+fileName;
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return fileName;
	}
	
	public static synchronized String getAboutItem(){
		
		Reader reader = new InputStreamReader(FileUtils.class.getResourceAsStream("/com/foursoft/gpa/info/info.txt")); 
		BufferedReader br= new BufferedReader(reader);
		
		String line ="";
		String version="";
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		line=line.replace("GPA", "");
		String sep="";
		String[] parts=line.split("-");
		for(String part:parts){	
			try{
				version+=sep+Integer.parseInt(part);
				sep=".";
			}catch(Exception e){				
			}
			
		}
		
		if(version.equals("")){
			version=line;
		}
		
		return version;
	}
}
