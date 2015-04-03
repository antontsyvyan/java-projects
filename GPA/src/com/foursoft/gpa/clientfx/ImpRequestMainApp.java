package com.foursoft.gpa.clientfx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.foursoft.gpa.clientfx.controller.RequestController;

public class ImpRequestMainApp extends Application{

	public static final String VIEW_FXML = "GpaRequest.fxml";
    private String type=RequestController.IMP;
    private String action=RequestController.REQUEST;
	
	public ImpRequestMainApp() {
		
	}
	
	public void start() {
		Pane pane=null;
		try {
			// Load the fxml file
			FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+ VIEW_FXML));
			pane = (Pane) loader.load();
			Scene scene = new Scene(pane);
					
			RequestController controller=loader.getController();
			controller.init(type, action);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			stage.setTitle(getTitle());
			
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

	public void setAction(String action) {
		this.action = action;
	}
	
	
	private String getTitle(){
	
		String action=this.action.toLowerCase();
		return Character.toUpperCase(action.charAt(0)) + action.substring(1)+" "+type;
	
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
