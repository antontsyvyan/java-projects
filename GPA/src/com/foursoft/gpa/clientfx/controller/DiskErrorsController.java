package com.foursoft.gpa.clientfx.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import org.controlsfx.dialog.Dialogs;

import com.foursoft.gpa.clientfx.Dashboard;
import com.foursoft.gpa.reporting.ImpDetailsReport;
import com.foursoft.gpa.utils.ApplicationConstants;
import com.foursoft.gpa.utils.ControleBrochure;
import com.foursoft.gpa.utils.DiskErrorsModel;
import com.foursoft.gpa.utils.FileUtils;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Resources;

public class DiskErrorsController {

	private String fileName;
	private String errorString;
	private boolean showLinks=false;
	
	public DiskErrorsController() {
	}
	
	public DiskErrorsController(String fileName) {
		this.setFileName(fileName);
	}
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="codeBookGrid"
    private TableView<DiskErrorsModel> diskErrorsGrid; // Value injected by FXMLLoader

    @FXML // fx:id="blockColumn"
    private TableColumn<DiskErrorsModel, String> fieldColumn; // Value injected by FXMLLoader

    @FXML // fx:id="typeColumn"
    private TableColumn<DiskErrorsModel, String> typeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="descrColumn"
    private TableColumn<DiskErrorsModel, String> descrColumn; // Value injected by FXMLLoader

    @FXML // fx:id="lineColumn"
    private TableColumn<DiskErrorsModel, String> lineColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="a3Column"
    private TableColumn<DiskErrorsModel, String> a3Column; // Value injected by FXMLLoader
    
    @FXML // fx:id="warnings"
    private CheckBox warnings; // Value injected by FXMLLoader
    
    @FXML // fx:id="errors"
    private CheckBox errors; // Value injected by FXMLLoader
    
    @FXML // fx:id="downloadButton"
    private Button downloadButton; // Value injected by FXMLLoader
    
	private ObservableList<DiskErrorsModel> diskErrorsData = FXCollections.observableArrayList();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert diskErrorsGrid != null : "fx:id=\"codeBookGrid\" was not injected: check your FXML file 'DiskErrors.fxml'.";
        assert fieldColumn != null : "fx:id=\"blockColumn\" was not injected: check your FXML file 'DiskErrors.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'DiskErrors.fxml'.";
        assert descrColumn != null : "fx:id=\"descrColumn\" was not injected: check your FXML file 'DiskErrors.fxml'.";
        assert lineColumn != null : "fx:id=\"lineColumn\" was not injected: check your FXML file 'DiskErrors.fxml'.";
        assert warnings != null : "fx:id=\"warnings\" was not injected: check your FXML file 'DiskErrors.fxml'.";
        assert errors != null : "fx:id=\"errors\" was not injected: check your FXML file 'DiskErrors.fxml'.";
        assert a3Column != null : "fx:a3Column=\"errors\" was not injected: check your FXML file 'DiskErrors.fxml'.";
        assert downloadButton != null : "fx:id=\"downloadButton\" was not injected: check your FXML file 'DiskErrors.fxml'.";
               
        fieldColumn.setCellValueFactory(cellData -> cellData.getValue().fieldProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        descrColumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());
        lineColumn.setCellValueFactory(cellData -> cellData.getValue().lineProperty());
        a3Column.setCellValueFactory(cellData -> cellData.getValue().a3Property());
        
        
        typeColumn.setCellFactory(new Callback<TableColumn<DiskErrorsModel,String>, TableCell<DiskErrorsModel,String>>(){

			@Override
			public TableCell<DiskErrorsModel, String> call(TableColumn<DiskErrorsModel, String> arg0) {
				
				TableCell<DiskErrorsModel, String> cell = new TableCell<DiskErrorsModel, String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						HBox box= new HBox(); 
						if(item!=null){							
							//box.setSpacing(30) ;
							ImageView imageview = new ImageView();
							imageview.setFitHeight(16);
							imageview.setFitWidth(16); 
							if (item.equals(DiskErrorsModel.TYPE_ERROR)) {
								//this.setTextFill(white);
								//this.setBackground(new Background(new BackgroundFill(blue, null, null)));
								imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/error.png")); 
							}
							if (item.equals(DiskErrorsModel.TYPE_WARNING)) {
								//this.setTextFill(white);
								//this.setBackground(new Background(new BackgroundFill(blue, null, null)));
								imageview.setImage(new Image(Dashboard.class.getResource("img").toString()+"/warning.png")); 
							}
							
							Tooltip t = new Tooltip(item);
							Tooltip.install(imageview, t);
							
							box.getChildren().addAll(imageview); 						
						}
						//SETTING ALL THE GRAPHICS COMPONENT FOR CELL 
						setGraphic(box);	
					}
				};
				
				cell.setAlignment(Pos.BASELINE_CENTER);
				return cell;
			}
        	
        });
        
        a3Column.setCellFactory(new Callback<TableColumn<DiskErrorsModel,String>, TableCell<DiskErrorsModel,String>>(){

			@Override
			public TableCell<DiskErrorsModel, String> call(TableColumn<DiskErrorsModel, String> param) {
				TableCell<DiskErrorsModel, String> cell = new TableCell<DiskErrorsModel, String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						
						if(showLinks){
							Hyperlink link = new Hyperlink();
						    link.setText(item);
						    link.setOnAction(new EventHandler<ActionEvent>() {
	
								@Override
								public void handle(ActionEvent arg0) {
	
									ImpDetailsReport idr= new ImpDetailsReport(item);
									
									if(!idr.generateReport()){
						        		Dialogs.create()
										  .nativeTitleBar()
									      .title("Message")
									      .masthead(null)
									      .message( "There is nothing to show for the id "+item)
									      .showInformation();
						        	}else{
						        		idr.show();
						        	}
								}
						    	
						    });
						    
						    setGraphic(link);
						}else{
							setText(item);
						}
					}					
				};
								
				cell.setOnDragEntered(new EventHandler<DragEvent>(){
					@Override
					public void handle(DragEvent arg0) {
						setSelection(cell, a3Column);
						
					}
					
				});
				
				cell.setOnDragDetected(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						Dragboard db = cell.getTableView().startDragAndDrop(TransferMode.COPY);
        				ClipboardContent content = new ClipboardContent();
        				//content.put(dataFormat, "XData");
        				db.setContent(content);
        				setSelection(cell, a3Column);
        				event.consume();
						
					}
					
				});
				return cell;
			}
        	
        });
    
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {	
				FileChooser fileChooser = new FileChooser();
				if(fileName!=null){
					fileChooser.setInitialFileName(fileName.replace(".txt", ".csv"));
					//Show save file dialog
					File file=fileChooser.showSaveDialog(null);
					copyLogFile(fileName,file.getPath());
				}else{
					fileChooser.setInitialFileName(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".csv");
					File file=fileChooser.showSaveDialog(null);
					createLogFile(file);
				}
			}
        	
        });
    }
    
     
    @FXML
    void onChange(ActionEvent event) {
    	populateData(errors.isSelected() ? true: false,warnings.isSelected() ? true: false);
    }

	
    public void populateData(boolean showErrors,boolean showWarnings){
    	ArrayList<DiskErrorsModel> list =new ArrayList<DiskErrorsModel>();
    	diskErrorsData.clear();
    	if(this.fileName==null){
    		list=populateDataFromString(showErrors,showWarnings);    		
    	}else{
    		list=populateDataFromFile(showErrors,showWarnings);
    	}
    	diskErrorsData.addAll(list);
    	diskErrorsGrid.setItems(diskErrorsData);
    }
    
	public void populateDataOld(boolean showErrors,boolean showWarnings){
		BufferedReader br=null;
		boolean include=true;
		try {
			br = new BufferedReader(new FileReader(getRemoteFilePath(this.fileName)));
			String currentLine;
			diskErrorsData.clear();
			while ((currentLine = br.readLine()) != null) {
				include=true;
				DiskErrorsModel dem=new DiskErrorsModel();
				dem.set(currentLine);
				if(dem.getType().equals(ControleBrochure.TYPE_ERROR) && !showErrors){
					include=false;
				}
				if(dem.getType().equals(ControleBrochure.TYPE_WARNING) && !showWarnings){
					include=false;
				}
				
				if(include){
					diskErrorsData.add(dem);
				}
			}
			
			diskErrorsGrid.setItems(diskErrorsData);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}

		}

	}
	
	private String getRemoteFilePath(String fileName){
		
		String path="";
		path=Resources.getSetting("server.root")+"\\"+ApplicationConstants.LOG_DIR+"\\"+fileName;
		
		return path;
	}
	
	private ArrayList<DiskErrorsModel> populateDataFromFile(boolean showErrors,boolean showWarnings){
		
		ArrayList<DiskErrorsModel> list =new ArrayList<DiskErrorsModel>();
		BufferedReader br=null;
		boolean include=true;
		try {
			br = new BufferedReader(new FileReader(getRemoteFilePath(this.fileName)));
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				include=true;
				DiskErrorsModel dem=new DiskErrorsModel();
				dem.set(currentLine);
				if(dem.getType().equals(ControleBrochure.TYPE_ERROR) && !showErrors){
					include=false;
				}
				if(dem.getType().equals(ControleBrochure.TYPE_WARNING) && !showWarnings){
					include=false;
				}
				
				if(include){
					list.add(dem);
				}
		}		

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}

		}
		
		return list;
	}
	
	private void copyLogFile(String orig, String dest){
		
		orig=Resources.getSetting("server.root")+"\\"+FileUtils.LOG+"\\"+orig;
		File origFile= new File(orig);
		File destFile= new File(dest);
		
		try {
			Files.copy(origFile.toPath(),destFile.toPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    private void createLogFile(File file){		
		//Create txt file
    	BufferedWriter output =null;
    	try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(errorString);
		} catch (IOException e) {
			
		}finally{
			try {
				output.close();
			} catch (IOException e) {
			}
		}
    }

	
	private ArrayList<DiskErrorsModel> populateDataFromString(boolean showErrors,boolean showWarnings){
		ArrayList<DiskErrorsModel> list =new ArrayList<DiskErrorsModel>();
		boolean include=true;
		
		if(this.errorString!=null && this.errorString.length()>0){
			StringTokenizer st = new StringTokenizer(errorString,Processors.LINE_SEPARATOR);
			String currentLine;
			while(st.hasMoreTokens()){ 
				currentLine=st.nextToken();
				include=true;
				DiskErrorsModel dem=new DiskErrorsModel();
				dem.set(currentLine);
				if(dem.getType().equals(ControleBrochure.TYPE_ERROR) && !showErrors){
					include=false;
				}
				if(dem.getType().equals(ControleBrochure.TYPE_WARNING) && !showWarnings){
					include=false;
				}
				
				if(include){
					list.add(dem);
				}
			}
		}
		
		return list;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getErrorString() {
		return errorString;
	}

	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}
		
	public void setShowLinks(boolean showLinks) {
		this.showLinks = showLinks;
	}

	private void setSelection(TableCell<DiskErrorsModel, String> cell, TableColumn<DiskErrorsModel, String> col) {
		if (cell.isSelected() ) {
			System.out.println("False enter");
			diskErrorsGrid.getSelectionModel().clearSelection(cell.getIndex(), col);
		} else {
			System.out.println("Select");
			diskErrorsGrid.getSelectionModel().select(cell.getIndex(), col);
		}
	}

	

}
