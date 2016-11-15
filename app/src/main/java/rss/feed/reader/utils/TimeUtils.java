package rss.feed.reader.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Andriy Ksenych on 01.11.2016.
 */

public class TimeUtils {
    private static final String RSS_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"; //example Tue, 01 Nov 2016 14:04:00 +0200
    private static final String OUTPUT_TIME_FORMAT = "HH:mm:ss dd.MM.yyyy";

    private static Date getDateFromString(String dateString){
        DateFormat format = new SimpleDateFormat(RSS_TIME_FORMAT, Locale.getDefault());
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static String getPrettyTime(String dateString){
        DateFormat format = new SimpleDateFormat(OUTPUT_TIME_FORMAT, Locale.getDefault());
        Date date = getDateFromString(dateString);

        return date == null ? dateString : format.format(date);
    }
}
