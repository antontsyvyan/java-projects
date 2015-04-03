package com.foursoft.gpa.models;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class GpaDetailsGridRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GpaDetailsGridRender() {
		// TODO Auto-generated constructor stub
	}
	 public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
		 
		 Component c = super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column);
	     
		 c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
		 
		 c.setBackground(new java.awt.Color(255, 255, 204));
		 
		 String value=(String) table.getValueAt(row, 2);
		 
		 if(value.equals("I")){
			 c.setBackground(new java.awt.Color(220, 230, 241));
		 }
		 		 
		 
	     return c;
		 

	 }

}
