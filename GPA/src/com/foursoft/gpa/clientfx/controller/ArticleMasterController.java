package com.foursoft.gpa.clientfx.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.WritableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import com.foursoft.gpa.clientfx.img.ImageLoader;
import com.foursoft.gpa.db.Article;
import com.foursoft.gpa.db.Arttar;
import com.foursoft.gpa.db.Femart;
import com.foursoft.gpa.utils.DateUtils;

public class ArticleMasterController {
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="runInBatch"
    private CheckBox runInBatch; // Value injected by FXMLLoader

    @FXML // fx:id="tariffCode"
    private TextField tariffCode; // Value injected by FXMLLoader

    @FXML // fx:id="progressPane"
    private BorderPane progressPane; // Value injected by FXMLLoader

    @FXML // fx:id="dutyRate"
    private TextField dutyRate; // Value injected by FXMLLoader

    @FXML // fx:id="description"
    private Label description; // Value injected by FXMLLoader

    @FXML // fx:id="okButton"
    private Button okButton; // Value injected by FXMLLoader

    @FXML // fx:id="articleCode"
    private Label articleCode; // Value injected by FXMLLoader

    @FXML // fx:id="customer"
    private Label customer; // Value injected by FXMLLoader
      
    @FXML
    private Parent root;
    
    public static Iterator<TreeMap<String, String>> iterator;
    
    public static int totalRemaining;
    public int totalProcessed;
    
    public static ArrayList<TreeMap<String, String>> records;
    
    private ProgressBar progressBar = new ProgressBar();
        
	private Article article=new Article();
	
	private Arttar arttar=new Arttar();
	
	private Femart femart= new Femart();
	
	private TreeMap<String, String> curRecord;
	
	public static boolean continueProcess=true;
			
	private static final long ANIMATION_DURATION=1000;

	
	
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
	    	
	    	 assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'ArticleMaster.fxml'.";
	         assert runInBatch != null : "fx:id=\"runInBatch\" was not injected: check your FXML file 'ArticleMaster.fxml'.";
	         assert tariffCode != null : "fx:id=\"tariffCode\" was not injected: check your FXML file 'ArticleMaster.fxml'.";
	         assert progressPane != null : "fx:id=\"progressPane\" was not injected: check your FXML file 'ArticleMaster.fxml'.";
	         assert dutyRate != null : "fx:id=\"dutyRate\" was not injected: check your FXML file 'ArticleMaster.fxml'.";
	         assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'ArticleMaster.fxml'.";
	         assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'ArticleMaster.fxml'.";
	         assert articleCode != null : "fx:id=\"articleCode\" was not injected: check your FXML file 'ArticleMaster.fxml'.";
	         assert customer != null : "fx:id=\"customer\" was not injected: check your FXML file 'ArticleMaster.fxml'.";

	        
	        dutyRate.addEventFilter(KeyEvent.KEY_TYPED,	new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {

						if (!event.getCharacter().matches("\\d*\\.?\\d*")) {
							event.consume();
						}
						
						if(dutyRate.getText().length()>=17){
							event.consume();
						}

					}

				});
	        
	        
	        cancelButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					setNext();				
				}
	        	
	        });
	        
	        okButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {	
					
					if(!runInBatch.isSelected()){
						processRecord(curRecord);				
						setNext();	
					}else{
						auto();
					}
								
				}
	        	
	        });
	        
	        progressBar.setPrefWidth(300);

	    }
	
	public void setRecords(ArrayList<TreeMap<String, String>> records) {
		ArticleMasterController.records = records;
		ArticleMasterController.totalRemaining=records.size();
		ArticleMasterController.iterator = ArticleMasterController.records.iterator();
	}
		
	public void setNext(){
		getNext();
		setScreen();
	}
	
	public void setScreen(){
		if(curRecord!=null){
			customer.setText(curRecord.get(Femart.DB_ACCOUNT_ID));
			articleCode.setText(curRecord.get(Femart.DB_FEMART_CODE));
			description.setText(curRecord.get(Femart.DB_FEMART_DESC));
			tariffCode.setText(curRecord.get(Femart.DB_FEMART_TARIFF_CODE));
			dutyRate.setText(curRecord.get(Femart.DB_FEMART_IMP_DUTY));
		}
	}
	
	private void getNext(){
		
		curRecord=null;
		if (iterator.hasNext()) {
			curRecord=iterator.next();
			totalRemaining--;
		}else{
			if(!runInBatch.isSelected()){
				Window window = cancelButton.getScene().getWindow();
				if (window instanceof Stage){
		            ((Stage) window).close();
		        }
			}
		}
		
	}
	
	private void processRecord(TreeMap<String, String> record){
		
		if(article.insertUpdate(mapToArticle(record))){
			if(arttar.insertUpdate(mapToArttar(record))){
				//delete from femart				
				//femart.deleteFemartRecord(record.get(Femart.DB_ACCOUNT_ID), record.get(Femart.DB_FEMART_CODE));
			}
		}
	}
		
	private TreeMap<String, String> mapToArticle(TreeMap<String, String> in){
		
		TreeMap<String, String> articleDetail=article.initRecord();
		articleDetail.put(Article.DB_ARTICLE_CODE, in.get(Femart.DB_FEMART_CODE));
		articleDetail.put(Article.DB_ACCOUNT_ID, in.get(Femart.DB_ACCOUNT_ID));
		articleDetail.put(Article.DB_ARTICLE_DESCRIPTION, in.get(Femart.DB_FEMART_DESC));

		
		return articleDetail;
	}
	
	private TreeMap<String, String> mapToArttar(TreeMap<String, String> in){
		TreeMap<String, String> arttarDetail=arttar.initRecord();
		SimpleDateFormat formatInhouse=new SimpleDateFormat(DateUtils.FORMAT_INHOUSE);
		
		arttarDetail.put(Arttar.DB_ARTICLE_CODE, in.get(Femart.DB_FEMART_CODE));
		arttarDetail.put(Arttar.DB_ACCOUNT_ID,in.get(Femart.DB_ACCOUNT_ID));
		arttarDetail.put(Arttar.DB_ARTTAR_START_DATE, formatInhouse.format(new Date()));
		arttarDetail.put(Arttar.DB_TARIFF_CODE, in.get(Femart.DB_FEMART_TARIFF_CODE));
		
		
		
		
		return arttarDetail;
	}
	
	private void auto(){
		Node progressBox=getProgressBarNode();
		Task<Void> task = new Task<Void>() {
			@Override
			public void run() {	
				continueProcess=true;				
				try {
					//Wait until window animation ready
					Thread.sleep(ANIMATION_DURATION);										
					Platform.runLater(new Runnable() {
		                @Override public void run() {
		                	//Crete progress bar		
		                	
		                	ArrayList<HBox> hboxList= new ArrayList<HBox>();
		                	List<Node> list=DashboardNewController.progressContainersPool.getChildren();
		                	
		                	for(Node node: list){		                		
		                		hboxList.add((HBox)node);
		                	}
		                	DashboardNewController.progressContainersPool.getChildren().clear();
		                	DashboardNewController.progressContainersPool.getChildren().add(progressBox);	
		                	DashboardNewController.progressContainersPool.getChildren().addAll(hboxList);	
		                	
		                	//close animated window
			               	((Stage)cancelButton.getScene().getWindow()).close();		            	
		               }
		            });

					totalProcessed=1;				
				    if(totalProcessed>1){
				    	//Scip first read 
				    	getNext();
				    }
					
                	while(curRecord!=null && ArticleMasterController.continueProcess){
                		processRecord(curRecord);
                		updateProgress(totalProcessed, totalRemaining);
                		getNext();
                		totalProcessed++;
                		try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}               		
                	}
             		//progressPane.toBack();             		
				} catch (Exception e) {
					e.printStackTrace();
				}	
				Platform.runLater(new Runnable() {
                     @Override public void run() {
                    	 
                    	 DashboardNewController.progressContainersPool.getChildren().remove(progressBox);
                    	 DashboardNewController.progressContainersPool.getChildren().add(getStatusBarNode());
                     }
                 });
						
			}

			@Override
			protected Void call() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
		progressBar.progressProperty().bind(task.progressProperty());
		
		animateWindowClosing();
		new Thread(task).start();
		

	}
	
	private void animateWindowClosing(){
		
		Window window = root.getScene().getWindow();
		
		if (window instanceof Stage){
			Window parentWindow =DashboardNewController.progressContainersPool.getScene().getWindow();			
			WritableValue<Double> writableHeight = new WritableValue<Double>() {
	            @Override
	            public Double getValue() {
	                return window.getHeight();
	            }

	            @Override
	            public void setValue(Double value) {
	            	window.setHeight(value);
	            }
	        };

	        WritableValue<Double> writableWidth = new WritableValue<Double>() {
	            @Override
	            public Double getValue() {
	                return window.getWidth();
	            }

	            @Override
	            public void setValue(Double value) {
	            	window.setWidth(value);
	            }
	        };
	        
	        WritableValue<Double> writableXPosition = new WritableValue<Double>() {
	            @Override
	            public Double getValue() {
	                return window.getX();
	            }

	            @Override
	            public void setValue(Double value) {
	            	window.setX(value);
	            }
	        };
	        
	        WritableValue<Double> writableYPosition = new WritableValue<Double>() {
	            @Override
	            public Double getValue() {
	                return window.getY();
	            }

	            @Override
	            public void setValue(Double value) {
	            	window.setY(value);
	            }
	        };
	        
	        //Left lower corner of the main application
	        double targetX = parentWindow.getX()+10;
			double targetY = parentWindow.getY()+parentWindow.getHeight()-50;
			
	        Timeline timeLine = new Timeline();

	        KeyValue keyWidth = new KeyValue(writableWidth,0.0);
	        KeyValue keyHeight = new KeyValue(writableHeight,0.0);
	        KeyValue keyX = new KeyValue(writableXPosition,targetX);
	        KeyValue keyY = new KeyValue(writableYPosition,targetY);	        
	        KeyValue keyOpacity=new KeyValue(window.opacityProperty(),0.0);  
	        
	        KeyFrame keyFrame = new KeyFrame(Duration.millis(2500), keyWidth,keyX,keyHeight,keyY,keyOpacity);
	        
	        timeLine.getKeyFrames().addAll(keyFrame);

	        timeLine.play();
	       	        
        }
	}
	
	private Node getProgressBarNode(){
		HBox node=new HBox(); 
		
		node.setSpacing(20);
		node.setAlignment(Pos.CENTER_LEFT);
		//node.setPadding(new Insets(5));		            	
    	node.getChildren().add(progressBar);
    	node.getChildren().add(getIconStop("progress_stop.gif"));
    	node.getChildren().add(new Label("Article interface"));
				
		return node;
	}
	
	private Node getStatusBarNode(){
		HBox node=new HBox(); 
		
		node.setSpacing(20);
		node.setAlignment(Pos.CENTER_LEFT);
		//node.setPadding(new Insets(5));	
    	node.getChildren().add(new Label("Processed: "+totalProcessed+" records."));
    	node.getChildren().add(getIconStop("clear.gif"));
				
		return node;
	}
	
	private Node getIconStop(String iconFile){
		
		Button node= new Button();
		node.getStyleClass().add("clickableButton");
		
		node.setGraphic(new ImageView(new Image(ImageLoader.class.getResourceAsStream(iconFile))));
					
		node.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				
				
				HBox box=(HBox) node.getParent();
				VBox container=(VBox) box.getParent();
				container.getChildren().remove(box);
				
				continueProcess=false;
			}
			
		});
		
		return node;
	}
}
