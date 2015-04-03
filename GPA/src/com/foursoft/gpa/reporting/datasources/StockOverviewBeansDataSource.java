package com.foursoft.gpa.reporting.datasources;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.foursoft.gpa.reporting.beans.StockOverviewBean;
import com.foursoft.gpa.utils.Processors;


public class StockOverviewBeansDataSource extends JRAbstractBeanDataSourceProvider {


	private ArrayList<StockOverviewBean> collection =new ArrayList<StockOverviewBean>();
	
	public StockOverviewBeansDataSource() {
		super(StockOverviewBean.class);
		// TODO Auto-generated constructor stub
	}

	public JRDataSource create(JasperReport arg0) throws JRException {
		//Some test data used for test purposes when developing in iReport designer
		ArrayList<StockOverviewBean> list =   new ArrayList<StockOverviewBean>(); 
		StockOverviewBean sob= new StockOverviewBean();
		sob.setArticle("ARTICLE 1");
		sob.setClient("ATN");
		sob.setCurrency("EUR");
		sob.setDateK13("2013-07-01");
		sob.setDescription("Test goods");
		sob.setFeedingSystem(Processors.DEFAULT_FEEDING_SYSTEM);
		sob.setJob("234567333/1");
		sob.setPeriod("201307");
		sob.setQuantity(18);
		sob.setTariff("8471499000000000000000");
		sob.setUnit("PCS");
		sob.setValue(150.0);
		sob.setType("BI");
		sob.setDirection("I");
		list.add(sob);
		
		sob= new StockOverviewBean();
		sob.setArticle("ARTICLE 1");
		sob.setClient("ATN");
		sob.setCurrency("EUR");
		sob.setDateK13("2013-07-01");
		sob.setDescription("Test goods");
		sob.setFeedingSystem(Processors.DEFAULT_FEEDING_SYSTEM);
		sob.setJob("234567359/1");
		sob.setPeriod("201307");
		sob.setQuantity(61);
		sob.setTariff("8471499000000000000000");
		sob.setUnit("PCS");
		sob.setValue(2659.0);
		sob.setType("BI");
		sob.setDirection("I");
		list.add(sob);
		
		sob= new StockOverviewBean();
		sob.setArticle("ARTICLE 1");
		sob.setClient("ATN");
		sob.setCurrency("EUR");
		sob.setDateK13("2013-07-01");
		sob.setDescription("Test goods");
		sob.setFeedingSystem(Processors.DEFAULT_FEEDING_SYSTEM);
		sob.setJob("863451113/1");
		sob.setPeriod("201307");
		sob.setQuantity(-32);
		sob.setTariff("8471499000000000000000");
		sob.setUnit("PCS");
		sob.setValue(-1400.35);
		sob.setType("AF");
		sob.setDirection("O");
		list.add(sob);
		
		return   new JRBeanCollectionDataSource (list); 
	}

	@Override
	public void dispose(JRDataSource arg0) throws JRException {
		// TODO Auto-generated method stub
		
	}
	
	public Collection<StockOverviewBean> getBeanCollection()
	{		  
		return this.collection;
	 }

	public void setCollection(ArrayList<StockOverviewBean> collection) {
		this.collection = collection;
	}



}
