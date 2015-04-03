package com.foursoft.gpa.clientfx;

import com.foursoft.gpa.clientfx.controller.FileInterfaceLogController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FileInterfaceLogApp extends Application {

	public static final String VIEW_FXML = "FileInterfaceLog.fxml";
	
	private FileInterfaceLogController controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load the fxml file
		try {
	        Stage stage=new Stage();
	        stage.setResizable(false);
	        
	        Scene  scene = new Scene((Pane)getNode());   	        
	        stage.setScene(scene);
	        stage.show();
	               
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
	
	public Node getNode() {
		Pane pane=null;
		try {
			FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+VIEW_FXML));
	        pane = (Pane) loader.load();	                  
	        controller=loader.getController();
	        controller.populateGridData();
	        //Submit background task
			Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2),	new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.populateGridData();
				}
			}));
	   
	        timer.setCycleCount(Timeline.INDEFINITE);
	        timer.play();
	        
	        
	        Timeline progressTimer = new Timeline(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					controller.checkProgress();
				}
			}));
	   
	        progressTimer.setCycleCount(Timeline.INDEFINITE);
	        progressTimer.play();
	        
        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}	
			
		return pane;
	}

}
