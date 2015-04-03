package com.foursoft.gpa.clientfx;

import java.io.IOException;

import com.foursoft.gpa.clientfx.controller.ArticleAddEditController;
import com.foursoft.gpa.clientfx.model.ArticleModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ArticleAddEditMainApp {
	
	
	public static final String VIEW_FXML = "ArticleAddEdit.fxml";
	private ArticleModel articleModel=new ArticleModel();
	
	public ArticleAddEditMainApp(ArticleModel articleModel){
		this.articleModel=articleModel;
	}
	

	
	public void start() {
		try {
			// Load the fxml file
			FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(Dashboard.VIEW_FOLDER+ VIEW_FXML));
			Pane overviewPage = (Pane) loader.load();
			Scene scene = new Scene(overviewPage);
			
			scene.getStylesheets().add(DashboardNew.class.getResource(DashboardNew.CSS_FOLDER+DashboardNew.VIEW_CSS).toExternalForm());
			ArticleAddEditController controller=loader.getController();
			controller.setArticleModel(articleModel);
			controller.init();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			
			stage.setTitle("Add Article");
			
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
		
	}
	
}
