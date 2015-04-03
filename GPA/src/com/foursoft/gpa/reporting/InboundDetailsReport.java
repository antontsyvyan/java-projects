package com.foursoft.gpa.reporting;

import java.awt.Container;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.foursoft.gpa.db.ConnectionDB;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Resources;

public class InboundDetailsReport extends ConnectionDB {
	
	public static final String PARM_FEED_SYSTEM="feed_system";
	public static final String PARM_CUSTOMER_CODE="customer_code";
	public static final String PARM_PERIOD="period";
	public static final String PARM_DB="db";
	
	
	private String feedSystem=Processors.DEFAULT_FEEDING_SYSTEM;
	//private String clientCode="";
	private String period="201306";
	
	private MyViewer viewer;
	
	public InboundDetailsReport(){
		
	}
	
	public InboundDetailsReport(String feedSystem,String clientCode,String period){
		this.feedSystem=feedSystem;
		//this.clientCode=clientCode;
		this.period=period;
	}

	public void show() {
		
		viewer.setVisible(true);
	}
	
	public boolean generateReport(){
		
		System.out.println("Inbound Details Report started");
		
		try {
			
			File currentFile = new File(".");
			String reportPath=currentFile.getCanonicalPath()+"\\"+Resources.getSetting("report.folder")+"\\"+Resources.getSetting("inbound.details");
			//JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
			
			HashMap<String, Object> parametersMap = new HashMap<String, Object>();
			parametersMap.put(PARM_FEED_SYSTEM, feedSystem);
			//parametersMap.put(PARM_CUSTOMER_CODE, clientCode);
			parametersMap.put(PARM_PERIOD, period);
			parametersMap.put(PARM_DB, Resources.getSetting("db.name"));
			
			JasperPrint jasperPrint=JasperFillManager.fillReport(reportPath,parametersMap,getConnection());		
			List<JRPrintPage> pages=jasperPrint.getPages();
			if(pages!=null && pages.size()>0){
				//JasperViewer.viewReport(jasperPrint,false);
				viewer= new MyViewer(jasperPrint,false);
				viewer.setTitle("Inbound Details" +period);
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
