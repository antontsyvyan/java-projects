package com.foursoft.gpa.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import com.foursoft.gpa.db.Excgrp;
import com.foursoft.gpa.models.ExcgrpGridModel;

public class ExcgrpGrid extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTable tableSave;
	private JScrollPane scrollPane;
	private ExcgrpGridModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExcgrpGrid frame = new ExcgrpGrid();
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
	public ExcgrpGrid() {
		setIconifiable(true);
		setClosable(true);
		setTitle("Excise");
		setBounds(100, 100, 590, 357);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addMouseListener(new CloseButtonAdapter());
		panel.add(btnNewButton);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setForeground(new Color(0, 153, 255));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Vrinda", Font.BOLD, 14));
		table.setRowHeight(20);
		table.setShowVerticalLines(false);
		table.setBackground(UIManager.getColor("Button.background"));
		table.setShowHorizontalLines(false);
		model=new ExcgrpGridModel();
		table.setModel(model);
		table.addMouseListener(new Adapter());
		scrollPane.setViewportView(table);

	}
	
	class Adapter extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent event) {
			//only when double clicked
			if (event.getClickCount() == 2) {
				int row = table.getSelectedRow();
				
				String id=model.getData().get(row).get(Excgrp.DB_EXCGRP_ID);
				System.out.println("Click row: "+row+" - "+id);
				tableSave=table;
				table = new JTable();
				scrollPane.setViewportView(table);
			}
			

		}
	}
	
	class CloseButtonAdapter extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent event) {
			
			if(tableSave!=null){
				table=tableSave;
				scrollPane.setViewportView(table);
			}
		}
	}

}
