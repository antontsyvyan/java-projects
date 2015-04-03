package com.foursoft.gpa.models;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.foursoft.gpa.db.Femuti;
import com.foursoft.gpa.db.Femutu;
import com.foursoft.gpa.utils.Processors;

public class GpaDetailsGridModel implements TableModel {
	
	private String[] columnNames = {"Status", "Feeding system", "I/O", "Period", "Job number", "Document", "Transaction", "code/type"};	
	boolean[] columnEditables = new boolean[] {false, false, false, false, false, false, false, false};
	
	private ArrayList<TreeMap<String, String>> data;
	
	private String feedSystem;
	private String clientCode;
	private String period;
	
	public GpaDetailsGridModel() {
		
	}
	
	public GpaDetailsGridModel(String feedSystem,String clientCode,String period) {
		
		this.period=period;
		this.clientCode=clientCode;
		this.feedSystem=feedSystem;
		
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
             return tempDetail.get(Femuti.DB_FEMUTI_STAT)==null? tempDetail.get(Femutu.DB_FEMUTU_STAT):tempDetail.get(Femuti.DB_FEMUTI_STAT);
         case 1:
             return tempDetail.get(Femuti.DB_FEED_SYS_FEMUTI)==null? tempDetail.get(Femutu.DB_FEED_SYS_FEMUTU):tempDetail.get(Femuti.DB_FEED_SYS_FEMUTI);
         case 2:
             return tempDetail.get(Femuti.DB_FEMUTI_IN_OUT_BOUND)==null? tempDetail.get(Femutu.DB_FEMUTU_IN_OUT_BOUND):tempDetail.get(Femuti.DB_FEMUTI_IN_OUT_BOUND);
         case 3:
             return tempDetail.get(Femuti.DB_FEMUTI_PERIOD)==null? tempDetail.get(Femutu.DB_FEMUTU_PERIOD):tempDetail.get(Femuti.DB_FEMUTI_PERIOD);
         case 4:
             return tempDetail.get(Femuti.DB_FEMUTI_JOB_NO)==null?tempDetail.get(Femutu.DB_FEMUTU_JOB_NO)+"/"+tempDetail.get(Femutu.DB_FEMUTU_LINE_NO):tempDetail.get(Femuti.DB_FEMUTI_JOB_NO)+"/"+tempDetail.get(Femuti.DB_FEMUTI_LINE_NO);
         case 5:
             return tempDetail.get(Femuti.DB_FEMUTI_DOC_NO)==null?tempDetail.get(Femutu.DB_FEMUTU_DOC_NO):tempDetail.get(Femuti.DB_FEMUTI_DOC_NO);
         case 6:
             return tempDetail.get(Femuti.DB_FEMUTI_TRANS_CODE)==null?tempDetail.get(Femutu.DB_FEMUTU_TRANS_CODE):tempDetail.get(Femuti.DB_FEMUTI_TRANS_CODE);
         case 7:
             return tempDetail.get(Femuti.DB_FEMUTI_TRANS_CODE_K7)==null?tempDetail.get(Femutu.DB_FEMUTU_TRANS_TYPE):tempDetail.get(Femuti.DB_FEMUTI_TRANS_CODE_K7);
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
		Femuti femuti=new Femuti();		
		this.data=femuti.readRecordsForPeriod(feedSystem,clientCode, period,Processors.PRELIMINARY);				
		//Retrieve FEMUTU data;
		Femutu femutu=new Femutu();
		ArrayList<TreeMap<String, String>> data=femutu.readRecordsForPeriod(feedSystem,clientCode, period, Processors.OUTBOUND, Processors.PRELIMINARY);
		
		if(data!=null && data.size()>0){
			this.data.addAll(data);
		}
	}

}
