package com.foursoft.gpa.reporting.beans;

public class ErrorOverviewBean {

	private String feedingSystem="";
	private String jobNumber="";
	private int lineNumber=0;
	private String direction="";
	private String blockNumber="";
	private String errorDescription="";
			
	public ErrorOverviewBean() {
		// TODO Auto-generated constructor stub
	}

	public String getFeedingSystem() {
		return feedingSystem;
	}

	public void setFeedingSystem(String feedingSystem) {
		this.feedingSystem = feedingSystem;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
