package com.foursoft.gpa.reporting;

import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.foursoft.gpa.reporting.beans.StockOverviewBean;
import com.foursoft.gpa.reporting.datasources.StockOverviewBeansDataSource;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Resources;
import com.foursoft.gpa.utils.StockUtils;

public class StockOverviewReport {

private String feedSystem=Processors.DEFAULT_FEEDING_SYSTEM;
private String clientCode="";
private String period="201306";
private ArrayList<StockOverviewBean> collection;

private MyViewer viewer;

	public	StockOverviewReport(){
		
	}
	
	public	StockOverviewReport(String feedSystem,String clientCode,String period){
		this.feedSystem=feedSystem;
		this.clientCode=clientCode;
		this.period=period;
	}

	public void setCollection(ArrayList<StockOverviewBean> collection) {
		this.collection = collection;
	}

	
	public void show() {
		
		viewer.setVisible(true);
	}
	
	public boolean generateReport(){
		
		System.out.println("Stock overview Report started");
		StockUtils su= new StockUtils();
		if(collection==null || collection.size()==0){		
		
			su.setFeedSystem(feedSystem);
			su.setClientCode(clientCode);
			su.setPeriod(period);
			collection=su.getCollection();
		}
		
		try{
			//Prepare data source
			StockOverviewBeansDataSource ds=new StockOverviewBeansDataSource();								
			if (collection != null && collection.size() > 0) {
				ds.setCollection(collection);
				
				File currentFile = new File(".");
				String reportPath=currentFile.getCanonicalPath()+"\\"+Resources.getSetting("report.folder")+"\\"+Resources.getSetting("stock.details");
				//JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
				//Set report parameters
				HashMap<String,Object> parametersMap = new HashMap<String,Object>();
				//Set report locale
				parametersMap.put(JRParameter.REPORT_LOCALE, new Locale("EN"));
				//Execute report
				JasperPrint jasperPrint=JasperFillManager.fillReport(reportPath,parametersMap,new JRBeanCollectionDataSource(ds.getBeanCollection()));
				//JasperViewer.viewReport(jasperPrint,false);
				List<JRPrintPage> pages=jasperPrint.getPages();
				if(pages!=null && pages.size()>0){
					viewer= new MyViewer(jasperPrint,false);
					viewer.setTitle("Stock overview" +period);
				}else{
					return false;
				}
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
