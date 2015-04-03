package com.foursoft.gpa.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.foursoft.gpa.models.CodesGridModel;

public class CodeBookGrid extends JInternalFrame {

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
					CodeBookGrid frame = new CodeBookGrid("","","");
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
	public CodeBookGrid(String language,String type, String codeTable) {
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		setTitle("Code Book");
		setBounds(100, 100, 710, 433);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new CodesGridModel(language,type,codeTable));
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

	}

}
