package com.foursoft.gpa.reporting.datasources;

import java.util.ArrayList;
import java.util.Collection;

import com.foursoft.gpa.reporting.beans.ErrorOverviewBean;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ErrorOverviewBeansDataSource extends JRAbstractBeanDataSourceProvider {

	private ArrayList<ErrorOverviewBean> collection =new ArrayList<ErrorOverviewBean>();
	
	public ErrorOverviewBeansDataSource() {
		super(ErrorOverviewBean.class);
	}

	@Override
	public JRDataSource create(JasperReport arg0) throws JRException {
		ArrayList<ErrorOverviewBean> list =   new ArrayList<ErrorOverviewBean>(); 
		
		return   new JRBeanCollectionDataSource (list); 
	}

	@Override
	public void dispose(JRDataSource arg0) throws JRException {
	
	}
	
	public Collection<ErrorOverviewBean> getBeanCollection()
	{		  
		return this.collection;
	 }

	public void setCollection(ArrayList<ErrorOverviewBean> collection) {
		this.collection = collection;
	}

}
