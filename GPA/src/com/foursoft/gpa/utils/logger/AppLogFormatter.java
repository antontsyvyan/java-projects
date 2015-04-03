package com.foursoft.gpa.utils.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class AppLogFormatter extends Formatter {

	public AppLogFormatter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String format(LogRecord rec) {
		
		StringBuffer buf = new StringBuffer(1000);
		
		buf.append(calcDate(rec.getMillis()));
		buf.append("\t");
		
		buf.append("[");
		buf.append(rec.getLevel());
		buf.append("]");
		buf.append("\t");
		buf.append("(");
		buf.append(rec.getSourceClassName());
		buf.append(")");
		buf.append("\t");
		buf.append(formatMessage(rec));
		buf.append("\r\n");
		
		return buf.toString();
				
	}
		
	private String calcDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date resultdate = new Date(millisecs);
	    return date_format.format(resultdate);
	  }

}
