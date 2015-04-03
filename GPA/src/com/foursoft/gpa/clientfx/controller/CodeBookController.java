package com.foursoft.gpa.clientfx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.foursoft.gpa.clientfx.model.CodeBookModel;
import com.foursoft.gpa.db.Cdbk;
import com.foursoft.gpa.db.Cdbksec;
import com.foursoft.gpa.db.Cdbktable;

public class CodeBookController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;
	
	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;
	
	@FXML // fx:id="codeBookGrid"
	private TableView<CodeBookModel> codeBookGrid; // Value injected by FXMLLoader
	
	@FXML // fx:id="codeColumn"
	private TableColumn<CodeBookModel, String> codeColumn; // Value injected by FXMLLoader
	
	@FXML // fx:id="remarksColumn"
	private TableColumn<CodeBookModel, String> remarksColumn; // Value injected by FXMLLoader
	
	@FXML // fx:id="descrColumn"
	private TableColumn<CodeBookModel, String> descrColumn; // Value injected by FXMLLoader
	
    @FXML // fx:id="tableName"
    private Label tableName; // Value injected by FXMLLoader
	
	private String language;
	private String type;
	private String codeTable;
	
	private ArrayList<TreeMap<String, String>> data;
	private ObservableList<CodeBookModel> codeBookData = FXCollections.observableArrayList();
	
	public CodeBookController() {
		
	}
	
	@FXML
	void initialize() {
        assert codeBookGrid != null : "fx:id=\"codeBookGrid\" was not injected: check your FXML file 'CodeBook.fxml'.";
        assert codeColumn != null : "fx:id=\"codeColumn\" was not injected: check your FXML file 'CodeBook.fxml'.";
        assert remarksColumn != null : "fx:id=\"remarksColumn\" was not injected: check your FXML file 'CodeBook.fxml'.";
        assert descrColumn != null : "fx:id=\"descrColumn\" was not injected: check your FXML file 'CodeBook.fxml'.";
        assert tableName != null : "fx:id=\"tableName\" was not injected: check your FXML file 'CodeBook.fxml'.";
        
             
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        descrColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        remarksColumn.setCellValueFactory(cellData -> cellData.getValue().remarksProperty());
		
	 }
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}

	public void populateData(){
		
		//Set title
		try{
	        Cdbktable cdbktbl = new Cdbktable();  	        
	        Cdbksec cdbksec = new Cdbksec();
	        	        
	        TreeMap<String, String> res=new  TreeMap<String, String>();
	        res=cdbktbl.getTableRecord(language, type, codeTable);
	        
	        TreeMap<String, String> res2=new  TreeMap<String, String>();
	        res2=cdbksec.getRecord(language, type);
	        
	        tableName.setText(res2.get(Cdbksec.DB_CDBKSEC_DESC)+" "+res.get(Cdbktable.DB_CDBKTBL_DESC).toLowerCase());
			
			//Retrieve data;
			Cdbk cdbk=new Cdbk();		
			this.data=cdbk.getTable(language,type,codeTable);
			if(data!=null && data.size()>0){
				for (int i = 0; i < this.data.size(); i++) {
					TreeMap<String, String> tmp=data.get(i); 				
					codeBookData.add(new CodeBookModel(tmp.get(Cdbk.DB_CDBK_CODE),tmp.get(Cdbk.DB_CDBK_DESC1),tmp.get(Cdbk.DB_CDBK_REM)));
				}
			}		
			
			codeBookGrid.setItems(codeBookData);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}	
	
}
