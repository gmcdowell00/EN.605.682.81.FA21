package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    
	public String GetDateTimeNow() {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	public Date ConvertStringToDate(String dateString) {
		
		try {
			if (dateString.contains("-")) {
				String [] date = dateString.split("-");
				dateString = date[1] + "/" + date[2] + "/" + date[0];
			} 
			
			return new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}
}
