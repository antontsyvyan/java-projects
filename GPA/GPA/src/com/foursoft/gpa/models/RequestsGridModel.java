package com.foursoft.gpa.models;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.foursoft.gpa.db.Gpareq;
import com.foursoft.gpa.utils.Processors;

public class RequestsGridModel implements TableModel {
	
	private String[] columnNames = {"Type", "Period", "Status", "Details"};	
	boolean[] columnEditables = new boolean[] {false, false, false, false};
	
	private ArrayList<TreeMap<String, String>> data;
	

	
	public RequestsGridModel() {
		populateData();
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return String.class;
        case 1: 
            return String.class;
        case 2: 
            return String.class;
        case 3: 
            return String.class;
        default:
            return String.class;
        }
    }

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		TreeMap<String, String> tempDetail=data.get(rowIndex);
		 switch (columnIndex) {
         case 0:
             return tempDetail.get(Gpareq.DB_GPAREQ_TYPE);
         case 1:
             return tempDetail.get(Gpareq.DB_GPAREQ_PER);
         case 2:
             return tempDetail.get(Gpareq.DB_GPAREQ_STAT);
         case 3:
        	 String ret="";
        	 if(tempDetail.get(Gpareq.DB_GPAREQ_STAT).equals(Processors.REQUEST_STATUS_ERROR)){
        		 ret=tempDetail.get(Gpareq.DB_GAPREQ_ERR_MSG);
        	 }else{
        		 if(!tempDetail.get(Gpareq.DB_GPAREQ_DISC_FILE).trim().equals("")){
        			 ret=tempDetail.get(Gpareq.DB_GPAREQ_DISC_FILE);
        		 }
        	 }
             return ret;
         }
		 
         return "";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return columnEditables[columnIndex];
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}
	
	private void populateData(){
		
		//Retrieve FEMUTI data;
		Gpareq gpareq=new Gpareq();		
		this.data=gpareq.readAllRecords(false);
	}

}
