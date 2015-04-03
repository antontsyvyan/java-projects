package com.foursoft.gpa.reporting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import com.foursoft.gpa.reporting.beans.ErrorOverviewBean;
import com.foursoft.gpa.reporting.datasources.ErrorOverviewBeansDataSource;
import com.foursoft.gpa.utils.Resources;

public class ErrorOverviewReport {

private ArrayList<ErrorOverviewBean> collection;


	public	ErrorOverviewReport(){
		
	}
	
	public void setCollection(ArrayList<ErrorOverviewBean> collection) {
		this.collection = collection;
	}

	public boolean show() {
		
		System.out.println("Error overview Report started");	
		try{
			//Prepare data source
			ErrorOverviewBeansDataSource ds=new ErrorOverviewBeansDataSource();								
			if (collection != null && collection.size() > 0) {
				ds.setCollection(collection);
				
				File currentFile = new File(".");
				String reportPath=currentFile.getCanonicalPath()+"\\"+Resources.getSetting("report.folder")+"\\"+Resources.getSetting("error.details");
				//JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
				//Set report parameters
				HashMap<String,Object> parametersMap = new HashMap<String,Object>();
				//Set report locale
				parametersMap.put(JRParameter.REPORT_LOCALE, new Locale("EN"));
				//Execute report
				JasperPrint jasperPrint=JasperFillManager.fillReport(reportPath,parametersMap,new JRBeanCollectionDataSource(ds.getBeanCollection()));
				JasperViewer.viewReport(jasperPrint,false);
			}else{
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
