package com.foursoft.gpa.clientfx;

import java.io.File;
import java.io.IOException;

import org.controlsfx.dialog.Dialogs;

import com.foursoft.gpa.utils.ApplicationConstants;

public class ExternalApplication {
	
	private String exeFile;
	private String path;
	
	public void start(){
		//construct path to the viewer
		File f = new File(path);
		if(!f.exists()){
			Dialogs.create()
					  .nativeTitleBar()
				      .title("Error")
				      .masthead(null)
				      .message( "File "+path+" doesn't exists!")
				      .showError();
			return;	
		}
		File binDir = new File(ApplicationConstants.BIN_DIR);
		try {
			StringBuffer cmd= new StringBuffer();
			cmd.append(binDir.getCanonicalPath());
			cmd.append("\\");
			cmd.append(exeFile);
			cmd.append(" ");
			cmd.append("\"");
			cmd.append(path);
			cmd.append("\"");
			
			Runtime.getRuntime().exec(cmd.toString());
		} catch (IOException e) {

		}
	}
	
	

	public void setExeFile(String exeFile) {
		this.exeFile = exeFile;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
