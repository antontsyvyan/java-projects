package com.foursoft.gpa.clientfx.controller;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import com.foursoft.gpa.GpaCore;
import com.foursoft.gpa.clientfx.Dashboard;
import com.foursoft.gpa.clientfx.DiskErrorsMainApp;
import com.foursoft.gpa.clientfx.model.IntlogModel;
import com.foursoft.gpa.db.Intlog;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.Uploader;

public class FileInterfaceLogController {
	
	 	
	public static String uploadErrorMessage;
	public static boolean uploaded=false;
	
	private DiskErrorsMainApp app=new DiskErrorsMainApp();
	private DateUtils du= new DateUtils();	
	private File selectedFile;
	private ObservableList<IntlogModel> errorData = FXCollections.observableArrayList();
	
	private Timeline uploadTimer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1),	new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(uploaded){
				uploadTimer.stop();
			}
			if(!uploadErrorMessage.equals("")){
				Label message=new Label(uploadErrorMessage);
			    HBox box= new HBox();
			    box.getChildren().addAll(message);
			    progressContainer.getChildren().addAll(box);
			}
		}
	 }));
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="errorGrid"
    private TableView<IntlogModel> errorGrid; // Value injected by FXMLLoader

    @FXML // fx:id="timeColumn"
    private TableColumn<IntlogModel, String> timeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="buttonSync"
    private Button buttonSync; // Value injected by FXMLLoader

    @FXML // fx:id="progressContainer"
    private VBox progressContainer; // Value injected by FXMLLoader

    @FXML // fx:id="selectFileButton"
    private Button selectFileButton; // Value injected by FXMLLoader

    @FXML // fx:id="errorColumn"
    private TableColumn<IntlogModel, TreeMap<String, String>> errorColumn; // Value injected by FXMLLoader

    @FXML // fx:id="dateColumn"
    private TableColumn<IntlogModel, String> dateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="fileNameColumn"
    private TableColumn<IntlogModel, String> fileNameColumn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert errorGrid != null : "fx:id=\"errorGrid\" was not injected: check your FXML file 'FileInterfaceLog.fxml'.";
        assert timeColumn != null : "fx:id=\"timeColumn\" was not injected: check your FXML file 'FileInterfaceLog.fxml'.";
        assert buttonSync != null : "fx:id=\"buttonSync\" was not injected: check your FXML file 'FileInterfaceLog.fxml'.";
        assert progressContainer != null : "fx:id=\"progressContainer\" was not injected: check your FXML file 'FileInterfaceLog.fxml'.";
        assert selectFileButton != null : "fx:id=\"selectFileButton\" was not injected: check your FXML file 'FileInterfaceLog.fxml'.";
        assert errorColumn != null : "fx:id=\"errorColumn\" was not injected: check your FXML file 'FileInterfaceLog.fxml'.";
        assert dateColumn != null : "fx:id=\"dateColumn\" was not injected: check your FXML file 'FileInterfaceLog.fxml'.";
        assert fileNameColumn != null : "fx:id=\"fileNameColumn\" was not injected: check your FXML file 'FileInterfaceLog.fxml'.";
        
        
        fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().procDateProperty());
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().procTimeProperty());
		errorColumn.setCellValueFactory(cellData -> cellData.getValue().intlogDetailsProperty());
		
		errorGrid.setFixedCellSize(30);
		
		dateColumn.setCellFactory(new Callback<TableColumn<IntlogModel,String>, TableCell<IntlogModel,String>>() {
			@Override
			public TableCell<IntlogModel, String> call(TableColumn<IntlogModel, String> parm) {
				return new TableCell<IntlogModel, String>() {
					public void updateItem(String item, boolean empty) {
						setText(item);
					}
				};
			}
			
		});
		
		//Cell Factories
		timeColumn.setCellFactory(new Callback<TableColumn<IntlogModel,String>, TableCell<IntlogModel,String>>() {

			@Override
			public TableCell<IntlogModel, String> call(TableColumn<IntlogModel, String> parm) {
				return new TableCell<IntlogModel, String>() {					
					@Override
					public void updateItem(String item, boolean empty) {						
						if(!empty && item!=null){
							item=du.convertTime(String.format("%06d",Integer.parseInt(item)));
						}
						setText(item);
					}
				};				
			}
		});
		
		errorColumn.setCellFactory(new Callback<TableColumn<IntlogModel,TreeMap<String, String>>, TableCell<IntlogModel,TreeMap<String, String>>>() {		
			@Override
			public TableCell<IntlogModel, TreeMap<String, String>> call(TableColumn<IntlogModel, TreeMap<String, String>> param) {
				return new TableCell<IntlogModel, TreeMap<String, String>>(){	
					@Override
					public void updateItem(TreeMap<String, String> item, boolean empty) {
						//this.setText(item.getStatus());
						HBox box= new HBox(); 
						box.setSpacing(30) ;
						
						if(item!=null){
							
							ImageView imageview = new ImageView();
							imageview.setFitHeight(16);
							imageview.setFitWidth(16);
							imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/ok.png")); 
							
							Button button=new Button();
							button.setLayoutX(100);
							button.setLayoutY(60);
							button.setPrefWidth(80);
							
							boolean showButton=false;
						    
							if (item.get(Intlog.DB_INTLOG_STAT).equals(Processors.REQUEST_STATUS_PROCESSED)) {								
								Label statusLabel=new Label(item.get(Intlog.DB_INTLOG_STAT));
								box.getChildren().addAll(imageview,statusLabel); 
								//SETTING ALL THE GRAPHICS COMPONENT FOR CELL 
								this.setTooltip(new Tooltip(item.get(Intlog.DB_INTLOG_STAT)));
																
							}else if (item.get(Intlog.DB_INTLOG_STAT).equals(Processors.REQUEST_STATUS_ERROR)) {
								button.setText("Errors");							    
								button.getStyleClass().add("error-button");
							    imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/error.png")); 
							    showButton=true;
							    
							}else if (item.get(Intlog.DB_INTLOG_STAT).equals(Processors.REQUEST_STATUS_WARNING)) {
								button.setText("Warnings");							    
								button.getStyleClass().add("warning-button");
								showButton=true;
							    
							}							
							if(showButton){
								button.setOnAction(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										if(item.get(Intlog.DB_INTLOG_ERR_MSG).startsWith(GpaCore.ERROR_PREFIX)){
											app =new DiskErrorsMainApp(item.get(Intlog.DB_INTLOG_ERR_MSG).substring(GpaCore.ERROR_PREFIX.length()));
										}else{
											app =new DiskErrorsMainApp(null);
											app.setErrorString(item.get(Intlog.DB_INTLOG_ERR_MSG));
										}										
										app.start();
									}

							    });
							    
							    box.getChildren().addAll(imageview,button);
							}						
							
						}
						setGraphic(box); 
					}
									
				};	
			}
			
		});
		selectFileButton.setOnAction(new EventHandler<ActionEvent>() {		
	          @Override
	          public void handle(ActionEvent event) {
	              FileChooser fileChooser = new FileChooser();
	 
	              //Set extension filter
	              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("interface files (*.csv, *xml)", "*.csv", "*.xml");
	              fileChooser.getExtensionFilters().add(extFilter);
	             
	              //Show open file dialog
	              selectedFile = fileChooser.showOpenDialog(null);
	              if(selectedFile!=null){
		              if(selectedFile.isFile()){
		            	  //Start uploading thread
		            	  Uploader t=new Uploader();
		            	  t.setName("UPLOADER");
		            	  t.setSourceFile(selectedFile);
		            	  //t.setDaemon(true);
		            	  t.start();		            	  
		            	  
		            	  if(uploadTimer.getStatus().equals(Animation.Status.STOPPED)){
		            		  selectFileButton.setDisable(true);
		            		  progressContainer.getChildren().clear();
		            		  uploadTimer.setCycleCount(Timeline.INDEFINITE);
			            	  uploadTimer.play();
		            	  }	            	  
		              }
	              }
	          }
	      });	

    }
	public void populateGridData(){
		int selectedErrorRow=-1;
		Intlog intlog=new Intlog();
		
		if(errorData !=null){		
			try{
				selectedErrorRow =errorGrid.getSelectionModel().getSelectedCells().get(0).getRow();
			}catch(Exception ex){
				
			}
			
			errorData.clear();
			errorData.removeAll(errorData);
			
			ArrayList<IntlogModel> intlogList=intlog.getIntlogModelCollection();
			for (IntlogModel item : intlogList) {
				errorData.add(item);
			}
			
			//errorData.addAll(intlog.getIntlogModelCollection()); 
			errorGrid.setItems(errorData);
						
			if(selectedErrorRow>=0){
				errorGrid.getSelectionModel().select(selectedErrorRow);
			}
			
		}
	}
	
	public void checkProgress(){
		
		if(Protocol.globalTotalRows>0){
			 selectFileButton.setDisable(true);
			 Protocol pt= new Protocol();	
			 ProgressBar progressBar=new ProgressBar();	       
		     double progress=pt.calculateCsvProgress();		       
		     progressBar.setProgress(progress); 
		     
		     DecimalFormat df = new DecimalFormat("#");
		     //Label statusLabel=new Label(pt.getStatusDescription());
		     Label fileName=new Label("Processing file: "+Protocol.globalCsvFileName);
		     HBox box= new HBox(); 
			 box.setSpacing(30) ;
		     Label progressLabel=new Label(String.valueOf(df.format(progress*100.0))+"%");
		     Label progressLabel2=new Label("("+Protocol.globalTotalProcessedRows+" from "+Protocol.globalTotalRows+")");
		     box.getChildren().addAll(progressBar,progressLabel,progressLabel2,new Label(getRemainingTime()));
		     progressContainer.getChildren().clear();
		     progressContainer.getChildren().addAll(fileName,box);
		}else{
			 selectFileButton.setDisable(false);
			 progressContainer.getChildren().clear();
		}
		
	}
	
	private String getRemainingTime(){
		String rem="";
		
		long duration = Duration.between(Instant.ofEpochMilli(Protocol.globalCsvStartTime),Instant.now()).toMillis()/1000;
		if(duration>0 && Protocol.globalTotalProcessedRows>0){
			long speed=Protocol.globalTotalProcessedRows/duration;
			if(speed>0){
				long rest=(Protocol.globalTotalRows-Protocol.globalTotalProcessedRows)/speed;		
				rem=String.valueOf(LocalTime.MIN.plusSeconds(rest).toString());				
			}
		}		

		return "Remaining time is "+rem ;
	}
}
