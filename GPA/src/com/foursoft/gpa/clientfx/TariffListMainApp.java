package com.foursoft.gpa.clientfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TariffListMainApp extends Application{

	public static final String VIEW_FXML = "TariffList.fxml";
	//private TariffListController controller;
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			Scene scene = new Scene((Pane)getNode());		
			Stage stage = new Stage();			
			stage.setTitle("Articles");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public Node getNode(){
		Pane pane=null;
		try {
			// Load the fxml file
			FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+ VIEW_FXML));
			pane = (Pane) loader.load();
			//controller=loader.getController();
	        //controller.populateData("");
		} catch (Exception e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
		return pane;
	}
}
