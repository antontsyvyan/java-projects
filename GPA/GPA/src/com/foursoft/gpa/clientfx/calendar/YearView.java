package com.foursoft.gpa.clientfx.calendar;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.TreeMap;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.controlsfx.dialog.Dialogs;

import com.foursoft.gpa.db.Prmchk;
import com.foursoft.gpa.reporting.TotalsDetailsReport;
import com.foursoft.gpa.utils.Processors;

/**
 * The year view shows the months.
 *
 * @author Christian Schudt
 */
final class YearView extends DatePane {

    private static final String CSS_CALENDAR_YEAR_VIEW = "calendar-year-view";
    private static final String CSS_CALENDAR_MONTH_BUTTON = "calendar-month-button";
   //private static final String CSS_CALENDAR_MONTH_CLOSED_BUTTON = "calendar-month-closed-button";
    
    
    int monthNumber = 0;


    public YearView(final CalendarView calendarView) {
        super(calendarView);

        getStyleClass().add(CSS_CALENDAR_YEAR_VIEW);

        // When the locale changes, update the contents (month names).
        calendarView.localeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateContent();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void buildContent() {

        // Get the number of months. I read, there are some lunar calendars, with more than 12 months.
        int numberOfMonths = calendarView.getCalendar().getMaximum(Calendar.MONTH) + 1;

        int numberOfColumns = 3;
        
        
        monthNumber=0;
        for (int i = 0; i < numberOfMonths; i++) {
            //final int j = i;
            Button button = new Button();
            button.getStyleClass().add(CSS_CALENDAR_MONTH_BUTTON);

            // Make the button stretch.
            button.setMaxWidth(Double.MAX_VALUE);
            button.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(button, Priority.ALWAYS);
            GridPane.setHgrow(button, Priority.ALWAYS);
                      
            int rowIndex = i % numberOfColumns;
            int colIndex = (i - rowIndex) / numberOfColumns;
            add(button, rowIndex, colIndex);
            
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateContent() {

        DateFormatSymbols symbols = new DateFormatSymbols(calendarView.localeProperty().get());
        String[] monthNames = symbols.getShortMonths();
        
        String year=getDateFormat("yyyy").format(calendarView.getCalendar().getTime());       
        for (int i = 1; i < monthNames.length; i++) {
            Button button = (Button) getChildren().get(i - 1);
            button.setText(monthNames[i - 1]);
            
            String month=String.format("%02d", i); 	
            button.setUserData(month);
            
            Prmchk prmchk=new Prmchk();
            TreeMap<String, String> det=prmchk.readPrmchkRecord(Processors.DOMPROC_FEEDING_SYSTEM,year+(String)button.getUserData());
            //button.getStyleClass().add(CSS_CALENDAR_MONTH_BUTTON);
            button.setStyle("-fx-text-fill: green");
            if(det!=null && det.size()>0){
            	button.setDisable(false);
            	if(det.get(Prmchk.DB_PRMCHK_DISK_CREATED).equals(Processors.DISK_FINAL)){
            		 button.setStyle("-fx-text-fill: red");
            	}
            	button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                    
                        if (calendarView.currentlyViewing.get() == Calendar.YEAR) {
                        	String period=getDateFormat("yyyy").format(calendarView.getCalendar().getTime())+(String)button.getUserData();
                        	TotalsDetailsReport tdr=new TotalsDetailsReport(Processors.DOMPROC_FEEDING_SYSTEM,period);
                        	tdr.generateReport();
                        	if(tdr.isValid()){
                        		tdr.show();
                        	}else{
                        		Dialogs.create()
								  .nativeTitleBar()
							      .title("Message")
							      .masthead(null)
							      .message( "There is nothing to show for period "+det.get(Prmchk.DB_PRMCHK_PERIOD))
							      .showInformation();
                        	}
                        	
                        }                   
                        
                    }
                });
            	
            }else{
                button.setDisable(true);
            }
        }

        title.set(year);
    }
}
