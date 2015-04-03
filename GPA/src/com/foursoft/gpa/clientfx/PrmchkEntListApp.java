package com.foursoft.gpa.clientfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.foursoft.gpa.clientfx.controller.PrmchkEntListController;

public class PrmchkEntListApp extends Application {
	
	public static final String VIEW_FXML = "PrmchkEntList.fxml";

	private PrmchkEntListController controller;
	
	public PrmchkEntListApp() {
				
	}

	@Override
	public void start(Stage primaryStage) {
		
		try {
			Scene scene = new Scene((Pane)getNode());
			scene.getStylesheets().add(Dashboard.class.getResource(Dashboard.CSS_FOLDER+Dashboard.VIEW_CSS).toExternalForm());
			Stage stage = new Stage();			
			stage.setTitle("Rate List");
			stage.setScene(scene);
			stage.show();		
		} catch (Exception e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
		
	}
	
	public Node getNode(){
		TreeView<?> pane=null;
		try {
			// Load the fxml file
			FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+ VIEW_FXML));			
			pane = loader.load();
			controller=loader.getController();
			controller.populateData();
			//Submit background task
			Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2),	new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.populateData();
				}
			}));
	   
	        timer.setCycleCount(Timeline.INDEFINITE);
	        timer.play();
		} catch (Exception e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
		return pane;
	}

}
