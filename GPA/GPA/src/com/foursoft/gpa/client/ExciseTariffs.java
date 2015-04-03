package com.foursoft.gpa.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.foursoft.gpa.db.Excgrp;
import com.foursoft.gpa.db.Excsrt;
import com.foursoft.gpa.models.TariffDrinkGridModel;

public class ExciseTariffs extends JInternalFrame implements TreeSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTree tree;
	private ArrayList<TreeMap<String, String>> groups;

	// Optionally play with line styles. Possible values are
	// "Angled" (the default), "Horizontal", and "None".
	private static boolean playWithLineStyle = false;
	private static String lineStyle = "Horizontal";
	private JSplitPane splitPane;
	private JLabel textLabel;
	private JPanel detailsPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExciseTariffs frame = new ExciseTariffs();
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
	public ExciseTariffs() {
		
		setResizable(true);
		setIconifiable(true);
		setClosable(true);
		setTitle("Excise tariffs");
		setBounds(100, 100, 641, 431);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(200);
		splitPane.setPreferredSize(new Dimension(800, 300));
		getContentPane().add(splitPane);	
		JScrollPane treeView = new JScrollPane();
		splitPane.setLeftComponent(treeView);
		
		// Create the nodes.
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Groups");
		createNodes(top);
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);
		
		if (playWithLineStyle) {
			System.out.println("line style = " + lineStyle);
			tree.putClientProperty("JTree.lineStyle", lineStyle);
		}
		treeView.setViewportView(tree);
		
		detailsPanel = new JPanel();		
		detailsPanel.setLayout(new BorderLayout(0, 0));
		splitPane.setRightComponent(detailsPanel);

	}
	
	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode group = null;
		DefaultMutableTreeNode sort = null;
		
		Excgrp excgrp=new Excgrp();		
		this.groups=excgrp.getAllExcgrpRecords();
		for (int i = 0; i < groups.size(); i++) {
		    TreeMap<String, String> detail=groups.get(i);
		    group = new DefaultMutableTreeNode(new ItemInfo(detail));
		    top.add(group);
		    //if(detail.get(Excgrp.DB_EXCGRP_ID).equals("OIL")){
		    	Excsrt excsrt=new Excsrt();
		    	ArrayList<TreeMap<String, String>> sorts=excsrt.getExcsrtRecords(detail.get(Excgrp.DB_EXCGRP_ID));
		    	for (int j = 0; j < sorts.size(); j++) {
		    		TreeMap<String, String> detailSrt=sorts.get(j);
		    		sort = new DefaultMutableTreeNode(new ItemInfo(detailSrt));
		    		group.add(sort);
		    	}
		    //}
		}		
	}
	

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		int loc=splitPane.getDividerLocation();
		if (node.isLeaf()) {
			splitPane.setRightComponent(getDrinkPanel(node));
		}else{
			splitPane.setRightComponent(new JPanel());
		}
		
		splitPane.setDividerLocation(loc);
		
	}
	
	private JPanel getDrinkPanel(DefaultMutableTreeNode node){
		//Get item info
		ItemInfo itemInfo=(ItemInfo)node.getUserObject();
		TreeMap<String, String> info= itemInfo.getInfo();
		//Get parent item info
		DefaultMutableTreeNode parentNode=(DefaultMutableTreeNode) node.getParent();
		ItemInfo parentItemInfo=(ItemInfo)parentNode.getUserObject();
		TreeMap<String, String> parentInfo= parentItemInfo.getInfo();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		
		textLabel = new JLabel(parentInfo.get(Excgrp.DB_EXCGRP_DESC));
		panel.add(textLabel, BorderLayout.NORTH);
		
		JScrollPane bodyPane = new JScrollPane();
		panel.add(bodyPane, BorderLayout.CENTER);
		
		JTable table = new JTable();
		table.setShowVerticalLines(false);
		table.setRowSelectionAllowed(false);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		TariffDrinkGridModel model=new TariffDrinkGridModel(info);
		table.setModel(model);
		bodyPane.setViewportView(table);
		
		return panel;
	}
	
	class ItemInfo {
		public String itemName;
	
		public TreeMap<String, String> info;
		public ItemInfo(TreeMap<String, String> info) {
			this.info=info;
			if(info.get(Excgrp.DB_EXCGRP_ID)!=null){
				itemName=info.get(Excgrp.DB_EXCGRP_DESC);
			}else{
				itemName=info.get(Excsrt.DB_EXCSRT_DESC);
			}
			
		}

		public TreeMap<String, String> getInfo(){
			return this.info;
		}
		public String toString() {
			return itemName;
		}
	}
	
	

}
