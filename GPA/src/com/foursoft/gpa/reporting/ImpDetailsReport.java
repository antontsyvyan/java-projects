package com.foursoft.gpa.reporting;

import java.awt.Container;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.foursoft.gpa.db.ConnectionDB;
import com.foursoft.gpa.utils.Resources;

public class ImpDetailsReport extends ConnectionDB {
	
	public static final String PARM_A3="a3";
	public static final String PARM_DB="db";
	
	private String a3;
	
	private MyViewer viewer;
	
	public ImpDetailsReport(){
		
	}
	
	public ImpDetailsReport(String a3){
		this.a3=a3;
	}
	
	
	public void show() {
		
		viewer.setVisible(true);
	}

	public boolean generateReport(){
		
		System.out.println("Details Report started");
		
		try {
			
			File currentFile = new File(".");
			String reportPath=currentFile.getCanonicalPath()+"\\"+Resources.getSetting("report.folder")+"\\"+Resources.getSetting("imp.details");
			//JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
			
			HashMap<String, Object> parametersMap = new HashMap<String, Object>();
			parametersMap.put(PARM_A3, a3);
			parametersMap.put(PARM_DB, Resources.getSetting("db.name"));
			
			JasperPrint jasperPrint=JasperFillManager.fillReport(reportPath,parametersMap,getConnection());		
			List<JRPrintPage> pages=jasperPrint.getPages();
			if(pages!=null && pages.size()>0){
				//JasperViewer.viewReport(jasperPrint,false);
				viewer= new MyViewer(jasperPrint,false);
				viewer.setTitle("Details " +a3);
			}else{
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public Container getNode() {
		return viewer.getContentPane();		
		
	}

}
