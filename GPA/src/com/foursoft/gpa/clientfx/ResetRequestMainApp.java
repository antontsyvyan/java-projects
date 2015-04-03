package com.foursoft.gpa.clientfx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.clientfx.controller.ResetRequestController;

public class ResetRequestMainApp {
	
	
	public static final String VIEW_FXML = "ResetRequest.fxml";
	private String type=Disk.ENTREPOT;
	
	
	public void start() {
		try {
			// Load the fxml file
			FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+ VIEW_FXML));
			BorderPane overviewPage = (BorderPane) loader.load();
			Scene scene = new Scene(overviewPage);
					
			ResetRequestController controller=loader.getController();
			controller.init(type);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			stage.setTitle("Reset "+type);
			
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
		
	}
	
	
	
	public void setType(String type) {
		this.type = type;
	}

}
