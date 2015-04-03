package com.foursoft.gpa.clientfx;

import java.io.File;

import com.foursoft.gpa.utils.ApplicationConstants;
import com.foursoft.gpa.utils.Resources;
import com.foursoft.gpa.utils.TopFileFilter;

public class LogViewerApp{
	
	
	public LogViewerApp(){
	}
	
	public void start(){
		
		ExternalApplication ea = new ExternalApplication();
		
		ea.setExeFile(ApplicationConstants.VIEWER);
		ea.setPath(getApplicationLogPath());
		ea.start();
		
	}
	
	private String getApplicationLogPath(){
		
		File logDir = new File(Resources.getSetting("server.root")+"\\"+ApplicationConstants.LOG_DIR);
		//StringBuffer buf=new StringBuffer();
		String path="";
		try {
			//buf.append(logDir.getCanonicalPath());
			//buf.append("\\");
			//buf.append(com.foursoft.gpa.client.GpaDashboard.LOG_FILE);
			TopFileFilter tff=new TopFileFilter();
			logDir.listFiles(tff);
			path=tff.getTopFile().getCanonicalPath();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//return buf.toString();
		return path;
	}

}
