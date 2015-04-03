package com.foursoft.gpa.clientfx;

import java.util.ArrayList;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.controlsfx.dialog.Dialogs;

import com.foursoft.gpa.clientfx.controller.ArticleMasterController;
import com.foursoft.gpa.db.Femart;

public class ArticleMasterApp extends Application {

	public static final String VIEW_FXML = "ArticleMaster.fxml";
	
	private Stage primaryStage;
	
	public void start(){
		 // Load the fxml file
		try {
			Femart femart= new Femart();
			ArrayList<TreeMap<String, String>> records=femart.readFemart();
			
			if(records!=null && records.size()>0){
		        FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+VIEW_FXML));
		        Pane page = (Pane) loader.load();	                  
		        ArticleMasterController controller=loader.getController();
		        Stage stage=new Stage();
		        stage.setResizable(false);
		        
		        stage.initModality(Modality.APPLICATION_MODAL);
		        stage.setTitle("Add or change articles");
		        
		        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

					@Override
					public void handle(WindowEvent event) {
						System.out.println("Exit screen");
						
					}
		        	
		        });

		        
		        controller.setRecords(records);	
		        controller.setNext();
		        
		        Scene  scene = new Scene(page);   	        
		        stage.setScene(scene);
		        stage.show();
			}else{
				Dialogs.create()
		        .owner(primaryStage)
		        .title("Information Dialog")
		        .masthead(null)
		        .message("There are no articles found.")
		        .showInformation();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage=primaryStage;
		start();
			
	}
	
	@Override
	  public void stop() {
		
	}
}
