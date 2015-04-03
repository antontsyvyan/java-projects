package com.foursoft.gpa.clientfx;

import java.util.prefs.Preferences;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.foursoft.gpa.clientfx.controller.DashboardController;
import com.foursoft.gpa.utils.Resources;

public class Dashboard extends Application {

	public static final String VIEW_FOLDER="view/";
	public static final String IMAGE_FOLDER="img/";
	public static final String CSS_FOLDER="css/";
	public static final String LOG_DIR="log/";
	public static final String VIEW_FXML="Dashboard.fxml";
	public static final String VIEW_CSS="dashboard.css";
	public static final String CALENDAR_CSS="calendarstyle.css";
	public static final String ICO_FILE="logo.ico";
	
	public static final String STAGE_X="stage.x";	
	public static final String STAGE_Y="stage.y";
	public static final String STAGE_WIDTH="stage.width";
	public static final String STAGE_HEIGHT="stage.height";
	
	private static Preferences userPrefs  = Preferences.userRoot().node(Application.class.getName());
	private Stage stage;

		
	public static void main(String[] args) {

		if(args!=null && args.length>0){
			Resources.setPropertiesFile(args[0]);
		}
		
		userPrefs  = Preferences.userRoot().node(Application.class.getName()+Resources.getPropertiesFile());

		launch(args);
	}
	
	public Dashboard() {

	}
	

	@Override
	public void start(Stage stage) throws Exception {
		
		this.stage=stage;
		 // Load the fxml file
        FXMLLoader loader = new FXMLLoader(Dashboard.class.getResource(VIEW_FOLDER+VIEW_FXML));
        VBox overviewPage = (VBox) loader.load();
        
        DashboardController controller=loader.getController();
        controller.populateGridData();
        controller.createCalendar();
                
        Scene  scene = new Scene(overviewPage); 
        //Set style sheet for the whole screen
        scene.getStylesheets().add(Dashboard.class.getResource(CSS_FOLDER+VIEW_CSS).toExternalForm());
        scene.getStylesheets().add(Dashboard.class.getResource(CSS_FOLDER+CALENDAR_CSS).toExternalForm());
        stage.setTitle("Dashboard");
        String icoPath=Dashboard.class.getResource(IMAGE_FOLDER+ICO_FILE).toString();
        System.out.println(icoPath);
        stage.setScene(scene);

        stage.getIcons().add(new Image(icoPath)); 
        
        //restore current size and position (use x=100, y=100, width=400, height=400 as default)
        stage.setScene(scene);
        stage.setX(userPrefs.getDouble(STAGE_X, 100));
        stage.setY(userPrefs.getDouble(STAGE_Y, 100));
        stage.setWidth(userPrefs.getDouble(STAGE_WIDTH, 800));
        stage.setHeight(userPrefs.getDouble(STAGE_HEIGHT, 600));
        
        stage.show();
       
        //Submit background tasks
		Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1),	new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controller.populateGridData();
			}
		}));
   
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        
        Timeline timer2 = new Timeline(new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controller.checkProgress();
			}
		}));
   
        timer2.setCycleCount(Timeline.INDEFINITE);
        timer2.play();
        
        //Start synchronization service
        Thread t=new SynchronizationService();
        t.setDaemon(true);
		t.start();     
		
	}
	
	@Override
	  public void stop() {
		//save current size and position
	    userPrefs.putDouble(STAGE_X, stage.getX());
	    userPrefs.putDouble(STAGE_Y, stage.getY());
	    userPrefs.putDouble(STAGE_WIDTH, stage.getWidth());
	    userPrefs.putDouble(STAGE_HEIGHT, stage.getHeight());
	  }

			

}
