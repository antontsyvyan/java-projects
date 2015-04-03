package com.foursoft.gpa.clientfx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import com.foursoft.gpa.clientfx.ArticleAddEditMainApp;
import com.foursoft.gpa.clientfx.img.ImageLoader;
import com.foursoft.gpa.clientfx.model.ArticleModel;
import com.foursoft.gpa.db.Account;
import com.foursoft.gpa.db.Arttar;

public class ArticleListController {
	
	   @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="tariffCodeColumn"
	    private TableColumn<ArticleModel, String> tariffCodeColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="searchProduct"
	    private TextField searchProduct; // Value injected by FXMLLoader

	    @FXML // fx:id="accountColumn"
	    private TableColumn<ArticleModel, String> accountColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="articleDescriptionColumn"
	    private TableColumn<ArticleModel, String> articleDescriptionColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="grid"
	    private TableView<ArticleModel> grid; // Value injected by FXMLLoader

	    @FXML // fx:id="articleNumberColumn"
	    private TableColumn<ArticleModel, String> articleNumberColumn; // Value injected by FXMLLoader

	    @FXML // fx:id="customerSelect"
	    private ComboBox<String> customerSelect; // Value injected by FXMLLoader

	    @FXML // fx:id="validFromColumn"
	    private TableColumn<ArticleModel, String> validFromColumn; // Value injected by FXMLLoader
	    
	    private ObservableList<ArticleModel> data = FXCollections.observableArrayList();
	    	    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tariffCodeColumn != null : "fx:id=\"tariffCodeColumn\" was not injected: check your FXML file 'ArticleList.fxml'.";
        assert accountColumn != null : "fx:id=\"accountColumn\" was not injected: check your FXML file 'ArticleList.fxml'.";
        assert articleDescriptionColumn != null : "fx:id=\"articleDescriptionColumn\" was not injected: check your FXML file 'ArticleList.fxml'.";
        assert grid != null : "fx:id=\"grid\" was not injected: check your FXML file 'ArticleList.fxml'.";
        assert articleNumberColumn != null : "fx:id=\"articleNumberColumn\" was not injected: check your FXML file 'ArticleList.fxml'.";
        assert validFromColumn != null : "fx:id=\"validFromColumn\" was not injected: check your FXML file 'ArticleList.fxml'.";
                
        tariffCodeColumn.setCellValueFactory(cellData -> cellData.getValue().tariffCodeColumnProperty());
        accountColumn.setCellValueFactory(cellData -> cellData.getValue().accountColumnProperty());
        articleDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().articleDescriptionColumnProperty());
        articleNumberColumn.setCellValueFactory(cellData -> cellData.getValue().articleNumberColumnProperty());
        validFromColumn.setCellValueFactory(cellData -> cellData.getValue().validFromColumnProperty());
        
        Account account= new Account();
        Arttar arttar= new Arttar();
        
        customerSelect.setItems(FXCollections.observableArrayList(account.getCustomers()));	
        
        grid.setItems(data);
        
		grid.setRowFactory(new Callback<TableView<ArticleModel>, TableRow<ArticleModel>>() {
			@Override
			public TableRow<ArticleModel> call(TableView<ArticleModel> param) {
				final TableRow<ArticleModel> row = new TableRow<ArticleModel>();

				ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");  
				removeMenuItem.setGraphic(new ImageView(new Image(ImageLoader.class.getResourceAsStream("delete.gif"))));
				final MenuItem editMenuItem = new MenuItem("Edit"); 
				editMenuItem.setGraphic(new ImageView(new Image(ImageLoader.class.getResourceAsStream("edit.gif"))));
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {  
                    @Override  
                    public void handle(ActionEvent event) {
                    	
                    	Action response = Dialogs.create()
        		    			.title("Confirm Dialog")
        		    	        .masthead("Article "+row.getItem().getArticleNumberColumn()+ "will be deleted.")
        		    	        .message("Do you want to continue?")
        		    	        .actions(Dialog.Actions.OK, Dialog.Actions.CANCEL)
        		    	        .showConfirm();

        		    	if (response == Dialog.Actions.OK) {
        		    		System.out.println(row.getItem().getArticleNumberColumn());
        		    		String articleCode=row.getItem().getArticleNumberColumn();
        		    		String accountId=row.getItem().getAccountColumn();
        		    		String startDate=row.getItem().getValidFromColumn();
        		    		if(arttar.deleteArticle(articleCode, accountId, "", startDate)){
        		    			 grid.getItems().remove(row.getItem());
        		    		}                        
        		    	}    
                    	
                    }  
                }); 
                
                
                editMenuItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {	
						
						ArticleModel row =grid.getSelectionModel().getSelectedItem();

						ArticleAddEditMainApp app = new ArticleAddEditMainApp(row);
						
				    	app.start();
					}  
                });

                
                
                contextMenu.getItems().add(editMenuItem);  
                contextMenu.getItems().add(removeMenuItem);  
               // Set context menu on row, but use a binding to make it only show for non-empty rows:  
                row.contextMenuProperty().bind(  
                        Bindings.when(row.emptyProperty())  
                        .then((ContextMenu)null)  
                        .otherwise(contextMenu)  
                );  
				return row;
			}

		});
        /*       
        MenuItem removeMenuItem = new MenuItem("Remove");
        contextMenu.getItems().add(removeMenuItem);
        grid.setContextMenu(contextMenu);
        // removeMenuItem will remove the row from the table:
        removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Deleting row");
			}
        	
        });
        */
                       
    }
    
    @FXML
    void buttonPressed(ActionEvent event) {

    	//Disable search field and button                        
    	searchProduct.setDisable(true);
    	customerSelect.setDisable(true);
    			
    	grid.setPlaceholder(new Label(""));
    	
    	Task<Void> task = new Task<Void>() {
			@Override
			public void run() {
				try {
					populateData(customerSelect.getSelectionModel().getSelectedItem(),searchProduct.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				Platform.runLater(new Runnable() {
                     @Override public void run() {
                    	 
                    	//grid.setPlaceholder(emptyTableMessage);
                    	grid.setItems(data);
                    	//Enable search field and button  
                    	searchProduct.setDisable(false);
                    	customerSelect.setDisable(false);
                 		//progressPane.toBack();
                     }
                 });

			}

			@Override
			protected Void call() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		new Thread(task).start(); 
    	
    }

    @FXML
    void addArticlePressed(ActionEvent event) {
    	
    	ArticleAddEditMainApp app = new ArticleAddEditMainApp(null);
    	app.start();

    }
    
    public void populateData(String customerCode, String searchString) throws Exception{
    	
    	Arttar arttar= new Arttar();
    	ArrayList<ArticleModel> list =new ArrayList<ArticleModel>();
    	
    	data.clear();
    	if(customerCode==null){
    		customerCode="";
    	}
    	list=arttar.getArticleModelCollection(customerCode, searchString);
    	data.addAll(list);
    		  	
    }
}
