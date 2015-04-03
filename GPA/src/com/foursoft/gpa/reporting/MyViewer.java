package com.foursoft.gpa.reporting;

import java.util.Locale;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor;

public class MyViewer extends JasperViewer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyViewer(JasperPrint jasperPrint,boolean isExitOnClose) throws JRException {
		super(jasperPrint,isExitOnClose);
		
		Locale locale = viewer.getLocale();
	    ResourceBundle resourceBundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", locale);
	 
	    JRPdfSaveContributor pdf = new JRPdfSaveContributor(locale, resourceBundle);
	    JRSingleSheetXlsSaveContributor xls = new JRSingleSheetXlsSaveContributor(locale, resourceBundle);	 
	    viewer.setSaveContributors(new JRSaveContributor[]{pdf, xls});
	    
	    
	}

}
