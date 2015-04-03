package com.foursoft.gpa.clientfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ImpDetailsMainApp {

	public static final String VIEW_FXML = "ImpDetails.fxml";

	
	public ImpDetailsMainApp() {
		
	}
	
	public void start() {
		try {
			
			Scene scene = new Scene((Pane)getNode());				
			//ImpDetailsController controller=loader.getController();
			Stage stage = new Stage();
			//stage.initModality(Modality.WINDOW_MODAL);			
			stage.setTitle("IMP Details");
			
			stage.setScene(scene);
			stage.show();
			
		} catch (Exception e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
		
	}
	
	public Node getNode() {
		Pane pane=null;
		try {
			// Load the fxml file
			FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+VIEW_FXML));
	        pane = (Pane) loader.load();	                  
	        
		} catch (Exception e) {
			e.printStackTrace();
		}	
			
		return pane;
	}

	

}
