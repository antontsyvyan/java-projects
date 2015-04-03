package com.foursoft.gpa.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

import com.foursoft.gpa.models.RequestsGridModel;
import com.foursoft.gpa.models.RequestsGridRender;

public class RequestsGrid extends JInternalFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RequestsGrid frame = new RequestsGrid();
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
	public RequestsGrid() {
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		setTitle("Requests");
		setBounds(100, 100, 920, 400);
		
		JScrollPane scrollPane = new JScrollPane();		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setBackground(Color.WHITE);
		table.setShowGrid(false);
		table.setDefaultRenderer(String.class, new RequestsGridRender());
		table.setModel(new RequestsGridModel());
		
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setBackground(Color.white);
		
		scrollPane.getViewport().setBackground(Color.white);

		
		Timer timer=new Timer(0, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row=-1;
				try{		
					row=table.getSelectedRow();
				}catch(Exception ex){
					
				}
				
				//int col0=table.getColumnModel().getColumn(0).getPreferredWidth();
				table.setModel(new RequestsGridModel());
				if(row>=0){
					table.setRowSelectionInterval(row,row);
				}
				table.getColumnModel().getColumn(0).setPreferredWidth(100);
				table.getColumnModel().getColumn(1).setPreferredWidth(100);
				table.getColumnModel().getColumn(2).setPreferredWidth(100);
				table.getColumnModel().getColumn(3).setPreferredWidth(600);
				
			}});
		timer.setDelay(1000); // delay for 1 second;
		timer.start();
		
		

	}

}
