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

public class TotalsDetailsReport extends ConnectionDB {
	
	public static final String PARM_FEED_SYSTEM="feed_system";
	public static final String PARM_PERIOD="period";
	public static final String PARM_ACCOUNT_ID="account_id";
	public static final String PARM_DB="db";
	
	private String feedSystem=Processors.DEFAULT_FEEDING_SYSTEM;
	private String period="201306";
	
	private MyViewer viewer;
	
	private boolean valid;
	
	public TotalsDetailsReport(){
		
	}
	
	public TotalsDetailsReport(String feedSystem,String period){
		this.feedSystem=feedSystem;
		this.period=period;
	}

	public void show() {
		
		viewer.setVisible(true);
	}
	
	
	public void generateReport(){
		try {			
			valid=true;
			File currentFile = new File(".");
			String reportPath=currentFile.getCanonicalPath()+"\\"+Resources.getSetting("report.folder")+"\\"+Resources.getSetting("totals.details");
			//JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
			
			HashMap<String, Object> parametersMap = new HashMap<String, Object>();
			
			String accountId="$XXX#";
			if(!Resources.getSetting("customer.name").trim().equals("")){
				accountId=Resources.getSetting("customer.name");
			}
						
			parametersMap.put(PARM_FEED_SYSTEM, feedSystem);
			parametersMap.put(PARM_PERIOD, period);
			parametersMap.put(PARM_ACCOUNT_ID, accountId);
			parametersMap.put(PARM_DB, Resources.getSetting("db.name"));
			
			JasperPrint jasperPrint=JasperFillManager.fillReport(reportPath,parametersMap,getConnection());				
			List<JRPrintPage> pages=jasperPrint.getPages();
			if(pages!=null && pages.size()>0){
				viewer= new MyViewer(jasperPrint,false);
				viewer.setTitle("Totalen voor" +period);
				//viewer.setVisible(true);
				//JasperViewer.viewReport(jasperPrint,false);
			}else{
				valid=false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			valid=false;
		}
		
	}
	
	public Container getNode() {
		return viewer.getContentPane();		
		
	}

	public boolean isValid() {
		return valid;
	}

	

}
