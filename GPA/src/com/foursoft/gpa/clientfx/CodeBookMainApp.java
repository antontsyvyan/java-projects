package com.foursoft.gpa.clientfx;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.foursoft.gpa.clientfx.controller.CodeBookController;

public class CodeBookMainApp {

	public static final String VIEW_FXML = "CodeBook.fxml";
	
	private String language;
	private String type;
	private String codeTable;
	
	
	
	public CodeBookMainApp() {

	}

	public CodeBookMainApp(String language, String type, String codeTable) {
		this.language=language;
		this.type=type;
		this.codeTable=codeTable;
	}


	public void start() {
		
		try {
			 // Load the fxml file
	        FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+VIEW_FXML));
	        Pane overviewPage = (Pane) loader.load();	           
	        
	        CodeBookController controller=loader.getController();
            
            controller.setCodeTable(codeTable);
            controller.setLanguage(language);
            controller.setType(type);
            
            controller.populateData();
                                        
            Stage stage= new Stage();
	        stage.setTitle("Sagitta Code Book");
	        
	        Scene  scene = new Scene(overviewPage);                            	
	        stage.setScene(scene);
	        stage.show();
	        
		} catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
	}
	
	public Node getNode(){
		Pane pane=null;
		try {
			 FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+VIEW_FXML));
		     pane = (Pane) loader.load();
		     
		     CodeBookController controller=loader.getController();
	         
	         controller.setCodeTable(codeTable);
	         controller.setLanguage(language);
	         controller.setType(type);
	         
	         controller.populateData();
         
		} catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
	     return pane;
	     
	}
}
