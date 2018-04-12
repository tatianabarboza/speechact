package decisaocomatosdefala.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	public static Date converterStringParaDate(String data) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
        Calendar cal = Calendar.getInstance(tz);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setCalendar(cal);
        cal.setTime(sdf.parse(data));
        Date date = cal.getTime();
        return date;
    }
	
}
