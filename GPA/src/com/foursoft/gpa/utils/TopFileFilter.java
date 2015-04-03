package com.foursoft.gpa.utils;

import java.io.File;
import java.io.FileFilter;

public class TopFileFilter implements FileFilter {

	private File topFile;

	public TopFileFilter() {
	}

	@Override
	public boolean accept(File newF) {
		if(topFile == null){
            topFile = newF;
		}else{ 
			if(newF.lastModified()>topFile.lastModified()){
				topFile = newF;
			}			
		}
                        
        return false;
	}
	
	public File getTopFile() {
		return topFile;
	}

}
