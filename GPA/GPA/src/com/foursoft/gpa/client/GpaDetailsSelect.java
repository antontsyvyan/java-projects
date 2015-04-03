package com.foursoft.gpa.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;

import com.foursoft.gpa.GpaCore;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.Processors;

public class GpaDetailsSelect extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> clientCode;
	private JTextField period;
	private JComboBox<String> feedSystem = new JComboBox<String>();
	private JLabel errorMessage;
	private int type=0;
	
	public void setType(int type) {
		this.type = type;
	}


	private static String[] companyNames=new String[]{"","Anheuser-Busch InBev","Customer KAW","Customer LOL"};
	private static String[] companyCodes=new String[]{"","ABI","KAW","LOL"};
	private static String[] feedSystemCodes=new String[]{Processors.DEFAULT_FEEDING_SYSTEM};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create and set up the window.
		GpaDetailsSelect frame = new GpaDetailsSelect();
		// Display the window.
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public GpaDetailsSelect() {
		super("Select GPA Details");
		setFrameIcon(new ImageIcon(GpaDetailsSelect.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
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
		lblNewLabel.setBounds(38, 81, 87, 27);
		getContentPane().add(lblNewLabel);
		
		populateComboBox();
		
		JLabel lblNewLabel_1 = new JLabel("Period (YYYYMM)");
		lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		lblNewLabel_1.setBounds(38, 121, 128, 16);
		getContentPane().add(lblNewLabel_1);
		
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
		errorMessage.setBounds(12, 217, 367, 16);
		getContentPane().add(errorMessage);
		
		period = new JTextField();
		period.setText("201309");
		period.setBounds(182, 118, 128, 22);
		getContentPane().add(period);
		period.setColumns(10);
		
		JLabel lblFeedingSystem = new JLabel("Feeding system");
		lblFeedingSystem.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		lblFeedingSystem.setBounds(38, 48, 128, 27);
		getContentPane().add(lblFeedingSystem);
			
		feedSystem.setModel(new DefaultComboBoxModel<String>(feedSystemCodes));
		feedSystem.setForeground(Color.BLACK);
		feedSystem.setBounds(182, 50, 128, 22);
		getContentPane().add(feedSystem);

	}
	private void populateComboBox(){
		
		clientCode = new JComboBox<String>();
		clientCode.setModel(new DefaultComboBoxModel<String>(companyNames));		
		clientCode.setForeground(Color.BLACK);
		clientCode.setBounds(182, 83, 128, 22);
		getContentPane().add(clientCode);
	}
	
	private void buttonPressed(){
		
		//Femart db= new Femart();
		//ArrayList<TreeMap<String, String>> femart = db.readProductsPerCustomer(companyCodes[comboBox.getSelectedIndex()]);
		
		errorMessage.setText("");
		errorMessage.setForeground(Color.RED);
		DateUtils du=new DateUtils();
		if(!du.isThisDateValid(period.getText(),"yyyyMM")){
			errorMessage.setText("Period is not valid.");
		}else{
			String selectedFeedSystem=feedSystemCodes[feedSystem.getSelectedIndex()];
		    String selectedCustomer=companyCodes[clientCode.getSelectedIndex()];
		    String selectedPeriod=period.getText();
		    GpaCore gpa=new GpaCore(selectedFeedSystem,selectedCustomer,selectedPeriod);
			
		    switch(type){
			case	1:			
				if(!gpa.testGpa()){
					errorMessage.setText(gpa.getErrorMessage());
				}
				break;
			case	2:
				if(!gpa.finalGpa()){
					errorMessage.setText(gpa.getErrorMessage());
				}else{
					errorMessage.setForeground(Color.GREEN);
					errorMessage.setText("GPA has been completed successfully!");
				}
				break;
			case	4:
				if(!gpa.finalDomproc()){
					errorMessage.setText(gpa.getErrorMessage());
				}else{
					errorMessage.setForeground(Color.GREEN);
					errorMessage.setText("Domproc has been completed successfully!");
				}
				break;
			case	9:
				if(JOptionPane.showConfirmDialog(this,"This operation can not be undone. Are you sure?", "Warning",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
					if(gpa.resetGpa()){
						errorMessage.setForeground(Color.GREEN);
						errorMessage.setText("GPA has been reset successfully!");
					}else{
						errorMessage.setText(gpa.getErrorMessage());
					}
				}
					

				break;
			default:
				GpaDetailsGrid dialog= new GpaDetailsGrid(selectedFeedSystem,selectedCustomer,selectedPeriod);				
				dialog.setNewTitle("GPA Details | customer: "+clientCode.getSelectedItem()+" | Period: "+selectedPeriod);
				this.getParent().add(dialog);
				dialog.setVisible(true);
			}
			//errorMessage.setText("There are no records found for the customer " + comboBox.getSelectedItem()+" for period "+period.getText() );			
		}
	}
}
