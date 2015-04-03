package com.foursoft.gpa.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class ProductGrid extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JLabel messageLable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductGrid frame = new ProductGrid();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductGrid() {
		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setBounds(100, 100, 769, 445);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new GridModel());
		
		//setButtonColumn(table, table.getColumnModel().getColumn(4));
		
		/*table.setModel(new DefaultTableModel(new Object[][] {},new String[] {"Product", "Product description", "Import duty", "HS code", "Action"}) {
		
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {	Object.class, String.class, Object.class, Object.class, Object.class};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		*/
		
		table.getColumnModel().getColumn(0).setPreferredWidth(163);
		table.getColumnModel().getColumn(1).setPreferredWidth(198);
		table.getColumnModel().getColumn(2).setPreferredWidth(84);
		table.getColumnModel().getColumn(3).setPreferredWidth(268);
		table.getColumnModel().getColumn(4).setResizable(false);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
		
		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		buttonPane.add(panel_1);
		
		messageLable = new JLabel("");
		messageLable.setForeground(Color.RED);
		panel_1.add(messageLable);
		messageLable.setBackground(Color.WHITE);
		messageLable.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		buttonPane.add(panel_2);
		
		JButton acceptButton = new JButton("Accept");
		panel_2.add(acceptButton);
		acceptButton.setActionCommand("accept");
		acceptButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { 
				buttonPressed();
			}          
		});
		


	}
	
	private void buttonPressed(){
		processTable();
	}
	
	private void processTable(){
		boolean selection=false;
		//Clear message field
		messageLable.setText("");
		if(table.getRowCount()>0){
			for(int i=0;i<table.getRowCount();i++){
				if((boolean) table.getModel().getValueAt(i, 4)){
					selection=true;
				}
			}
		}
		
		if(!selection){
			messageLable.setText("Nothing has been selected");
		}
	}
	

	class GridModel extends AbstractTableModel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] columnNames = {"Product", "Product description", "Import duty", "HS code", "Action"};	
		boolean[] columnEditables = new boolean[] {false, false, false, true, true};
		
		private Object[][] data={{"Prod","Descr","Duty","HS",new Boolean(false)}};

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;

		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
		

		public String getColumnName(int col) {
			return columnNames[col];
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the last column
		 * would contain text ("true"/"false"), rather than a check box.
		 */
		public Class<? extends Object> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		
		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object value, int row, int col) {

			data[row][col] = value;
			fireTableCellUpdated(row, col);

		}
		
	}
	
}
