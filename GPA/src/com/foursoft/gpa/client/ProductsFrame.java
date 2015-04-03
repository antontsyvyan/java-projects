package com.foursoft.gpa.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;

import com.foursoft.gpa.db.Femart;

public class ProductsFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ProductsFrame frame;
	private static JComboBox<String> comboBox;
	private static JLabel errorMessage;
	private static JCheckBox checkBox;
	
	private static String[] companyNames=new String[]{"Four-soft","Rhenus Logistics","Bring"};
	private static String[] companyCodes=new String[]{"4S","RHA","PNL"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ProductsFrame();
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
	public ProductsFrame() {
		super("Products");
		setFrameIcon(new ImageIcon(ProductsFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		setIconifiable(true);
		setBackground(SystemColor.control);
		setResizable(true);
		addInternalFrameListener(new InternalFrameAdapter() {
		});
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 407, 282);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Client code");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		lblNewLabel.setBounds(38, 37, 87, 27);
		getContentPane().add(lblNewLabel);
		
		populateComboBox();
		
		JLabel lblNewLabel_1 = new JLabel("Control");
		lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		lblNewLabel_1.setBounds(38, 99, 56, 16);
		getContentPane().add(lblNewLabel_1);
		
		checkBox = new JCheckBox("");
		checkBox.setBounds(182, 99, 113, 25);
		getContentPane().add(checkBox);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { 
				buttonPressed();
			}          
		});
		btnNewButton.setBounds(152, 172, 97, 25);
		getContentPane().add(btnNewButton);
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(38, 217, 321, 16);
		getContentPane().add(errorMessage);

	}
	private void populateComboBox(){
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(companyNames));
		comboBox.setForeground(Color.BLACK);
		comboBox.setBounds(182, 39, 128, 22);
		getContentPane().add(comboBox);
	}
	
	private void buttonPressed(){
		
		Femart db= new Femart();
		ArrayList<TreeMap<String, String>> femart = db.readProductsPerCustomer(companyCodes[comboBox.getSelectedIndex()]);
		if (femart != null && femart.size() > 0) {
			if(checkBox.isSelected()){
				//Start prompt screen for each record
				ProductGrid dialog= new ProductGrid();
				this.getParent().add(dialog);
				dialog.setVisible(true);
			}else{
				//Start processing without prompt
			}
		}else{
			//Display error message
			errorMessage.setText("There are no records found for the customer " + comboBox.getSelectedItem()+".");
		}
	}
}
