package com.foursoft.gpa.clientfx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.foursoft.gpa.clientfx.controller.DiskErrorsController;

public class DiskErrorsMainApp {

	public static final String VIEW_FXML = "DiskErrors.fxml";
	
	private String fileName;
	private String errorString;
	private boolean showLinks=false;
		
	public DiskErrorsMainApp() {

	}

	public DiskErrorsMainApp(String fileName) {
		this.fileName=fileName;

	}
	
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}
	
	

	public void setShowLinks(boolean showLinks) {
		this.showLinks = showLinks;
	}

	public void start() {
		
		try {
			 // Load the fxml file
	        FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+VIEW_FXML));
	        AnchorPane overviewPage = (AnchorPane) loader.load();	           
	        
	        DiskErrorsController controller=loader.getController();
            
	        controller.setFileName(fileName);
	        controller.setErrorString(errorString);
	        controller.setShowLinks(showLinks);
            controller.populateData(true,true);
                                        
            Stage stage= new Stage();
	        stage.setTitle("Disk errors");
	        
	        Scene  scene = new Scene(overviewPage);                            	
	        stage.setScene(scene);
	        stage.show();
	        
		} catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
	}
}
