package com.foursoft.gpa.clientfx.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;

import com.foursoft.gpa.GpaCore;
import com.foursoft.gpa.clientfx.ArticleListMainApp;
import com.foursoft.gpa.clientfx.ArticleMasterApp;
import com.foursoft.gpa.clientfx.CodeBookMainApp;
import com.foursoft.gpa.clientfx.Dashboard;
import com.foursoft.gpa.clientfx.DiskErrorsMainApp;
import com.foursoft.gpa.clientfx.EntResetMainApp;
import com.foursoft.gpa.clientfx.ImpDetailsMainApp;
import com.foursoft.gpa.clientfx.ImpRequestMainApp;
import com.foursoft.gpa.clientfx.ImpResetMainApp;
import com.foursoft.gpa.clientfx.RateListMainApp;
import com.foursoft.gpa.clientfx.SynchronizationService;
import com.foursoft.gpa.clientfx.calendar.CalendarView;
import com.foursoft.gpa.clientfx.model.IntlogModel;
import com.foursoft.gpa.clientfx.model.RequestModel;
import com.foursoft.gpa.db.Cdbksec;
import com.foursoft.gpa.db.Cdbktable;
import com.foursoft.gpa.db.Gpareq;
import com.foursoft.gpa.db.Intlog;
import com.foursoft.gpa.reporting.TotalsDetailsReport;
import com.foursoft.gpa.utils.ApplicationConstants;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.FileUtils;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.Resources;
import com.foursoft.gpa.utils.TopFileFilter;
import com.foursoft.gpa.utils.Uploader;

public class DashboardController {

	// Views
	public static final String DOMPROC_REQUEST_FXML = "DomprocRequest.fxml";
	public static final String CODE_BOOK_FXML = "CodeBook.fxml";

	public static final String VIEW_FOLDER = "view/";

	public static final String ITEM_SEPARATOR = "[SEP]";
	
	
	//Menu items
	
	public static final String INTERFACES="Interfaces";
	public static final String SETUP="Setup";
	public static final String CODEBOOK="Codebook";
	public static final String GPA="GPA";
	public static final String ADMIN="Admin";
	public static final String HELP="Help";
	
	public static final String PRODUCT="Product";
	public static final String COMPANY="Company";
	public static final String INBOUND="Inbound";
		
	public static final String AUTO_JOB="Auto job";
	public static final String SETTINGS="Settings";
	
	public static final String IMP_REQUEST="IMP Request";
	public static final String ENT_REQUEST="ENT Request";
	public static final String IMP_DETAILS="IMP Details";
	public static final String ENT_DETAILS="ENT Details";
	public static final String TOTALS="Totals";
	
	public static final String SAGITTA_CODEBOOK = "[SAG_COD]";
	public static final String EXCISE_TAX = "Excise tax";
	public static final String RATES = "Rate list";
	public static final String ARTICLES = "Articles";
	
	public static final String RESET_IMP="Reset IMP";
	public static final String RESET_ENT="Reset ENT";
	public static final String LOGGING="Logging";
	public static final String ABOUT="About";
	
	
	private DiskErrorsMainApp app=new DiskErrorsMainApp();
	
	private CalendarView calendarView;
	
	
	public static final String[] MAIN_MENU = new String[] { INTERFACES, SETUP, CODEBOOK, GPA, ADMIN, HELP };
	public static final String[][] ITEMS = new String[][] {
			{ PRODUCT, COMPANY, INBOUND },
			{ AUTO_JOB, SETTINGS },
			{ EXCISE_TAX, RATES, ARTICLES, ITEM_SEPARATOR, SAGITTA_CODEBOOK },
			{ ENT_DETAILS, ENT_REQUEST, ITEM_SEPARATOR, IMP_DETAILS,IMP_REQUEST, ITEM_SEPARATOR, TOTALS },
			{ RESET_IMP,RESET_ENT,ITEM_SEPARATOR,LOGGING },
			{ABOUT}
	};
	
	public static final boolean[][] ITEMS_ENABLE = new boolean[][] {
		{ true, false, false },
		{ false, false },
		{ false, true, false, true, true },
		{ false, false, true, true, true, true, true },
		{ true,true,true,true },
		{true}
	};
	
	//private Color white = Color.WHITE;
	//private Color black = Color.BLACK;
	//private Color green =Color.rgb(0, 170, 0);
	//private Color red =Color.rgb(240, 0, 0);
	//private Color blue =Color.rgb(0, 140, 255);

	public static String uploadErrorMessage;
	public static boolean uploaded=false;
	
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
	
	@FXML
	// ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML
	// URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML
	// fx:id="menuBar"
	private MenuBar menuBar; // Value injected by FXMLLoader
	
    @FXML // fx:id="requestGrid"
    private TableView<RequestModel> requestGrid; // Value injected by FXMLLoader

    @FXML // fx:id="detailsColumn"
    private TableColumn<RequestModel, TreeMap<String, String>> detailsColumn; // Value injected by FXMLLoader

    @FXML // fx:id="statusColumn"
    private TableColumn<RequestModel, String> statusColumn; // Value injected by FXMLLoader

    @FXML // fx:id="periodColumn"
    private TableColumn<RequestModel, String> periodColumn; // Value injected by FXMLLoader

    @FXML // fx:id="typeColumn"
    private TableColumn<RequestModel, String> typeColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="idColumn"
    private TableColumn<RequestModel, String> idColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="requestGrid"
    private TableView<IntlogModel> errorGrid; // Value injected by FXMLLoader
    
    @FXML // fx:id="fileNameColumn"
    private TableColumn<IntlogModel, String> fileNameColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="dateColumn"
    private TableColumn<IntlogModel, String> dateColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="timeColumn"
    private TableColumn<IntlogModel, String> timeColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="errorColumn"
    private TableColumn<IntlogModel, TreeMap<String, String>> errorColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="buttonSync"
    private Button buttonSync; // Value injected by FXMLLoader
    
    @FXML // fx:id="calendarContainer"
    private HBox calendarContainer; // Value injected by FXMLLoader
    
    @FXML // fx:id="progressContainer"
    private VBox progressContainer; // Value injected by FXMLLoader
    
    @FXML // fx:id="fileContainer"
    private HBox fileContainer; // Value injected by FXMLLoader   
    
    @FXML // fx:id="selectFileButton"
    private Button selectFileButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="logoImageView"
    private ImageView logoImageView;
      
	private ArrayList<TreeMap<String, String>> data;
	private ObservableList<RequestModel> requestData = FXCollections.observableArrayList();
	private ObservableList<IntlogModel> errorData = FXCollections.observableArrayList();
	
	private DateUtils du= new DateUtils();
	
	private Socket progressSocket=null;
	
	private File selectedFile;
	

	@FXML
	// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert requestGrid != null : "fx:id=\"requestGrid\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert detailsColumn != null : "fx:id=\"detailsColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert statusColumn != null : "fx:id=\"statusColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert periodColumn != null : "fx:id=\"periodColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert calendarContainer != null : "fx:id=\"calendarContainer\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert idColumn != null : "fx:id=\"idColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert buttonSync != null : "fx:id=\"buttonSync\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert progressContainer != null : "fx:id=\"progressContainer\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert fileContainer != null : "fx:id=\"fileContainer\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert selectFileButton != null : "fx:id=\"selectFileButton\" was not injected: check your FXML file 'Dashboard.fxml'.";
        
        assert errorGrid != null : "fx:id=\"requestGrid\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert fileNameColumn != null : "fx:id=\"fileNameColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert dateColumn != null : "fx:id=\"dateColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert timeColumn != null : "fx:id=\"timeColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert errorColumn != null : "fx:id=\"errorColumn\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert logoImageView != null : "fx:id=\"logoImageView\" was not injected: check your FXML file 'Dashboard.fxml'.";
           
		generateMenuBar();
		setLogo();
				
		//Add columns to table
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
		periodColumn.setCellValueFactory(cellData -> cellData.getValue().periodProperty());
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		detailsColumn.setCellValueFactory(cellData -> cellData.getValue().detailsProperty());
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		
		fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().procDateProperty());
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().procTimeProperty());
		errorColumn.setCellValueFactory(cellData -> cellData.getValue().intlogDetailsProperty());
		
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
							if (item.get(Intlog.DB_INTLOG_STAT).equals(Processors.REQUEST_STATUS_PROCESSED)) {

								imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/ok.png")); 
								Label statusLabel=new Label(item.get(Intlog.DB_INTLOG_STAT));
								box.getChildren().addAll(imageview,statusLabel); 
								//SETTING ALL THE GRAPHICS COMPONENT FOR CELL 
								this.setTooltip(new Tooltip(item.get(Intlog.DB_INTLOG_STAT)));
																
							}else 	if (item.get(Intlog.DB_INTLOG_STAT).equals(Processors.REQUEST_STATUS_ERROR)) {
								Button btn = new Button();
							    btn.setLayoutX(100);
							    btn.setLayoutY(60);
							    btn.setText("Errors");							    
							    btn.getStyleClass().add("error-button");
							    imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/error.png")); 
							    
							    btn.setOnAction(new EventHandler<ActionEvent>() {
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
							    
							    box.getChildren().addAll(imageview,btn); 
							    
							}else if (item.get(Intlog.DB_INTLOG_STAT).equals(Processors.REQUEST_STATUS_WARNING)) {
								 Button btnWarning = new Button();
								 btnWarning.setLayoutX(100);
								 btnWarning.setLayoutY(60);
								 btnWarning.setText("Warnings");							    
								 btnWarning.getStyleClass().add("warning-button");
								 imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/ok.png")); 
								 btnWarning.setOnAction(new EventHandler<ActionEvent>() {
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
							    
							    box.getChildren().addAll(imageview,btnWarning);
							}
							
							
						}
						setGraphic(box); 
					}
									
				};	
			}
			
		});
			
		statusColumn.setCellFactory(new Callback<TableColumn<RequestModel, String>, TableCell<RequestModel, String>>() {
				@Override
				public TableCell<RequestModel, String> call(TableColumn<RequestModel, String> arg0) {
					TableCell<RequestModel, String> cell = new TableCell<RequestModel, String>() {
						
						@Override
						public void updateItem(String item, boolean empty) {
							// default colors							
							//this.setTextFill(black);
							//this.setBackground(new Background(new BackgroundFill(white, null, null)));
							HBox box= new HBox(); 
							box.setSpacing(30) ;
							
							if(item!=null){								
								ImageView imageview = new ImageView();
								imageview.setFitHeight(16);
								imageview.setFitWidth(16); 
								//System.out.println(Dashboard.class.getResource("img").toString());
							
								if (item.equals(Processors.REQUEST_STATUS_NEW)) {
									//this.setTextFill(white);
									//this.setBackground(new Background(new BackgroundFill(blue, null, null)));
									imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/new.png")); 
								}
								
								if (item.equals(Processors.REQUEST_STATUS_RUNNING)) {
									//this.setTextFill(white);
									//this.setBackground(new Background(new BackgroundFill(green, null,null)));
									imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/busy.gif")); 
								}
	
								if (item.equals(Processors.REQUEST_STATUS_ERROR)) {
									//this.setTextFill(white);
									//this.setBackground(new Background(new BackgroundFill(red, null, null)));
									imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/error.png")); 
								}
	
								if (item.equals(Processors.REQUEST_STATUS_PROCESSED)) {
									//this.setTextFill(white);
									//this.setBackground(new Background(new BackgroundFill(green, null,null)));
									imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/ok.png")); 
								}
								
								box.getChildren().addAll(imageview); 
								//SETTING ALL THE GRAPHICS COMPONENT FOR CELL 
								this.setTooltip(new Tooltip(item));
								
							}
							this.setText(item);
							setGraphic(box); 							
						}
					};
					return cell;
				}

			});
		/*
		requestGrid.setRowFactory(new Callback<TableView<RequestModel>, TableRow<RequestModel>>(){
			TableRow<RequestModel> row = new TableRow<RequestModel>();
			@Override
			public TableRow<RequestModel> call(TableView<RequestModel> arg0) {
				row.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						System.out.println("row selected");
						
					}
				});
				return null;
			}
			
		});
		*/
		detailsColumn.setCellFactory(new Callback<TableColumn<RequestModel, TreeMap<String, String>>, TableCell<RequestModel, TreeMap<String, String>>>() {
			@Override
			public TableCell<RequestModel, TreeMap<String, String>> call(TableColumn<RequestModel, TreeMap<String, String>> arg0) {
				TableCell<RequestModel, TreeMap<String, String>> cell = new TableCell<RequestModel, TreeMap<String, String>>() {
					String details="";
					String errorDetails="";
					@Override
					public void updateItem(TreeMap<String, String> item, boolean empty) {
						//default colors
						//this.setText(item);
						//final WebView browser = new WebView();
				        //final WebEngine webEngine = browser.getEngine();
						HBox box= new HBox(); 
						box.setSpacing(30) ;
				        details="";
						if(item!=null){
							
							if(item.get(Gpareq.DB_GPAREQ_STAT).equals(Processors.REQUEST_STATUS_RUNNING)){
								//When running add progress bar
								
								   BufferedReader in;
								   PrintWriter out;
								   
								   int port=Integer.parseInt(Resources.getSetting("comm.server.port"));
								   try{
									   
									   progressSocket = new Socket(Resources.getSetting("comm.server.host"), port);

								       in = new BufferedReader( new InputStreamReader(progressSocket.getInputStream()));
								       out = new PrintWriter(progressSocket.getOutputStream(), true);
								       
								       out.println(Protocol.GET_PROGRESS);
								       
								       Protocol pt= new Protocol();							       
								       pt.parceProgressMessage(in.readLine());
								       ProgressBar progressBar=new ProgressBar();
								       
								       double progress=pt.calculateDiskProgress();
								       
								       progressBar.setProgress(progress);  
								       
								       DecimalFormat df = new DecimalFormat("#");
								       Label statusLabel=new Label(pt.getStatusDescription());
								       Label progressLabel=new Label(String.valueOf(df.format(progress*100.0))+"%");							       
								       box.getChildren().addAll(statusLabel,progressBar,progressLabel);
								       
								   }catch(Exception ex){
									   
								   }finally{
									   try {
										   progressSocket.close();
									} catch (Exception e) {

									}
								   }
								
								
							}else if(item.get(Gpareq.DB_GPAREQ_STAT).equals(Processors.REQUEST_STATUS_ERROR)){
								details=item.get(Gpareq.DB_GAPREQ_ERR_MSG);
								if(details.startsWith(GpaCore.ERROR_PREFIX)){
									details=details.substring(GpaCore.ERROR_PREFIX.length());
									Button btn = new Button();
								    btn.setLayoutX(100);
								    btn.setLayoutY(60);
								    btn.setText("Errors");							    
								    btn.getStyleClass().add("error-button");
								    
								    btn.setOnAction(new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											// TODO Auto-generated method stub
											app =new DiskErrorsMainApp(details);
											app.setShowLinks(true);
											app.start();
										}

								    });
								    
								    box.getChildren().addAll(btn);
								}else{
									Label lbl=new Label(details);
									box.getChildren().addAll(lbl);
								} 
				        	 }else{
				        		 if(!item.get(Gpareq.DB_GPAREQ_DISC_FILE).trim().equals("")){
				        			 details=item.get(Gpareq.DB_GPAREQ_DISC_FILE);
				        			 Button btn = new Button();
									 btn.setLayoutX(100);
									 btn.setLayoutY(60);
									 btn.setText("Totals");
									 btn.setOnAction(new EventHandler<ActionEvent>() {
									        public void handle(ActionEvent event) {
									        	TotalsDetailsReport tdr=new TotalsDetailsReport(item.get(Gpareq.DB_GPAREQ_FED_SYS),item.get(Gpareq.DB_GPAREQ_PER));
									        	tdr.generateReport();
									        	if(tdr.isValid()){
									        		tdr.show();
									        	}else{
									        		Dialogs.create()
													  .nativeTitleBar()
												      .title("Message")
												      .masthead(null)
												      .message( "There is nothing to show for period "+item.get(Gpareq.DB_GPAREQ_PER))
												      .showInformation();
									        	}
									        }
									  });
				        			 
									 errorDetails=item.get(Gpareq.DB_GAPREQ_ERR_MSG);
								     if(errorDetails.startsWith(GpaCore.ERROR_PREFIX)){
								    	 errorDetails=errorDetails.substring(GpaCore.ERROR_PREFIX.length());
										 Button btnWarning = new Button();
										 btnWarning.setLayoutX(100);
										 btnWarning.setLayoutY(60);
										 btnWarning.setText("Warnings");							    
										 btnWarning.getStyleClass().add("warning-button");
										 btnWarning.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												// TODO Auto-generated method stub
												app =new DiskErrorsMainApp(errorDetails);
												app.setShowLinks(true);
												app.start();
											}

									    });
									    
									    box.getChildren().add(btnWarning);
								     }
								 
								     //check if file exists
								     File f = new File(Resources.getSetting("server.root")+"\\"+FileUtils.DISK+"\\"+details);
								     if(f.exists()){
								     
									     Hyperlink link = new Hyperlink();
									     link.setText(details);
									     
									     link.setOnAction(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent arg0) {
													//System.out.println("Source: "+link.getText());
													FileChooser fileChooser = new FileChooser();
	
													//Set extension filter
													//FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
													//fileChooser.getExtensionFilters().add(extFilter);
													fileChooser.setInitialFileName(link.getText());
	
													//Show save file dialog
													File file=fileChooser.showSaveDialog(null);
													
													if(file!=null){
														//System.out.println("Destination: "+file.getPath());
														copyDiskFile(link.getText(),file.getPath());
													}
													
												}
												
										 });
									     box.getChildren().addAll(btn,link); 
					        		 }else{
					        			 Text  label= new Text (details);
					        			 label.getStyleClass().add("nonexisting-file");
					        			 box.getChildren().addAll(btn,label); 
					        		 }
								    								
									
				        		 }
				        	 }
																				
						}
						setGraphic(box); 
						
					}
				};
				return cell;
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
	
	@FXML
    void syncClicked(ActionEvent event) {
		 
		 if(SynchronizationService.synchronize){
			 SynchronizationService.synchronize=false;
			 buttonSync.setText("Start");
			 System.out.println("STOPPED");
		 }else{
			 SynchronizationService.synchronize=true;
			 buttonSync.setText("Stop");
			 System.out.println("STARTED");
		 }
		 
		 /*
		 
		 if(SynchronizationService.sync.getStatus().equals(Animation.Status.RUNNING)){
			 SynchronizationService.sync.pause();
			 buttonSync.setText("Start");
		 }else{
			 SynchronizationService.sync.play();
			 buttonSync.setText("Stop");
		 }
		 		 
		 System.out.println("Status: "+SynchronizationService.sync.getStatus());
		 */

    }

	 
	public void createCalendar(){
		calendarView = new CalendarView(Locale.ENGLISH);		
		calendarView.todayButtonTextProperty().set("Today");
		calendarView.setShowWeeks(false);
		calendarView.setShowTodayButton(false);
		calendarContainer.getChildren().add(calendarView);
	}
	
	/**
	 * Generates main menu bar
	 */
	private void generateMenuBar() {

		//Remove default items 
		menuBar.getMenus().clear();

		for (int i = 0; i < MAIN_MENU.length; i++) {

			Menu menu = new Menu(MAIN_MENU[i]);

			try {
				if (ITEMS[i] != null && ITEMS[i].length > 0) {
					for (int j = 0; j < ITEMS[i].length; j++) {

						boolean set = false;
						if (ITEMS[i][j].equals(ITEM_SEPARATOR)) {
							menu.getItems().add(new SeparatorMenuItem());
							set = true;
						}

						if (ITEMS[i][j].equals(SAGITTA_CODEBOOK)) {

							generateCodebookMenu(menu);
							set = true;
						}
						if (ITEMS[i][j].equals(ABOUT)) {
							menu.getItems().add(new MenuItem("Client version: "+FileUtils.getAboutItem()));							
							MenuItem item=new MenuItem("");
							menu.getItems().add(item);
							//update server version in the loop
							Timeline timer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(3),new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									item.setText("Server version: "+Protocol.getGlobalServerVersion());
								}
							}));
							timer.setCycleCount(Timeline.INDEFINITE);
					        timer.play();
							set = true;
						}

						if (!set) {
							MenuItem item = new MenuItem(ITEMS[i][j]);
							item.setOnAction(new EventHandler<ActionEvent>() {
								public void handle(ActionEvent event) {

									MenuItem item = (MenuItem) event.getSource();

									actionPerformed(item.getText());
									System.out.println(item.getText());
								}

							});
							if(!ITEMS_ENABLE[i][j]){
								item.setDisable(true);
							}
							menu.getItems().add(item);
						}
					}

				}
			} catch (Exception ex) {

			}

			menuBar.getMenus().add(menu);
		}

	}

	/**
	 * Sagitta code book menu
	 * @param menu
	 */
	private void generateCodebookMenu(Menu menu) {

		Cdbksec cdbksec = new Cdbksec();
		Cdbktable cdbktbl = new Cdbktable();

		ArrayList<TreeMap<String, String>> sections = cdbksec.getAllRecords(Processors.NL);

		for (int i = 0; i < sections.size(); i++) {
			TreeMap<String, String> det = sections.get(i);
			MenuItem item = new MenuItem(det.get(Cdbksec.DB_CDBKSEC_DESC));

			ArrayList<TreeMap<String, String>> tables = cdbktbl.getAllRecords(Processors.NL,det.get(Cdbksec.DB_CDBKSEC_DECL_TYPE));

			if (tables != null && tables.size() > 0) {
				Menu subMenu = new Menu(item.getText());
				for (int j = 0; j < tables.size(); j++) {
					TreeMap<String, String> sub = tables.get(j);
					String itemName = sub.get(Cdbktable.DB_CDBKTBL_NM) + " "+ sub.get(Cdbktable.DB_CDBKTBL_DESC);

					MenuItem subItem = new MenuItem(itemName);
					subItem.setUserData(sub);

					subItem.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {

							MenuItem item = (MenuItem) event.getSource();

							@SuppressWarnings(value = "unchecked")
							TreeMap<String, String> data = (TreeMap<String, String>) item.getUserData();
							createCodeBook(data.get(Cdbktable.DB_CDBKTBL_LAN),data.get(Cdbktable.DB_CDBKTBL_DECL_TYPE),	data.get(Cdbktable.DB_CDBKTBL_NM));

						}
					});

					subMenu.getItems().add(subItem);
				}
				menu.getItems().add(subMenu);

			} else {
				//
				item.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						System.out.println("Item");
					}

				});
				menu.getItems().add(item);
			}

		}

	}

	private void createCodeBook(String language, String type, String codeTable) {
		CodeBookMainApp app = new CodeBookMainApp(language,type,codeTable);
		app.start();
	}

	
	private void createGpaDetailsSelect(){
		
	}
	
	private void createImpDetailsSelect(){
		ImpDetailsMainApp app=new ImpDetailsMainApp() ;
		app.start();
	}
	
	private void createRateList(){
		RateListMainApp app=new RateListMainApp() ;
		app.start(null);
	}
	
	private void createArticleList(){
		ArticleListMainApp app=new ArticleListMainApp() ;
		app.start(null);		
	}
	
	private void createArticleMaster(){
		ArticleMasterApp app=new ArticleMasterApp() ;
		app.start();		
	}
	
	private void createTotalsSelect(){
		
	}
	
	private void createDomprocfRunFrame(){
		
		ImpRequestMainApp app= new ImpRequestMainApp();
		app.setType(RequestController.IMP);
		app.setAction(RequestController.REQUEST);
		app.start();

	}
	
	private void createGpaRunFrame(){
		ImpRequestMainApp app= new ImpRequestMainApp();
		app.setType(RequestController.ENT);
		app.setAction(RequestController.REQUEST);
		app.start();
	}
	
	private void createImpResetFrame(){
		ImpResetMainApp app= new ImpResetMainApp();
		//app.setType(Disk.INVOER);
		app.start();
	}
	
	private void createEntResetFrame(){
		EntResetMainApp app= new EntResetMainApp();
		//app.setType(Disk.ENTREPOT);
		app.start();
	}
	private void createExciseTariffs(){
		
	}
	
	private void createRequests(){
		
	}
	
	private void openLogViewer(String path){
		//construct path to the viewer
		File f = new File(path);
		if(!f.exists()){
			Dialogs.create()
					  .nativeTitleBar()
				      .title("Error")
				      .masthead(null)
				      .message( "File "+path+" doesn't exists!")
				      .showError();
			return;	
		}
		File binDir = new File(ApplicationConstants.BIN_DIR);
		try {
			StringBuffer cmd= new StringBuffer();
			cmd.append(binDir.getCanonicalPath());
			cmd.append("\\");
			cmd.append(ApplicationConstants.VIEWER);
			cmd.append(" ");
			cmd.append("\"");
			cmd.append(path);
			cmd.append("\"");
			
			Runtime.getRuntime().exec(cmd.toString());
		} catch (IOException e) {

		}
	}
	
	private String getApplicationLogPath(){
		
		File logDir = new File(Resources.getSetting("server.root")+"\\"+ApplicationConstants.LOG_DIR);
		//StringBuffer buf=new StringBuffer();
		String path="";
		

		try {
			//buf.append(logDir.getCanonicalPath());
			//buf.append("\\");
			//buf.append(com.foursoft.gpa.client.GpaDashboard.LOG_FILE);
			TopFileFilter tff=new TopFileFilter();
			logDir.listFiles(tff);
			path=tff.getTopFile().getCanonicalPath();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//return buf.toString();
		return path;
	}
	
	private void actionPerformed(String action){
		if (PRODUCT.toLowerCase().equals(action.toLowerCase())) {
			createArticleMaster();
		}else if(ENT_DETAILS.toLowerCase().equals(action.toLowerCase())){
			createGpaDetailsSelect();
		}else if(TOTALS.toLowerCase().equals(action.toLowerCase())){
			createTotalsSelect();	
		}else if(IMP_REQUEST.toLowerCase().equals(action.toLowerCase())){
			createDomprocfRunFrame();
		}else if(IMP_DETAILS.toLowerCase().equals(action.toLowerCase())){
				createImpDetailsSelect();	
		}else if(ENT_REQUEST.toLowerCase().equals(action.toLowerCase())){
			createGpaRunFrame();		
		}else if(RESET_IMP.toLowerCase().equals(action.toLowerCase())){
			createImpResetFrame();
		}else if(RESET_ENT.toLowerCase().equals(action.toLowerCase())){
			createEntResetFrame();
		}else if(LOGGING.toLowerCase().equals(action.toLowerCase())){
			openLogViewer(getApplicationLogPath());
		}else if(INBOUND.toLowerCase().equals(action.toLowerCase())){
			//start inbound process
		}else if(EXCISE_TAX.toLowerCase().equals(action.toLowerCase())){
			createExciseTariffs();
		}else if(RATES.toLowerCase().equals(action.toLowerCase())){
			createRateList();
		}else if(ARTICLES.toLowerCase().equals(action.toLowerCase())){
			createArticleList();
		}else if("requests".equals(action.toLowerCase())){
			createRequests();			
		}else {
			quit();
		}
	}
	
   @FXML
    void sendToServer(ActionEvent event) {  
	   
    }
   
	// Quit the application.
	protected void quit() {
		System.exit(0);
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
	
	public void populateGridData(){
		
		//Retrieve data;
		int selectedRow=-1;
		Gpareq gpareq=new Gpareq();		
		this.data=gpareq.readAllRecords(false);
		if(requestData!=null){
			try{
				selectedRow =requestGrid.getSelectionModel().getSelectedCells().get(0).getRow();
			}catch(Exception ex){
				
			}
			requestData.clear();
			
			if(data!=null && data.size()>0){
				for (int i = 0; i < this.data.size(); i++) {
					TreeMap<String, String> tmp=data.get(i);
					
					requestData.add(new RequestModel(tmp.get(Gpareq.DB_GPAREQ_TYPE),tmp.get(Gpareq.DB_GPAREQ_PER),tmp.get(Gpareq.DB_GPAREQ_STAT),tmp,tmp.get(Gpareq.DB_GPAREQ_ID)));
				}
			}
			
			requestGrid.setItems(requestData);
			
			if(selectedRow>=0){
				requestGrid.getSelectionModel().select(selectedRow);
			}
		}
		
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
	
	private void setLogo(){
		String logoUri=Resources.getSetting("logo.uri");
		
		if(!logoUri.equals("")){
			File f= new  File(logoUri);
			
			if(f.exists()){
				logoImageView.setImage(new Image(logoUri));
			}
		}

		
	}
	
	private void copyDiskFile(String orig, String dest){
		
		orig=Resources.getSetting("server.root")+"\\"+FileUtils.DISK+"\\"+orig;
		File origFile= new File(orig);
		File destFile= new File(dest);
		
		try {
			Files.copy(origFile.toPath(),destFile.toPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
