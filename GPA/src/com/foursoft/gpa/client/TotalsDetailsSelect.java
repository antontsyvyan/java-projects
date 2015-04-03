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

import com.foursoft.gpa.reporting.TotalsDetailsReport;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.Processors;

public class TotalsDetailsSelect extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField period;
	private JComboBox<String> feedSystem = new JComboBox<String>();
	private JLabel errorMessage;
	
	private static String[] feedSystemCodes=new String[]{Processors.DOMPROC_FEEDING_SYSTEM,Processors.DEFAULT_FEEDING_SYSTEM};

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
		TotalsDetailsSelect frame = new TotalsDetailsSelect();
		// Display the window.
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public TotalsDetailsSelect() {
		super("Print Totals");
		setFrameIcon(new ImageIcon(TotalsDetailsSelect.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
		setIconifiable(true);
		setBackground(SystemColor.control);
		setResizable(true);
		addInternalFrameListener(new InternalFrameAdapter() {
		});
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 423, 270);
		getContentPane().setLayout(null);
		
		populateComboBox();
		
		JLabel lblNewLabel_1 = new JLabel("Period (YYYYMM)");
		lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		lblNewLabel_1.setBounds(38, 88, 128, 16);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { 
				buttonPressed();
			}          
		});
		btnNewButton.setBounds(38, 164, 97, 25);
		getContentPane().add(btnNewButton);
		
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(10, 246, 416, 52);
		getContentPane().add(errorMessage);
		
		period = new JTextField();
		period.setText("201402");
		period.setBounds(182, 86, 128, 22);
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
	}
	
	
	private void buttonPressed(){
		
		errorMessage.setText("");
		errorMessage.setForeground(Color.RED);
		DateUtils du=new DateUtils();
		if(!du.isThisDateValid(period.getText(),"yyyyMM")){
			errorMessage.setText("Period is not valid.");
		}else{
			String selectedFeedSystem=feedSystemCodes[feedSystem.getSelectedIndex()];
		    String selectedPeriod=period.getText();
		    
		    TotalsDetailsReport tdr=new TotalsDetailsReport(selectedFeedSystem,selectedPeriod);
		    tdr.generateReport();
		    if(tdr.isValid()){
		    	tdr.show();		    	
		    }else{
		    	JOptionPane.showMessageDialog(null,"There is nothing to show for this period");
		    }
		}
	}
}
