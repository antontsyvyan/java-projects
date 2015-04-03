package com.foursoft.gpa.models;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.foursoft.gpa.utils.Processors;

public class RequestsGridRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Color white = Color.white;
	private Color black = Color.black;
	private Color green =new java.awt.Color(0, 170, 0);
	private Color red =new java.awt.Color(240, 0, 0);
	private Color blue =new java.awt.Color(0, 140, 255);
	private Color selBackground=new java.awt.Color(184, 207, 229);

	public RequestsGridRender() {
		// TODO Auto-generated constructor stub
	}
	 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 
		 Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	     
		 //Default colors
		 c.setBackground(white);
		 c.setForeground(black);
		 
		 if(column==2){
			 if(value.equals(Processors.REQUEST_STATUS_NEW)){
				 c.setBackground(blue);
				 c.setForeground(white);
			 }
			 
			 if(value.equals(Processors.REQUEST_STATUS_ERROR)){
				 c.setBackground(red);
				 c.setForeground(white);
			 }
			 
			 if(value.equals(Processors.REQUEST_STATUS_PROCESSED)){
				 c.setBackground(green);
				 c.setForeground(white);
			 }
		 
		 }
		 
		 //override when selected row
		 if(isSelected){
			 c.setBackground(selBackground);
			 c.setForeground(black);
		 }
		 		 
		 
	     return c;
		 

	 }

}
