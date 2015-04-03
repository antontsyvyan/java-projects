package com.foursoft.gpa.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;

import com.foursoft.gpa.GpaCore;
import com.foursoft.gpa.db.Intlog;
public abstract class FileProcessors extends Processors {

	private String extention = "";
	
	private String interfaceName="";
	
	protected List<DiskErrorsModel> errorsList;
	
	private Intlog intlog=new Intlog();
	
	private boolean locked=false;
	
	public abstract void init();
	
	public abstract boolean loadFile(String fileName);
	

	@Override
	public void Process() {
		init();
		
		if(interfaceName.equals("")){
			interfaceName=Thread.currentThread().getName();
		}

		log.info(" ==> Entering " + interfaceName + " interface <==");
		try{
			// Just retrieve files that end with extension defined at the top.
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith("." + extention.toLowerCase());
				}
			};
	
			File path = new File(Resources.getSetting("upload.folder"));
	
			File inputDirectory = path.getAbsoluteFile();
			if (path.isDirectory() == false) {
				path.mkdir();
			}
			
	       File[] f = inputDirectory.listFiles(filter); 
		   for (int i = 0; i < f.length; i++) {
				String fileName = f[i].getPath();
				//String parent = f[i].getParent();
				String name = f[i].getName();
				log.info("Retrieving file = " + fileName);
				// Process file
				//String newFileName = fileName;
				long startTime = System.currentTimeMillis();
				errorsList = new ArrayList<DiskErrorsModel>();
				String newPath="";				
				if (loadFile(fileName) && !isLocked()) {
					newPath=Resources.getSetting("archive.folder");					
					log.info("File " + fileName + " has been processed");	
				} else {
					//newFileName = fileName.replace("." + EXTENSION,".error");
					newPath=Resources.getSetting("error.folder");
				}
	
				if(!isLocked()){
					saveFile(f[i],newPath);			
					printDuration(startTime,fileName);				
					saveLog(name);
				}

		  }
	  }catch(Exception e){
		  e.printStackTrace();
		  
	  }finally{

	  }
	}
	
	
	private void printDuration(long startTime, String fileName ){
		
		long endTime = System.currentTimeMillis();
		long dif = (endTime - startTime) / (1000 * 60);
		String text=dif + " minutes";
		if(dif==0){
			dif=(endTime - startTime) / (1000);
			text= dif + " seconds";			
			if(dif==0){
				dif=endTime - startTime;
				text= dif + " milliseconds";
				
			}
		}
		
		log.info("Processing of the file " + fileName + " took "+ text);
	}

	private void saveLog(String fileName){
		TreeMap<String, String> intlogDetail= new TreeMap<String, String>();
		
		intlogDetail.put(Intlog.DB_INTLOG_INT_NAME, interfaceName);
		intlogDetail.put(Intlog.DB_INTLOG_SEQUENCE, "");
		intlogDetail.put(Intlog.DB_INTLOG_FILE_NAME, fileName);
		intlogDetail.put(Intlog.DB_INTLOG_PROC_DATE, du.getCurrentDateInhouse());
		intlogDetail.put(Intlog.DB_INTLOG_PROC_TIME, du.getCurrentTimeInhouse());
		intlogDetail.put(Intlog.DB_INTLOG_STAT, "");
		intlogDetail.put(Intlog.DB_INTLOG_ERR_MSG, "");

		StringBuffer buffer=new StringBuffer();
		boolean errorFlag=false;
		if (errorsList != null && errorsList.size() > 0) {
			for(DiskErrorsModel entry : errorsList){
				buffer.append(entry.toString());
				buffer.append(LINE_SEPARATOR);
				if(entry.getType().equals(Processors.REQUEST_STATUS_ERROR)){
					errorFlag=true;
				}
			}
			String message=buffer.toString();
			if(message.length()>4000){
				intlogDetail.put(Intlog.DB_INTLOG_ERR_MSG,GpaCore.ERROR_PREFIX+createErrorLog());
			}else{
				intlogDetail.put(Intlog.DB_INTLOG_ERR_MSG, message);
			}
			
			
			if(errorFlag){
				intlogDetail.put(Intlog.DB_INTLOG_STAT, Processors.REQUEST_STATUS_ERROR);
			}else{
				intlogDetail.put(Intlog.DB_INTLOG_STAT, REQUEST_STATUS_WARNING);
			}
		}else{
			intlogDetail.put(Intlog.DB_INTLOG_STAT, Processors.REQUEST_STATUS_PROCESSED);
			intlogDetail.put(Intlog.DB_INTLOG_ERR_MSG, "File has been processed.");
		}
		
		intlog.insertIntlog(intlogDetail);
	}

	
	public String createErrorLog(){
		
		//Create txt file
		String errorLogFileName=FileUtils.getFileName(FileUtils.LOG)+".txt";
		
		BufferedWriter output =null;
		File file = new File(errorLogFileName);
		
		try {
			output = new BufferedWriter(new FileWriter(file));
			Iterator<DiskErrorsModel> iterator = errorsList.iterator();
			while (iterator.hasNext()) {
				output.write(iterator.next().toString()+"\r\n");
			}
		} catch (IOException e) {
			
		}finally{
			try {
				output.close();
			} catch (IOException e) {
			}
		}
		
		return file.getName();
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	
	

}
