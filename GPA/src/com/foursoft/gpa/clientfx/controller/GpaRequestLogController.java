package com.foursoft.gpa.clientfx.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import javax.swing.JComponent;

import org.controlsfx.dialog.Dialogs;

import com.foursoft.gpa.Disk;
import com.foursoft.gpa.GpaCore;
import com.foursoft.gpa.clientfx.Dashboard;
import com.foursoft.gpa.clientfx.DiskErrorsMainApp;
import com.foursoft.gpa.clientfx.DraggableTab;
import com.foursoft.gpa.clientfx.img.ImageLoader;
import com.foursoft.gpa.clientfx.model.RequestModel;
import com.foursoft.gpa.db.Gpareq;
import com.foursoft.gpa.reporting.InboundDetailsReport;
import com.foursoft.gpa.reporting.NegativeStockReport;
import com.foursoft.gpa.reporting.StockOverviewReport;
import com.foursoft.gpa.reporting.TotalsDetailsReport;
import com.foursoft.gpa.reporting.beans.StockOverviewBean;
import com.foursoft.gpa.utils.FileUtils;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.Resources;
import com.foursoft.gpa.utils.StockUtils;

public class GpaRequestLogController {
	
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="requestGrid"
    private TableView<RequestModel> requestGrid; // Value injected by FXMLLoader

    @FXML // fx:id="detailsColumn"
    private TableColumn<RequestModel, TreeMap<String, String>> detailsColumn; // Value injected by FXMLLoader

    @FXML // fx:id="statusColumn"
    private TableColumn<RequestModel, String> statusColumn; // Value injected by FXMLLoader

    @FXML // fx:id="periodColumn"
    private TableColumn<RequestModel, String> periodColumn; // Value injected by FXMLLoader

    @FXML // fx:id="typeColumn"
    private TableColumn<RequestModel,String> typeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="idColumn"
    private TableColumn<RequestModel, String> idColumn; // Value injected by FXMLLoader

    private ObservableList<RequestModel> requestData = FXCollections.observableArrayList();
    
    private ArrayList<TreeMap<String, String>> data;
    
    private DiskErrorsMainApp diskErrorsApp=new DiskErrorsMainApp();
    
    private Socket progressSocket=null;
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert requestGrid != null : "fx:id=\"requestGrid\" was not injected: check your FXML file 'GpaRequestLog.fxml'.";
        assert detailsColumn != null : "fx:id=\"detailsColumn\" was not injected: check your FXML file 'GpaRequestLog.fxml'.";
        assert statusColumn != null : "fx:id=\"statusColumn\" was not injected: check your FXML file 'GpaRequestLog.fxml'.";
        assert periodColumn != null : "fx:id=\"periodColumn\" was not injected: check your FXML file 'GpaRequestLog.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'GpaRequestLog.fxml'.";
        assert idColumn != null : "fx:id=\"idColumn\" was not injected: check your FXML file 'GpaRequestLog.fxml'.";
        
        //Add columns to table
  		typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
  		periodColumn.setCellValueFactory(cellData -> cellData.getValue().periodProperty());
  		statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
  		detailsColumn.setCellValueFactory(cellData -> cellData.getValue().detailsProperty());
  		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
  		
  		requestGrid.setFixedCellSize(30);
  		
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
								    btn.setPrefWidth(80);
								    btn.setText("Errors");							    
								    btn.getStyleClass().add("error-button");
								    
								    btn.setOnAction(new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											// TODO Auto-generated method stub
											diskErrorsApp =new DiskErrorsMainApp(details);
											diskErrorsApp.setShowLinks(true);
											diskErrorsApp.start();
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
									        		SwingNode swingNode=new SwingNode();
													swingNode.setContent((JComponent) tdr.getNode());
													DraggableTab tab = new DraggableTab("Totalen "+item.get(Gpareq.DB_GPAREQ_PER));
													//tab.setText("Totalen "+item.get(Gpareq.DB_GPAREQ_PER));
													tab.setUserData(item.get(Gpareq.DB_GPAREQ_PER));
													tab.setContent(swingNode);
													DashboardNewController.publicBodyTabPane.getTabs().add(tab);
													DashboardNewController.publicBodyTabPane.getSelectionModel().select(tab);
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
									 
									 if(item.get(Gpareq.DB_GPAREQ_TYPE).startsWith(Disk.ENTREPOT)){
										 Button stockRptBtn=new Button();
										 stockRptBtn.setLayoutX(100);
										 stockRptBtn.setLayoutY(60);
										 stockRptBtn.setText("Stock");
										 stockRptBtn.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												
												Node icon = new ImageView(new Image(ImageLoader.class.getResourceAsStream("loading_tab.gif")));
												String labelText="Stock overview "+item.get(Gpareq.DB_GPAREQ_PER);
												DraggableTab tab = new DraggableTab(labelText,icon);
												tab.setUserData(item.get(Gpareq.DB_GPAREQ_PER));
												DashboardNewController.publicBodyTabPane.getTabs().add(tab);
												
												Task<Void> task = new Task<Void>() {
													
													@Override
													public void run() {	
														StockUtils su= new StockUtils();
														
														su.setFeedSystem(item.get(Gpareq.DB_GPAREQ_FED_SYS));
														su.setClientCode("");
														su.setPeriod(item.get(Gpareq.DB_GPAREQ_PER));
														ArrayList<StockOverviewBean>collection=su.getCollection();
														
														StockOverviewReport sor = new StockOverviewReport(item.get(Gpareq.DB_GPAREQ_FED_SYS),"",item.get(Gpareq.DB_GPAREQ_PER));
														sor.setCollection(collection);
														
														if(sor.generateReport()){
															Platform.runLater(new Runnable() {
	
																@Override
																public void run() {
																	SwingNode swingNode=new SwingNode();
																	swingNode.setContent((JComponent) sor.getNode());
																	tab.setContent(swingNode);
																	//DashboardNewController.publicBodyTabPane.getTabs().add(tab);
																	tab.setGraphic(null);
																	tab.setText(labelText);
																	
																	DashboardNewController.publicBodyTabPane.getSelectionModel().select(tab);
																	
																}
																
															});
														}
														
														NegativeStockReport nsr= new NegativeStockReport(item.get(Gpareq.DB_GPAREQ_FED_SYS),"",item.get(Gpareq.DB_GPAREQ_PER));
														nsr.setCollection(collection);
														
														if(nsr.generateReport()){
															Platform.runLater(new Runnable() {
																@Override
																public void run() {
																	String labelText="Negative Stock "+item.get(Gpareq.DB_GPAREQ_PER);
																	DraggableTab tabNegativeStock = new DraggableTab(labelText);
																	tabNegativeStock.setUserData(item.get(Gpareq.DB_GPAREQ_PER));
																	SwingNode swingNode=new SwingNode();
																	swingNode.setContent((JComponent) nsr.getNode());
																	
																	tabNegativeStock.setContent(swingNode);
																	DashboardNewController.publicBodyTabPane.getTabs().add(tabNegativeStock);
																	
																}
																
															});
														}
														
														InboundDetailsReport idr= new InboundDetailsReport(item.get(Gpareq.DB_GPAREQ_FED_SYS),"",item.get(Gpareq.DB_GPAREQ_PER));
														if(idr.generateReport()){
															Platform.runLater(new Runnable() {

																@Override
																public void run() {

																	String labelText="Inbound Details "+item.get(Gpareq.DB_GPAREQ_PER);
																	DraggableTab tabInboundDetails = new DraggableTab(labelText);
																	tabInboundDetails.setUserData(item.get(Gpareq.DB_GPAREQ_PER));
																	SwingNode swingNode=new SwingNode();
																	swingNode.setContent((JComponent) idr.getNode());
																	
																	tabInboundDetails.setContent(swingNode);
																	DashboardNewController.publicBodyTabPane.getTabs().add(tabInboundDetails);
																}
																
															});
														}
													}

													@Override
													protected Void call() throws Exception {
														// TODO Auto-generated method stub
														return null;
													}
													
												};
												
												new Thread(task).start();
																								
											}
											 
										 });
										 
										 box.getChildren().add(stockRptBtn);
									 }
									 errorDetails=item.get(Gpareq.DB_GAPREQ_ERR_MSG);
								     if(errorDetails.startsWith(GpaCore.ERROR_PREFIX)){
								    	 errorDetails=errorDetails.substring(GpaCore.ERROR_PREFIX.length());
										 Button btnWarning = new Button();
										 btnWarning.setLayoutX(100);
										 btnWarning.setLayoutY(60);
										 btnWarning.setPrefWidth(80);
										 btnWarning.setText("Warnings");							    
										 btnWarning.getStyleClass().add("warning-button");
										 btnWarning.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												// TODO Auto-generated method stub
												diskErrorsApp =new DiskErrorsMainApp(errorDetails);
												diskErrorsApp.setShowLinks(true);
												diskErrorsApp.start();
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
