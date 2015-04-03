package com.foursoft.gpa;

import java.util.Calendar;

import com.foursoft.gpa.jobs.ExchangeRateProcessor;

public class ExchangeRateService {

	public static void main(String[] args) {
		// determine current period
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

		String period = year + String.format("%02d", month);

		ExchangeRateProcessor proc = new ExchangeRateProcessor();

		proc.setPeriod(period);
		proc.Process();

	}

}
