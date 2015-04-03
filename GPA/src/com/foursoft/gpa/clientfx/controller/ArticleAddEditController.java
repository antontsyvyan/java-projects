package com.foursoft.gpa.clientfx.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import com.foursoft.gpa.clientfx.AutoCompleteComboBoxListener;
import com.foursoft.gpa.clientfx.model.ArticleModel;
import com.foursoft.gpa.db.Account;
import com.foursoft.gpa.db.Article;
import com.foursoft.gpa.db.Arttar;
import com.foursoft.gpa.db.Tariff;
import com.foursoft.gpa.utils.Codetable;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.TariffCode;

public class ArticleAddEditController {

	public ArticleModel articleModel;
	
	public Article article= new Article();
	public Arttar arttar=new Arttar();
	
	public ArticleAddEditController() {
		
	}


	@FXML
	// fx:id="accountIdCombo"
	private ComboBox<String> accountIdCombo; // Value injected by FXMLLoader

	@FXML
	// fx:id="startDatePicker"
	private DatePicker startDatePicker; // Value injected by FXMLLoader

	@FXML
	// fx:id="tariffCodeCombo"
	private ComboBox<String> tariffCodeCombo; // Value injected by FXMLLoader

	@FXML
	// fx:id="descriptionText"
	private TextField descriptionText; // Value injected by FXMLLoader

	@FXML
	// fx:id="saveButton"
	private Button saveButton; // Value injected by FXMLLoader

	@FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader 
	
	@FXML
	// fx:id="articleCodeText"
	private TextField articleCodeText; // Value injected by FXMLLoader

	@FXML
	// fx:id="colliCodeCombo"
	private ComboBox<String> colliCodeCombo; // Value injected by FXMLLoader
	
    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader

	
	private ArrayList<String> tariffList;
	
	
	@FXML
	// This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert accountIdCombo != null : "fx:id=\"accountIdCombo\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
		assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
		assert tariffCodeCombo != null : "fx:id=\"tariffCodeCombo\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
		assert descriptionText != null : "fx:id=\"descriptionText\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
		assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
		assert articleCodeText != null : "fx:id=\"articleCodeText\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
		assert colliCodeCombo != null : "fx:id=\"colliCodeCombo\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
		assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
		assert messageLabel != null : "fx:id=\"messageLabel\" was not injected: check your FXML file 'ArticleAddEdit.fxml'.";
			
		messageLabel.setText("");
				
	}
	
	@FXML
	void addTariffButtonPressed(ActionEvent event) {

	}

	@FXML
	void saveButtonPressed(ActionEvent event) {
		//String validFrom=startDatePicker.getValue().format(DateTimeFormatter.ofPattern(DateUtils.FORMAT_INHOUSE));
		//System.out.println(validFrom);
		
		resetFields();
			
		Tariff tariff=new Tariff();
		
		messageLabel.setText("");
		
		TreeMap<String, String> articleDetail=new TreeMap<String, String>();
		TreeMap<String, String> arttarDetail=new TreeMap<String, String>();
		
		//validations
		boolean process = true;
		
		if(articleModel==null){
			
			if(articleCodeText.getText().trim().equals("")){
				articleCodeText.getStyleClass().add("error-field");
				articleCodeText.setTooltip(new Tooltip("Article is mandatory"));
				process=false;
			}
			
			if(startDatePicker.getValue()==null){
				startDatePicker.getStyleClass().add("error-field");
				startDatePicker.setTooltip(new Tooltip("Date is mandatory"));
				process=false;
			}
			
		}
		
		//Tariff code validation
		if(tariffCodeCombo.getSelectionModel().getSelectedItem()==null){
			process=false;
			tariffCodeCombo.getStyleClass().add("error-field");
			tariffCodeCombo.setTooltip(new Tooltip("Tariff code is mandatory"));
		}else{
			TariffCode tariffCode= new TariffCode(tariffCodeCombo.getSelectionModel().getSelectedItem());
			if(tariffCode.isValid()){
				TreeMap<String, String> tariffDetals=tariff.getTariff(tariffCodeCombo.getSelectionModel().getSelectedItem());			
				if(tariffDetals==null || tariffDetals.size()==0){
					process=false;
					tariffCodeCombo.getStyleClass().add("error-field");
					tariffCodeCombo.setTooltip(new Tooltip("Tariff code doesn't exist"));
				}
			}else{
				process=false;
				tariffCodeCombo.getStyleClass().add("error-field");
				tariffCodeCombo.setTooltip(new Tooltip("Tariff code is not valid"));
			}
		}
		
		if(process){
			//check if already exists
			if(articleModel==null){
				articleDetail=article.getArticleData(articleCodeText.getText(), accountIdCombo.getSelectionModel().getSelectedItem());
				if(articleDetail!=null && articleDetail.size()>0){
					//article exists					
					 messageLabel.setText("Article "+articleCodeText.getText()+" already exists");
					 process=false;
				}else{
					arttarDetail=arttar.getCurrentTariff(articleCodeText.getText(), accountIdCombo.getSelectionModel().getSelectedItem(), "", startDatePicker.getValue().format(DateTimeFormatter.ofPattern(DateUtils.FORMAT_INHOUSE)));
					if(arttarDetail!=null && arttarDetail.size()>0){
						messageLabel.setText("Combination article tariff already exists");
						process=false;
					}
				}
			}
			
		}
		
		
		if(process){
			
			articleDetail=new TreeMap<String, String>();
			arttarDetail=new TreeMap<String, String>();
			
			articleDetail.put(Article.DB_ARTICLE_CODE, articleCodeText.getText());
			articleDetail.put(Article.DB_ACCOUNT_ID, accountIdCombo.getSelectionModel().getSelectedItem());
			articleDetail.put(Article.DB_ARTICLE_DESCRIPTION, descriptionText.getText());
			articleDetail.put(Article.DB_ARTICLE_SEARCH_NAME, "");
			articleDetail.put(Article.DB_ARTICLE_NET_WEIGHT, "");
			articleDetail.put(Article.DB_COLLI_CODE, colliCodeCombo.getSelectionModel().getSelectedItem());
			
			arttarDetail.put(Arttar.DB_ARTICLE_CODE, articleCodeText.getText());
			arttarDetail.put(Arttar.DB_ACCOUNT_ID, accountIdCombo.getSelectionModel().getSelectedItem());
			arttarDetail.put(Arttar.DB_ARTTAR_ORIGIN, "");
			arttarDetail.put(Arttar.DB_ARTTAR_START_DATE, startDatePicker.getValue().format(DateTimeFormatter.ofPattern(DateUtils.FORMAT_INHOUSE)));
			arttarDetail.put(Arttar.DB_TARIFF_CODE, tariffCodeCombo.getSelectionModel().getSelectedItem());
			
			
			if(article.insertUpdate(articleDetail)){
				 if(arttar.insertUpdate(arttarDetail)){
					 messageLabel.setText("Article "+articleCodeText.getText()+" saved successfully");
					 if(articleModel==null){
						 cleanUp();
					 }else{
						 articleModel.setColliCodeColumn(colliCodeCombo.getSelectionModel().getSelectedItem());
						 //articleModel.setTariffCodeColumn(tariffCodeCombo.getSelectionModel().getSelectedItem());
						 articleModel.setArticleDescriptionColumn(descriptionText.getText());
					 }
					 
				 }
			 }
		}
	}

	 @FXML
	 void cancelButtonPressed(ActionEvent event) {
		// get a handle to the stage
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		//close window
		stage.close();
	 }
	
	public void init(){
		 Account account= new Account();
		 Codetable codetable=new Codetable();
		 
		 setTariffList();
		 
		 accountIdCombo.setItems(FXCollections.observableArrayList(account.getCustomers()));
		 colliCodeCombo.setItems(FXCollections.observableArrayList(codetable.getAdministrativeUnitsList()));		 
		 tariffCodeCombo.setItems(FXCollections.observableArrayList(this.tariffList));
		 		 
		 if(articleModel==null){
			 articleModel=new ArticleModel();
		 }
		 // bind model properties to screen fields
		 articleCodeText.textProperty().bindBidirectional(articleModel.articleNumberColumnProperty());
		 tariffCodeCombo.valueProperty().bindBidirectional(articleModel.tariffCodeColumnProperty());
		 
		 new AutoCompleteComboBoxListener(tariffCodeCombo);
		 
		 if(articleModel!=null){
			 //we are in update mode
			 saveButton.setText("Update");
			 
			 //articleCodeText.setText(articleModel.getArticleNumberColumn());
			 articleCodeText.setDisable(true);	
			 
			 accountIdCombo.getSelectionModel().select(articleModel.getAccountColumn());
			 accountIdCombo.setDisable(true);
			 
			 startDatePicker.setValue(LocalDate.parse(articleModel.getValidFromColumn(),DateTimeFormatter.ofPattern(DateUtils.FORMAT_INHOUSE)));
			 startDatePicker.setEditable(false);
			 startDatePicker.setDisable(true);
			 		 
			 descriptionText.setText(articleModel.getArticleDescriptionColumn());
			 colliCodeCombo.getSelectionModel().select(articleModel.getColliCodeColumn());
			 
			 //tariffCodeCombo.setValue(articleModel.getTariffCodeColumn());
			 						 
		 }
	}
	
	
	private void cleanUp(){		
		articleCodeText.setText("");
		accountIdCombo.getSelectionModel().select(0);
		descriptionText.setText("");
		startDatePicker.getEditor().setText("");
		colliCodeCombo.getSelectionModel().select(0);
		tariffCodeCombo.setValue("");
	}
	
	private void resetFields(){
		//reset fields
		tariffCodeCombo.getStyleClass().remove("error-field");
		tariffCodeCombo.setTooltip(null);
		//
		articleCodeText.getStyleClass().remove("error-field");
		articleCodeText.setTooltip(null);
		//
		startDatePicker.getStyleClass().remove("error-field");
		startDatePicker.setTooltip(null);
	}

	
	private void setTariffList(){
		
		Tariff tariff= new Tariff();
		this.tariffList=new ArrayList<String>();
		
		ArrayList<TariffCode> tariffList= new ArrayList<TariffCode>();		
		tariffList=tariff.getTariffList("");
		if(tariffList!=null && tariffList.size()>0){
			for(TariffCode item:tariffList){
				this.tariffList.add(item.getTariffCode());
			}
		}
		
	}
	
	
	public void setArticleModel(ArticleModel articleModel) {
		this.articleModel = articleModel;
	}
	
	
}
