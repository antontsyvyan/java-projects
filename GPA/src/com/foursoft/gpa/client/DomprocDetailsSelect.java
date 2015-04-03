package com.foursoft.gpa.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;

import com.foursoft.gpa.db.Gpareq;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.Processors;

public class DomprocDetailsSelect extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField period;
	private JComboBox<String> feedSystem = new JComboBox<String>();
	private JComboBox<String>method=new JComboBox<String>();
	private JLabel errorMessage;
	private JCheckBox finalCheckBox;
	private JComboBox<String> clientCode;
	
	private static String[] feedSystemCodes=new String[]{Processors.DOMPROC_FEEDING_SYSTEM};
	
	private static String[] companyNames=new String[]{""};
	private static String[] companyCodes=new String[]{""};

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
		DomprocDetailsSelect frame = new DomprocDetailsSelect();
		// Display the window.
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public DomprocDetailsSelect() {
		super("Domicili\u00EBringsprocedure ");
		setFrameIcon(new ImageIcon(DomprocDetailsSelect.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		setIconifiable(true);
		setBackground(SystemColor.control);
		setResizable(true);
		addInternalFrameListener(new InternalFrameAdapter() {
		});
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 452, 339);
		getContentPane().setLayout(null);
		
		populateComboBox();
		
		JLabel lblNewLabel_1 = new JLabel("Period (YYYYMM)");
		lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		lblNewLabel_1.setBounds(38, 122, 128, 16);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { 
				buttonPressed();
			}          
		});
		btnNewButton.setBounds(38, 208, 97, 25);
		getContentPane().add(btnNewButton);
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(10, 246, 416, 52);
		getContentPane().add(errorMessage);
		
		period = new JTextField();
		period.setText("201402");
		period.setBounds(182, 120, 128, 22);
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
		
		finalCheckBox = new JCheckBox("Final run");
		finalCheckBox.setBounds(182, 209, 97, 23);
		getContentPane().add(finalCheckBox);
		
		JLabel label = new JLabel("Client code");
		label.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		label.setBounds(38, 84, 87, 27);
		getContentPane().add(label);
		
		clientCode = new JComboBox<String>();
		clientCode.setModel(new DefaultComboBoxModel<String>(companyNames));
		clientCode.setForeground(Color.BLACK);
		clientCode.setBounds(182, 87, 128, 22);
		getContentPane().add(clientCode);
		
		JLabel lblMethod = new JLabel("Method");
		lblMethod.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		lblMethod.setBounds(38, 155, 128, 16);
		getContentPane().add(lblMethod);
		
		method.setModel(new DefaultComboBoxModel<String>(Processors.METHODS));
		method.setForeground(Color.BLACK);
		method.setBounds(182, 153, 128, 22);
		getContentPane().add(method);

	}
	
	private void populateComboBox(){
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
		    String selectedPeriod=period.getText();
		    String selectedCustomer=companyCodes[clientCode.getSelectedIndex()];
		    String selectedMethod=Processors.METHODS[method.getSelectedIndex()];
		    String type=Processors.DOMPROC_PROOF;
		    
		    if(finalCheckBox.isSelected()){
		    	type=Processors.DOMPROC_FINAL;
		    }
		    
		    Gpareq gpareq= new Gpareq();
		    TreeMap<String, String> gpareqDetail=gpareq.initRecord();
		    gpareqDetail.put(Gpareq.DB_GPAREQ_TYPE, type);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_RLBCK, "N");
		    gpareqDetail.put(Gpareq.DB_GPAREQ_CUS, selectedCustomer);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_FED_SYS, selectedFeedSystem);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_PER, selectedPeriod);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_MTHD, selectedMethod);
		    gpareqDetail.put(Gpareq.DB_GPAREQ_STAT, Processors.REQUEST_STATUS_NEW);
		    
		    if(gpareq.insertGpareq(gpareqDetail)){
		    	errorMessage.setForeground(Color.GREEN);
				errorMessage.setText("Domproc has been requested successfully! The system will inform you when DISC will be ready");
		    }else{
		    	errorMessage.setText("Request has been failed.");
		    }
		    		    
			//errorMessage.setText("There are no records found for the customer " + comboBox.getSelectedItem()+" for period "+period.getText() );			
		}
	}
}
