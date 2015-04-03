package com.foursoft.gpa.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import com.foursoft.gpa.models.GpaDetailsGridModel;
import com.foursoft.gpa.models.GpaDetailsGridRender;

import java.awt.Color;

public class GpaDetailsGrid extends JInternalFrame {
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
					GpaDetailsGrid frame = new GpaDetailsGrid("","","");
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
	public GpaDetailsGrid(String feedSystem,String clientCode,String period) {
		getContentPane().setBackground(new Color(238, 232, 170));
		
		setTitle("GPA Details");
		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setBounds(100, 100, 769, 445);
		
		getContentPane().setLayout(new BorderLayout(0, 0));		
		table = new JTable();		
		table.setShowGrid(false);
		table.setBackground(new Color(238, 232, 170));
		table.setDefaultRenderer(String.class, new GpaDetailsGridRender());
		table.setModel(new GpaDetailsGridModel(feedSystem,clientCode,period));
		JScrollPane panel_grid = new JScrollPane(table);
		getContentPane().add(panel_grid);
		
		JPanel panel_control = new JPanel();
		getContentPane().add(panel_control, BorderLayout.SOUTH);
		panel_control.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnClose = new JButton("Close");
		panel_control.add(btnClose);

	}
	
	public void setNewTitle(String title){
		setTitle(title);
	}
}
