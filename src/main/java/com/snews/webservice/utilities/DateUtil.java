package com.snews.webservice.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	public static Date DateFrom_ddMMyyyy(String ddMMyyyy) {
		try {
			return formatter.parse(ddMMyyyy);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}
}
