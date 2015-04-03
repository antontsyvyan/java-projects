package com.foursoft.gpa.models;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.foursoft.gpa.db.Excsrt;
import com.foursoft.gpa.db.Exctar;

public class TariffDrinkGridModel implements TableModel {
	
	private String[] columnNames = {"Soort","Ingangsdatum","Normaal tarief per hl"};	
	boolean[] columnEditables = new boolean[] {false,false,false};
	
	private ArrayList<TreeMap<String, String>> data;
	
	private TreeMap<String, String> info;
	
	private boolean firstTime=true;
		
	public TariffDrinkGridModel() {	
		//populateData();
	}
	
	public TariffDrinkGridModel(TreeMap<String, String> info) {
		this.info=info;
		populateData();
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
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
        	
        	if(firstTime){       		
        		firstTime=false;
        		return info.get(Excsrt.DB_EXCSRT_DESC);
        	}else{
        		break;
        	}
        case 1:
        	return tempDetail.get(Exctar.DB_EXCTAR_START_DATE);
        case 2:
        	return Double.parseDouble(tempDetail.get(Exctar.DB_EXCTAR_UNIT_TAR));
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
		
		Exctar excgrp=new Exctar();		
		this.data=excgrp.getExcsrtRecords(info.get(Excsrt.DB_EXCSRT_ID));				
	}
	
	public ArrayList<TreeMap<String, String>> getData() {
		return data;
	}
	
	public void setInfo(TreeMap<String, String> info) {
		this.info = info;
	}

	
}
