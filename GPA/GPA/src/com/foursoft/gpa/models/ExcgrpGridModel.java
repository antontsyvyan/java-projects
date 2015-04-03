package com.foursoft.gpa.models;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.foursoft.gpa.db.Excgrp;

public class ExcgrpGridModel implements TableModel {
	
	private String[] columnNames = {"Groups"};	
	boolean[] columnEditables = new boolean[] {false};
	
	private ArrayList<TreeMap<String, String>> data;
	
	
	public ExcgrpGridModel() {
		
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
		
		return tempDetail.get(Excgrp.DB_EXCGRP_DESC);
		
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
		
		Excgrp excgrp=new Excgrp();		
		this.data=excgrp.getAllExcgrpRecords();				
	}
	
	public ArrayList<TreeMap<String, String>> getData() {
		return data;
	}

}
