package com.foursoft.gpa;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.foursoft.gpa.db.Femuti;
import com.foursoft.gpa.db.Femutu;
import com.foursoft.gpa.db.Gpareq;
import com.foursoft.gpa.db.Prmchk;
import com.foursoft.gpa.db.Ranges;
import com.foursoft.gpa.db.Stock;
import com.foursoft.gpa.reporting.InboundDetailsReport;
import com.foursoft.gpa.reporting.NegativeStockReport;
import com.foursoft.gpa.reporting.StockOverviewReport;
import com.foursoft.gpa.reporting.beans.StockOverviewBean;
import com.foursoft.gpa.utils.DateUtils;
import com.foursoft.gpa.utils.ProcessSemaphore;
import com.foursoft.gpa.utils.Processors;
import com.foursoft.gpa.utils.Protocol;
import com.foursoft.gpa.utils.Resources;
import com.foursoft.gpa.utils.StockUtils;

public class GpaCore {

	public Logger log = Logger.getLogger(this.getClass().toString());
	
	public static final String ERROR_PREFIX="ERRORFILE:";
	private String feedSystem = Processors.DEFAULT_FEEDING_SYSTEM;
	private String clientCode = "";
	private String period = "201306";

	private Femuti femuti = new Femuti();
	private Femutu femutu = new Femutu();
	private Stock stock = new Stock();
	private Prmchk prmchk = new Prmchk();
	private Gpareq gpareq= new Gpareq();
	private Ranges ranges= new Ranges();
	private StockUtils su = new StockUtils();
	private DateUtils du = new DateUtils();
	private ArrayList<TreeMap<String, String>> stockDetails = new ArrayList<TreeMap<String, String>>();
	
	private ArrayList<StockOverviewBean> collection;
		
	private Disk disk;
	
	private String errorMessage="";
	
	private String runMode=Processors.PRELIMINARY;
	
	private int method;
	
	private String requestId;
		
	public GpaCore(String feedSystem,String clientCode,String period) {
		
		this.feedSystem=feedSystem;
		this.clientCode=clientCode;
		this.period=period;
	}
		
	public boolean testGpa(){
		
		this.runMode=Processors.PRELIMINARY;
		return runGpa();
	}
	
	public boolean finalGpa(){
		this.runMode=Processors.FINAL;
		return runGpa();
	}
	
	
	public boolean testDomproc(){
		this.runMode=Processors.PRELIMINARY;
		return runDomproc();
	}
	
	public boolean finalDomproc(){
		this.runMode=Processors.FINAL;
		return runDomproc();
	}
	
	
	public boolean resetGpa(){
		
		boolean retCode=true;	
		if(period.trim().equals("") || feedSystem.equals("")){
			retCode=false;
		}else{
			femuti.updateFemutiStatusForPeriod(feedSystem, clientCode, period, Processors.FINAL, Processors.PRELIMINARY);
			femutu.updateFemutuStatusForPeriod(feedSystem, clientCode, period, Processors.FINAL, Processors.PRELIMINARY);
			//Delete stock records
			stock.deleteStockForPeriod(feedSystem, clientCode, period);
			prmchk.deletePrmchk(feedSystem,period);
			
		}
		
		return retCode;
	}
	
	public boolean resetDomproc(){
		
		disk =new Disk(this.method);
		
		gpareq.updateStatusGpareq(requestId, Processors.REQUEST_STATUS_RUNNING,"","");
		femutu.updateFemutuStatusForPeriod(Processors.DOMPROC_FEEDING_SYSTEM, clientCode, period, Processors.FINAL, Processors.PRELIMINARY);
		prmchk.deletePrmchk(Processors.DOMPROC_FEEDING_SYSTEM, period);
		
		return true;
	}
		
	public boolean resetGpaNew(String type,boolean delete){
		
		disk =new Disk(this.method);
		gpareq.updateStatusGpareq(requestId, Processors.REQUEST_STATUS_RUNNING,"","");
		
		if(delete){
			femutu.deletePeriod(feedSystem, period);
		}else{
			femutu.updateFemutuStatusForPeriod(feedSystem, clientCode, period, Processors.FINAL, Processors.PRELIMINARY);
		}		
		
		if(type.equals(Disk.ENTREPOT)){
			
			if(delete){
				femuti.deletePeriod(feedSystem, period);
			}else{
				femuti.updateFemutiStatusForPeriod(feedSystem, clientCode, period, Processors.FINAL, Processors.PRELIMINARY);
				//Delete stock records
				stock.deleteStockForPeriod(feedSystem, clientCode, period);
			}
		}
		
		prmchk.deletePrmchk(feedSystem,period);
		
		return true;
	}

	private boolean runGpa(){
		boolean ret=true;
		
		try{
			ProcessSemaphore.femutuSemaphore.acquire();
			ProcessSemaphore.femutiSemaphore.acquire();
			log.info(" ==> ENT acqured femutu and femuti semaphore<==");
			
			gpareq.updateStatusGpareq(requestId, Processors.REQUEST_STATUS_RUNNING,"","");
			
			disk =new Disk(this.method);
			disk.setCustomsFileName(Disk.ENTREPOT);
			disk.loadPersistData();
			su.setFeedSystem(feedSystem);
			su.setClientCode(clientCode);
			su.setPeriod(period);
			collection=su.getSortedCollection();
			
			if(isValidGpa()){							
				if(runMode.equals(Processors.PRELIMINARY)){
					//ret= showReports();
				}else{
					if(runMode.equals(Processors.FINAL)){
						ret= finalizeGpa();
					}
				}			
			}else{
				ret=false;
			}
		
		}catch(Exception e){
			
		}finally{
			ProcessSemaphore.femutuSemaphore.release();
			ProcessSemaphore.femutiSemaphore.release();
			log.info(" ==> ENT released femutu and femuti semaphore<==");
		}
		return ret;
	}
	
	private boolean runDomproc(){
		boolean ret=false;
		
		try{
			ProcessSemaphore.femutuSemaphore.acquire();
			log.info(" ==> IMP acqured femutu semaphore<==");
	
			disk =new Disk(this.method);
			disk.setCustomsFileName(Disk.INVOER);
			disk.loadPersistData();
			//Read Femutu
			femutu=new Femutu();
			Protocol.initProgress();
			
			ArrayList<TreeMap<String, String>> details=femutu.readDomprocRecordsForPeriod(feedSystem, clientCode, period, Processors.PRELIMINARY);
			if (details != null && details.size() > 0) {
				//Update requests table
				
				Protocol.globalTotalRecords=details.size();
				Protocol.globalStatus=Protocol.STATUS_COLLECTING;
				gpareq.updateStatusGpareq(requestId, Processors.REQUEST_STATUS_RUNNING,"","");
				for (int i = 0; i < details.size(); i++) {
					Disk.sleep(100);
					disk.addDetails(femutu.mapToDisk(details.get(i)));
					Protocol.globalTotalProcessedRecords=i;
				}
				
				if(disk.isValid(Disk.INVOER)){
					if(disk.create()){
						createTotals();
						if(runMode.equals(Processors.FINAL)){
							ret= finalizeDomproc(details);
						}else{
							ret=true;
						}				
					}
				}else{				
					ret=false;
				}
							
				if(disk.getErrorsList()!=null && disk.getErrorsList().size()>0){
					errorMessage=ERROR_PREFIX+disk.createErrorLog();
				}
				
			}else{
				errorMessage="No records found for :"+feedSystem+"/"+period;
				ret= false;
			}
			
			Protocol.initProgress();
		}catch(Exception e){
			
		}finally{
			ProcessSemaphore.femutuSemaphore.release();
			log.info(" ==> IMP acqured released semaphore<==");
		}
		
		return ret;
	}
	
	private boolean finalizeDomproc(ArrayList<TreeMap<String, String>> details){
		boolean ret=true;
		TreeMap<String, String> det=new TreeMap<String, String>();
		Protocol.globalStatus=Protocol.STATUS_FINALIZING;
		for (int i = 0; i < details.size(); i++) {
			det=details.get(i);
			if(!femutu.updateFemutuStatus(det.get(Femutu.DB_FEMUTU_UNIQUE_KEY),Processors.FINAL)){
				ret=false;
			}
			Protocol.globalTotalProcessedRecords=i;
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private boolean isValidGpa(){
		
		boolean valid= true;
		StockOverviewBean sob=new StockOverviewBean();
		StockOverviewBean prevSob=new StockOverviewBean();
		TreeMap<String, String> tmpStock=new TreeMap<String, String>();
		TreeMap<String, String> tmpStockPrev=new TreeMap<String, String>();	
		TreeMap<String, String> detail=new TreeMap<String, String>();
		TreeMap<String, String> bvRecord=new TreeMap<String, String>();	
		
		int qtyTeller=0;
		double amtTeller=0;
		double wgtTeller=0;
		boolean firstTime=true;
		//Process all stock records for the entire period.
		if (collection != null && collection.size() > 0) {
			Protocol.globalStatus=Protocol.STATUS_VALIDATING;
			Protocol.globalTotalRecords=collection.size();	
			
			for (int i = 0; i < collection.size(); i++) {
				
				boolean addRecord=false;
				
				sob=collection.get(i);			
				if(sob.getType().equals("AA")){
					//BV (begin of stock) record 
					tmpStock=prepareBeginStock(sob);
					//keep BV record stored
					bvRecord=(TreeMap<String, String>) tmpStock.clone();
					
					if(bvRecord!=null && bvRecord.size()>0){
						//Add BV record .
						stockDetails.add(bvRecord);
						disk.addDetails(mapToDisk(bvRecord));
						bvRecord=new TreeMap<String, String>();	
					}
				}else{
					StringTokenizer st = new StringTokenizer(sob.getJob(),"/");
					String jobNumber=(String) st.nextElement();
					int jobLineNumber=Integer.parseInt((String) st.nextElement());				
					
					if(sob.getDirection().equals(Processors.INBOUND)){
						detail=femuti.readFemutiRecord(sob.getFeedingSystem(), Processors.INBOUND, jobNumber, jobLineNumber);
						tmpStock=femuti.mapToStock(detail);
						addRecord=true;
						//disk.addDetails(femuti.mapToDisk(detail));
					}else{
						detail=femutu.readFemutuRecord(sob.getFeedingSystem(), Processors.OUTBOUND, jobNumber, jobLineNumber);
						tmpStock=femutu.mapToStock(detail);
						addRecord=true;
						//disk.addDetails(femutu.mapToDisk(detail));
					}
				}
				
				//Eindvoorraad
				if(!firstTime){
					if(!prevSob.getClient().equals(sob.getClient()) ||	
							!prevSob.getArticle().equals(sob.getArticle())|| 
							!prevSob.getCurrency().equals(sob.getCurrency())){
						
						//Add EV record
						tmpStockPrev=prepareEndStock(tmpStockPrev,qtyTeller,amtTeller,wgtTeller);					
						stockDetails.add(tmpStockPrev);
						disk.addDetails(mapToDisk(tmpStockPrev));
												
						qtyTeller=0;
						amtTeller=0;
						wgtTeller=0;
					}
					
				}else{
					firstTime=false;
				}
				
				if(addRecord){
					if(sob.getDirection().equals(Processors.INBOUND)){
						disk.addDetails(femuti.mapToDisk(detail));
					}else{
						disk.addDetails(femutu.mapToDisk(detail));
					}
				}
				
				prevSob=sob;
				tmpStockPrev=(TreeMap<String, String>) tmpStock.clone();
				
				qtyTeller+=sob.getQuantity();
				amtTeller+=sob.getValue();
				wgtTeller+=sob.getWeight();
				
				stockDetails.add(tmpStock);
				
				Protocol.globalTotalProcessedRecords=i;						
			}
			
			//Add EV last record
			tmpStockPrev=prepareEndStock(tmpStockPrev,qtyTeller,amtTeller,wgtTeller);					
			stockDetails.add(tmpStockPrev);
			disk.addDetails(mapToDisk(tmpStockPrev));
		}else{
			//No records for GPA found
			errorMessage="No records found for :"+feedSystem+"/"+clientCode+"/"+period;
			return false;
		}
		
		if(disk.isValid(Disk.ENTREPOT)){
			//write DISK file to defined folder
			if(disk.create()){
				createTotals();							
			}else{
				valid=false;
			}
		}else{
			valid=false;
			//showErrorReports(disk.getErrorOverview());
						
		}
		
		if(disk.getErrorsList()!=null && disk.getErrorsList().size()>0){
			errorMessage=ERROR_PREFIX+disk.createErrorLog();
		}
		
		return valid;
	}
	
	
	private void createTotals(){
		
		String diskCreated=Processors.DISK_TEST;
		//Write PRMCHK
		TreeMap<String, String> prmchkDetail=prmchk.initRecord();
		prmchkDetail.put(Prmchk.DB_FEED_SYS_PRMCHK, feedSystem);
		prmchkDetail.put(Prmchk.DB_PRMCHK_PERIOD, period);
		if(runMode.equals(Processors.FINAL)){
			diskCreated=Processors.DISK_FINAL;
			prmchkDetail.put(Prmchk.DB_PRMCHK_CRDATE, du.getCurrentDate());
			
		}
		disk.calculateTotals();
		prmchkDetail.put(Prmchk.DB_PRMCHK_DISK_CREATED, diskCreated);		
		prmchkDetail.put(Prmchk.DB_PRMCHK_IMP_DUTIES, ""+disk.getTotalImportDuties());
		prmchkDetail.put(Prmchk.DB_PRMCHK_AMT_RELIEF, ""+disk.getTotalAmountRelief());
		prmchkDetail.put(Prmchk.DB_PRMCHK_SPEC_CUST_DUTIES, ""+disk.getTotalSpecificCustomsDuties());
		prmchkDetail.put(Prmchk.DB_PRMCHK_IMP_DUTIES_E35, ""+disk.getTotalCustomsDuties());
		prmchkDetail.put(Prmchk.DB_PRMCHK_PRELIM_AMT_CHRGS, ""+disk.getTotalChargesPreliminary());
		prmchkDetail.put(Prmchk.DB_PRMCHK_FINAL_AMT_CHRGS, ""+disk.getTotalChargesFinal());
		prmchkDetail.put(Prmchk.DB_PRMCHK_AGR_LEVIES, ""+disk.getTotalAgricultureLevies());
		prmchkDetail.put(Prmchk.DB_PRMCHK_VAT_AMT, ""+disk.getTotalAmountVat());
		prmchkDetail.put(Prmchk.DB_PRMCHK_CUST_VAL, ""+disk.getTotalCustomsValue());
		prmchkDetail.put(Prmchk.DB_PRMCHK_TRANS_CODE_NO, ""+disk.getTotalTransactionCodes());
		prmchkDetail.put(Prmchk.DB_PRMCHK_REC_NO, ""+disk.getNumberOfDiskRecords());
		prmchkDetail.put(Prmchk.DB_PRMCHK_DECL_REC_NO, ""+disk.getNumberOfDeclaration());				
		
		prmchk.insertUpdatePrmchk(prmchkDetail);
	}
	/*
	private boolean showErrorReports(ArrayList<ErrorOverviewBean> errorCollection){
		
		ErrorOverviewReport eor=new ErrorOverviewReport();
		eor.setCollection(errorCollection);
		
		if(eor.show()){
			return true;
		}
		return false;
	}
	*/
	
	public boolean showReports(){
			
		InboundDetailsReport idr= new InboundDetailsReport(feedSystem,clientCode,period);
		boolean idrOk=idr.generateReport();
		if(idrOk){
			idr.show();
		}
		
		StockOverviewReport sor = new StockOverviewReport(feedSystem,clientCode,period);
		sor.setCollection(collection);
		boolean sorOk=sor.generateReport();
		if(sorOk){
			sor.show();
		}
		NegativeStockReport nsr= new NegativeStockReport(feedSystem,clientCode,period);
		nsr.setCollection(collection);
		
		boolean nsrOk=nsr.generateReport();
		if(nsrOk){
			nsr.show();
		}
			
		if(!idrOk && !sorOk && !nsrOk){
			errorMessage="There is nothing to show for this period";
			return false;
		}
		return true;
	}
			
	private boolean finalizeGpa(){
		//TODO Write stock records
		for(int i=0; i<stockDetails.size();i++){
			
			TreeMap<String, String> det=stockDetails.get(i);
			Stock.insertStock(det);
			
			if(!det.get(Stock.DB_STOCK_JOB_NO_IN).trim().equals("")){
				femuti.updateFemutiStatus(det.get(Stock.DB_FEED_SYS_STOCK),Processors.INBOUND,det.get(Stock.DB_STOCK_JOB_NO_IN),Integer.parseInt(det.get(Stock.DB_STOCK_LN_NO_IN)),Processors.FINAL);
			}else{
				if(!det.get(Stock.DB_STOCK_JOB_NO_OUT).trim().equals("")){
					femutu.updateFemutuStatus(det.get(Stock.DB_FEED_SYS_STOCK),Processors.OUTBOUND,det.get(Stock.DB_STOCK_JOB_NO_OUT),Integer.parseInt(det.get(Stock.DB_STOCK_LN_NO_OUT)),Processors.FINAL);
				}
			}
			
		}
		//update check parameters to final.
		prmchk.updatePrmchkStatus(feedSystem,period, Processors.DISK_FINAL);
		
		return true;
	}
	
	private TreeMap<String, String> prepareEndStock(TreeMap<String, String> detail,int qty,double amt,double wgt){
		
		detail.put(Stock.DB_STOCK_TRANS_TYPE,Processors.EV);
		String a3Field=ranges.getUniqueIdentifier(Ranges.TYPE_A3, Resources.getSetting("customer.name"));
		detail.put(Stock.DB_STOCK_UNIQ_LN_NO,a3Field);		
		detail.put(Stock.DB_STOCK_JOB_NO_IN,"");
		detail.put(Stock.DB_STOCK_JOB_NO_OUT,"");
		detail.put(Stock.DB_STOCK_LN_NO_IN,"");
		detail.put(Stock.DB_STOCK_LN_NO_OUT,"");
		detail.put(Stock.DB_STOCK_PCS,""+qty);
		detail.put(Stock.DB_STOCK_AMT,""+amt);
		detail.put(Stock.DB_STOCK_NT_WGT,""+wgt);
		
		return detail;
	}
		
	private TreeMap<String, String> prepareBeginStock(StockOverviewBean sob){
		
		TreeMap<String, String> detail=new TreeMap<String, String>();
		
		detail.put(Stock.DB_STOCK_TRANS_TYPE,Processors.BV);
		String a3Field=ranges.getUniqueIdentifier(Ranges.TYPE_A3, Resources.getSetting("customer.name"));
		detail.put(Stock.DB_STOCK_UNIQ_LN_NO,a3Field);	
		detail.put(Stock.DB_FEED_SYS_STOCK,sob.getFeedingSystem());
		detail.put(Stock.DB_STOCK_JOB_NO_IN,"");
		detail.put(Stock.DB_STOCK_JOB_NO_OUT,"");
		detail.put(Stock.DB_STOCK_LN_NO_IN,"0");
		detail.put(Stock.DB_STOCK_LN_NO_OUT,"0");
		detail.put(Stock.DB_NAW_CODE_CM,sob.getClient());
		detail.put(Stock.DB_STOCK_ART_NO,sob.getArticle());
		detail.put(Stock.DB_STOCK_TARIFF_CODE,sob.getTariff());
		detail.put(Stock.DB_STOCK_PERIOD,sob.getPeriod());
		detail.put(Stock.DB_CURR_CODE_STOCK,sob.getCurrency());
		detail.put(Stock.DB_STOCK_ADM_UNIT,sob.getUnit());
		detail.put(Stock.DB_STOCK_PCS,""+sob.getQuantity());
		detail.put(Stock.DB_STOCK_AMT,""+sob.getValue());
		detail.put(Stock.DB_STOCK_NT_WGT,""+sob.getWeight());
		
		detail.put(Stock.DB_STOCK_EX_RATE,"1");
		
		return detail;
	}
	
	private TreeMap<String, String> mapToDisk(TreeMap<String, String> detail) {

		TreeMap<String, String> diskDetail = disk.getEmptyDetail();

		String transType = "AA";
		String transDate = detail.get(Stock.DB_STOCK_PERIOD) + "01";
		if (!detail.get(Stock.DB_STOCK_TRANS_TYPE).equals("AA")) {
			transType = detail.get(Stock.DB_STOCK_TRANS_TYPE);
			transDate = du.getLastDayOfMonth(transDate);
		}
		
		String numberOfPices=detail.get(Stock.DB_STOCK_PCS);
		String amount=detail.get(Stock.DB_STOCK_AMT);
		if(transType.equals("EV")){
			//Eindvoorraad wordt negatief in K.9 en K.10 vermeld.
			numberOfPices=""+(Integer.parseInt(numberOfPices)*-1);
			amount=""+(Double.parseDouble(amount)* new Double(-1.0));
		}
		diskDetail.put(Disk.BLOCK_A0, "A");
		diskDetail.put(Disk.BLOCK_A1, period);
		diskDetail.put(Disk.BLOCK_A2, "101000");
		diskDetail.put(Disk.BLOCK_A3,detail.get(Stock.DB_STOCK_UNIQ_LN_NO));
		diskDetail.put(Disk.BLOCK_K0, "K");
		diskDetail.put(Disk.BLOCK_K1, detail.get(Stock.DB_NAW_CODE_CM));
		diskDetail.put(Disk.BLOCK_K3, detail.get(Stock.DB_STOCK_ART_NO));
		diskDetail.put(Disk.BLOCK_K4, detail.get(Stock.DB_STOCK_TARIFF_CODE));
		diskDetail.put(Disk.BLOCK_K5, detail.get(Stock.DB_STOCK_IMP_DUT_PERC));
		diskDetail.put(Disk.BLOCK_K6, detail.get(Stock.DB_STOCK_ADM_UNIT));
		diskDetail.put(Disk.BLOCK_K7, transType);		
		diskDetail.put(Disk.BLOCK_K9, numberOfPices);
		diskDetail.put(Disk.BLOCK_K10, amount);
		diskDetail.put(Disk.BLOCK_K11, detail.get(Stock.DB_CURR_CODE_STOCK));
		diskDetail.put(Disk.BLOCK_K12, detail.get(Stock.DB_STOCK_EX_RATE));
		diskDetail.put(Disk.BLOCK_K13, transDate);
		diskDetail.put(Disk.BLOCK_K14, detail.get(Stock.DB_STOCK_NT_WGT));

		return diskDetail;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public String getDiskFileName() {
		return disk.getDiskFileName();
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	
	
}
