package com.foursoft.gpa.models;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.foursoft.gpa.db.Cdbk;

public class CodesGridModel implements TableModel {
	
	private String[] columnNames = {"Code", "Description", "Remarks"};	
	boolean[] columnEditables = new boolean[] {false, false, false};
	
	private ArrayList<TreeMap<String, String>> data;
	
	private String language;
	private String type;
	private String table;
	
	public CodesGridModel() {
		
	}
	
	public CodesGridModel(String language,String type,String table) {
		
		this.language=language;
		this.type=type;
		this.table=table;
		
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
             return tempDetail.get(Cdbk.DB_CDBK_CODE);
         case 1:
             return tempDetail.get(Cdbk.DB_CDBK_DESC1);
         case 2:
             return tempDetail.get(Cdbk.DB_CDBK_REM);

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
		
		//Retrieve data;
		Cdbk cdbk=new Cdbk();		
		this.data=cdbk.getTable(language,type,table);				
	}

}
