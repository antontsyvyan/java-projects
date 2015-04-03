package com.foursoft.gpa.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.foursoft.gpa.db.Cdbksec;
import com.foursoft.gpa.db.Cdbktable;
import com.foursoft.gpa.utils.ApplicationConstants;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.TopFileFilter;

public class GpaDashboard extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JDesktopPane desktop;
	private ProductsFrame productFrame=null;
	private GpaDetailsSelect gpaDetailsSelect=null;
	private GpaDetailsSelect gpaProofRunFrame=null;
	private GpaDetailsSelect gpaFinalRunFrame=null;
	private DomprocDetailsSelect domprocRunFrame=null;
	private GpaDetailsSelect gpaResetFrame=null;
	private TotalsDetailsSelect totalsDetailsSelect=null;
	private ExcgrpGrid excgrpGrid=null;
	private ExciseTariffs exciseTariffs=null;
	private CodeBookGrid codeBookGrid=null;
	private RequestsGrid requestsGrid=null;
		
	public static final Font defaultMenuFont=new Font("Microsoft Sans Serif", Font.PLAIN, 15);

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
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);
		// Create and set up the window.
		GpaDashboard frame = new GpaDashboard();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window.
		frame.setVisible(true);
	
	}

	/**
	 * Create the frame.
	 */
	public GpaDashboard() {

		super("GPA Dashboard");
		setBackground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GpaDashboard.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height	- inset * 2);
		
		//Create menu		
		setJMenuBar(createMenuBar());
		
		//Create desktop pane
		desktop = new JDesktopPane(); // a specialized layered pane		
		desktop.setBackground(new Color(119, 136, 153));
		setContentPane(desktop);
		// Make dragging a little faster but perhaps uglier.
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		
	}
	
	protected JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
			
		JMenu menu = new JMenu("Interfaces");
		menu.setFont(defaultMenuFont);
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Product");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Company");
		menuItem.setFont(defaultMenuFont);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Inbound");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Requests");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu = new JMenu("Setup");
		menu.setFont(defaultMenuFont);
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Auto jobs");
		menuItem.setFont(defaultMenuFont);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Settings");
		menuItem.setFont(defaultMenuFont);
		menu.add(menuItem);
		
		menu = new JMenu("Codebook");
		menu.setFont(defaultMenuFont);
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Excise tax");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		//
		Cdbksec cdbksec=new Cdbksec();
		Cdbktable cdbktbl= new Cdbktable();
		
		ArrayList<TreeMap<String, String>> sections=cdbksec.getAllRecords(Processors.NL);
		for (int i = 0; i < sections.size(); i++) {
    		TreeMap<String, String> det=sections.get(i);  
    		
    		JMenu submenu = new JMenu(det.get(Cdbksec.DB_CDBKSEC_DESC));
    		menu.setFont(defaultMenuFont);
    		menu.add(submenu);  
    		
    		ArrayList<TreeMap<String, String>> tables=cdbktbl.getAllRecords(Processors.NL,det.get(Cdbksec.DB_CDBKSEC_DECL_TYPE));
    		for (int j = 0; j < tables.size(); j++) {
    			TreeMap<String, String> sub=tables.get(j);  
    			String itemName=sub.get(Cdbktable.DB_CDBKTBL_NM)+ " "+sub.get(Cdbktable.DB_CDBKTBL_DESC);
    			
    			menuItem = new JMenuItem(itemName);
    			menuItem.setFont(defaultMenuFont);
    			menuItem.putClientProperty(Cdbktable.DB_CDBKTBL_LAN,sub.get(Cdbktable.DB_CDBKTBL_LAN));
    			menuItem.putClientProperty(Cdbktable.DB_CDBKTBL_DECL_TYPE,sub.get(Cdbktable.DB_CDBKTBL_DECL_TYPE));
    			menuItem.putClientProperty(Cdbktable.DB_CDBKTBL_NM,sub.get(Cdbktable.DB_CDBKTBL_NM));
    			menuItem.addActionListener(new ActionListener() {
    	            @Override
    	            public void actionPerformed(ActionEvent event) {
    	            	
    	            	JMenuItem item=(JMenuItem)event.getSource();
    	            	String language=(String)item.getClientProperty(Cdbktable.DB_CDBKTBL_LAN);
    	            	String type=(String)item.getClientProperty(Cdbktable.DB_CDBKTBL_DECL_TYPE);
    	            	String table=(String)item.getClientProperty(Cdbktable.DB_CDBKTBL_NM);
    	            	createCodeBook(language,type,table);
    	            }
    	        });
    			submenu.add(menuItem);
    		}
    	}
				
		menu = new JMenu("GPA");
		menu.setFont(defaultMenuFont);
		menuBar.add(menu);
		
		menuItem = new JMenuItem("GPA Details");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
						
		menuItem = new JMenuItem("GPA Test run");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("GPA Final run");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Domproc");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Totals");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menu = new JMenu("Admin");
		menu.setFont(defaultMenuFont);
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Reset GPA");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Logging");
		menuItem.setFont(defaultMenuFont);
		menuItem.addActionListener(this);
		menu.add(menuItem);
				
		return menuBar;
	}
	
	
	protected void createProductFrame() {		
		if (productFrame != null) {
			//remove frame if already exists
			productFrame.dispose();
		}
		
		// Create a new internal frame.
		productFrame = new ProductsFrame();
		desktop.add(productFrame);

		productFrame.setVisible(true);

		try {
			productFrame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}

	protected void createTotalsSelect() {	
		if (totalsDetailsSelect != null) {
			//remove frame if already exists
			totalsDetailsSelect.dispose();
		}
		
		totalsDetailsSelect= new TotalsDetailsSelect();
		desktop.add(totalsDetailsSelect);
		totalsDetailsSelect.setVisible(true);
		
		try {
			totalsDetailsSelect.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
		
	}
	
	protected void createGpaDetailsSelect() {		
		if (gpaDetailsSelect != null) {
			//remove frame if already exists
			gpaDetailsSelect.dispose();
		}
		
		// Create a new internal frame.
		gpaDetailsSelect = new GpaDetailsSelect();
		desktop.add(gpaDetailsSelect);

		gpaDetailsSelect.setVisible(true);

		try {
			gpaDetailsSelect.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
	
	protected void createGpaProofRunFrame(){
		if (gpaProofRunFrame != null) {
			//remove frame if already exists
			gpaProofRunFrame.dispose();
		}
		
		// Create a new internal frame.
		gpaProofRunFrame = new GpaDetailsSelect();
		gpaProofRunFrame.setType(1);
		gpaProofRunFrame.setTitle("GPA Proof Run");
		desktop.add(gpaProofRunFrame);

		gpaProofRunFrame.setVisible(true);

		try {
			gpaProofRunFrame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
	
	protected void createGpaFinalRunFrame(){
		if (gpaFinalRunFrame != null) {
			//remove frame if already exists
			gpaFinalRunFrame.dispose();
		}
		
		// Create a new internal frame.
		gpaFinalRunFrame = new GpaDetailsSelect();
		gpaFinalRunFrame.setType(2);
		gpaFinalRunFrame.setTitle("GPA Final Run");
		desktop.add(gpaFinalRunFrame);

		gpaFinalRunFrame.setVisible(true);

		try {
			gpaFinalRunFrame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
	
	protected void createDomprocfRunFrame(){
		if (domprocRunFrame != null) {
			//remove frame if already exists
			domprocRunFrame.dispose();
		}
		
		// Create a new internal frame.
		domprocRunFrame = new DomprocDetailsSelect();
		domprocRunFrame.setTitle("Domicilieringsprocedure");
		desktop.add(domprocRunFrame);

		domprocRunFrame.setVisible(true);

		try {
			domprocRunFrame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
	
	protected void createGpaResetFrame(){
		if (gpaResetFrame != null) {
			//remove frame if already exists
			gpaResetFrame.dispose();
		}
		
		// Create a new internal frame.
		gpaResetFrame = new GpaDetailsSelect();
		gpaResetFrame.setType(9);
		gpaResetFrame.setTitle("Reset final GPA");
		desktop.add(gpaResetFrame);

		gpaResetFrame.setVisible(true);

		try {
			gpaResetFrame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
	
	protected void createExcgrpGrid(){
		if (excgrpGrid != null) {
			//remove frame if already exists
			excgrpGrid.dispose();
		}
		excgrpGrid=new ExcgrpGrid();
		desktop.add(excgrpGrid);
		excgrpGrid.setVisible(true);
		
		try {
			excgrpGrid.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
	
	protected void createExciseTariffs(){
		if (exciseTariffs != null) {
			//remove frame if already exists
			exciseTariffs.dispose();
		}
		exciseTariffs=new ExciseTariffs();
		desktop.add(exciseTariffs);
		exciseTariffs.setVisible(true);
		
		try {
			exciseTariffs.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
		}
	}
	
	protected void createCodeBook(String language,String type, String codeTable){
		if(codeBookGrid!=null){
			codeBookGrid.dispose();
		}
		codeBookGrid=new CodeBookGrid(language, type, codeTable);
		desktop.add(codeBookGrid);
		codeBookGrid.setVisible(true);
	}
	
	protected void createRequests(){
		if(requestsGrid!=null){
			requestsGrid.dispose();
		}
		requestsGrid=new RequestsGrid();
		desktop.add(requestsGrid);
		requestsGrid.setVisible(true);
	}
	
	private void openLogViewer(String path){
		//construct path to the viewer
		File f = new File(path);
		if(!f.exists()){
			JOptionPane.showMessageDialog(desktop,"File "+path+" doesn't exists!");
			return;	
		}
		File binDir = new File(ApplicationConstants.BIN_DIR);
		try {
			StringBuffer cmd= new StringBuffer();
			cmd.append(binDir.getCanonicalPath());
			cmd.append("\\");
			cmd.append(ApplicationConstants.VIEWER);
			cmd.append(" ");
			cmd.append("\"");
			cmd.append(path);
			cmd.append("\"");
			
			Runtime.getRuntime().exec(cmd.toString());
		} catch (IOException e) {

		}
	}
	
	private String getApplicationLogPath(){
		
		File logDir = new File(ApplicationConstants.LOG_DIR);
		String path="";
		
		try {

			TopFileFilter tff=new TopFileFilter();
			logDir.listFiles(tff);
			path=tff.getTopFile().getCanonicalPath();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//return buf.toString();
		return path;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        System.out.println("test");
		if ("product".equals(e.getActionCommand().toLowerCase())) {
			createProductFrame();
		}else if("gpa details".equals(e.getActionCommand().toLowerCase())){
			createGpaDetailsSelect();
		}else if("totals".equals(e.getActionCommand().toLowerCase())){
			createTotalsSelect();
		}else if("domproc".equals(e.getActionCommand().toLowerCase())){
			createDomprocfRunFrame();
		}else if("gpa test run".equals(e.getActionCommand().toLowerCase())){
			createGpaProofRunFrame();
		}else if("gpa final run".equals(e.getActionCommand().toLowerCase())){
			createGpaFinalRunFrame();
		}else if("reset gpa".equals(e.getActionCommand().toLowerCase())){
			createGpaResetFrame();
		}else if("logging".equals(e.getActionCommand().toLowerCase())){
			openLogViewer(getApplicationLogPath());
		}else if("inbound".equals(e.getActionCommand().toLowerCase())){
			//start inbound process
		}else if("excise tax".equals(e.getActionCommand().toLowerCase())){
			createExciseTariffs();
		}else if("requests".equals(e.getActionCommand().toLowerCase())){
			createRequests();
		}else {
			quit();
		}
	}
	
	// Quit the application.
	protected void quit() {
		System.exit(0);
	}
}
