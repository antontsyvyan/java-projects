package com.foursoft.gpa.clientfx;

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

import com.foursoft.gpa.clientfx.controller.GpaRequestLogController;

public class GpaRequestLogApp extends Application {

	public static final String VIEW_FXML = "GpaRequestLog.fxml";
	private GpaRequestLogController controller;
	
	
	@Override
	public void start(Stage primaryStage){
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
	        
	        /*
	        Thread t = new Thread(new Runnable() {
				@Override
				public void run() {				
					new GpaRequestLogConsumer(controller);
				}	        	
	        });
	      
	        t.setName(GpaRequestLogConsumer.TOPIC_NAME);
	        t.setDaemon(true);
	        t.start();
	         */
		} catch (Exception e) {
			e.printStackTrace();
		}	
			
		return pane;
	}
}
