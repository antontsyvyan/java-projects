package com.foursoft.gpa.utils;

import java.io.File;
import java.nio.file.Files;



import com.foursoft.gpa.clientfx.controller.DashboardController;
import com.foursoft.gpa.clientfx.controller.FileInterfaceLogController;

public class Uploader extends Thread{
	
	
	public static final String TEMP_FOLDER="temp";
	
	private File currentFile = new File(".");
	private File tempFile=null;	
	private File sourceFile;
	private File destinationFile;
	
	private String errorMessage="";
	

	public Uploader() {
	}
	
	@Override
	public void run() {
		
		DashboardController.uploadErrorMessage="";
		DashboardController.uploaded=false;
		
		FileInterfaceLogController.uploadErrorMessage="";
		FileInterfaceLogController.uploaded=false;
		
		if(!isRemoteExists()){
			copyToTemp();
			moveToServer();			
		}
		DashboardController.uploadErrorMessage=errorMessage;
		DashboardController.uploaded=true;
		
		FileInterfaceLogController.uploadErrorMessage=errorMessage;
		FileInterfaceLogController.uploaded=true;
			
	}

	private void copyToTemp(){		
		try{
			checkTemp();
			tempFile = new File(currentFile.getCanonicalPath()+"\\"+TEMP_FOLDER+"\\", getFileNameWithoutExtention(sourceFile.getName())+".tmp");
			Files.copy(sourceFile.toPath(),tempFile.toPath());
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			
		}
	}
	
	private void moveToServer(){
		try {
						
			    	
			File remoteTempFile=new File(Resources.getSetting("upload.folder_remote")+"\\"+tempFile.getName());
			//Move temp file to the remote folder
			tempFile.renameTo(remoteTempFile);
			
			//rename file in the remote folder			
			remoteTempFile.renameTo(destinationFile);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getFileNameWithoutExtention(String fileName){
		
		String fileNameWithoutExtention=fileName;
		if (sourceFile.getName().contains(".")) {
			fileNameWithoutExtention = sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf("."));
		}
		
		return fileNameWithoutExtention;
	}
	
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	
	private void checkTemp(){
		try {
			File directory = new File(currentFile.getCanonicalPath()+"\\"+TEMP_FOLDER+"\\");
			if(!directory.exists()){
				directory.mkdir();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private boolean isRemoteExists(){
		destinationFile=new File((Resources.getSetting("upload.folder_remote")+"\\"+sourceFile.getName()));
		
		if(destinationFile.exists()){
			errorMessage="File "+sourceFile.getName()+" already exists in the destination folder";
			return true;
		}
		return false;
	}
		

}
