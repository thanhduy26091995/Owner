package com.hbbsolution.owner.utils;

import android.content.Context;
import android.widget.TextView;

import com.hbbsolution.owner.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by tantr on 6/7/2017.
 */

public class WorkTimeValidate {

    private static String[] workTimeValidate(String endDate)  {
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date();
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        Date date = parser.parseDateTime(endDate).toDate();
        long millisecond = date.getTime();
        long timer = (currentTime - millisecond);
        long timeHistory = (timer / 60000);
        String[] currentTimeHistory = {"0","0", "0", "0","0","0"};
        if(timeHistory < 1) {
            currentTimeHistory[0] = String.format("%d",
                    TimeUnit.MILLISECONDS.toMillis(timer));
        }
        else if (1 < timeHistory && timeHistory < 60) {
            currentTimeHistory[1] = String.format("%d",
                    TimeUnit.MILLISECONDS.toMinutes(timer));

        } else if (60 < timeHistory && timeHistory < 1440) {
            currentTimeHistory[2] = String.format("%d",
                    TimeUnit.MILLISECONDS.toHours(timer));

        } else if (timeHistory > 1440) {
            currentTimeHistory[3] = String.format("%d",
                    TimeUnit.MILLISECONDS.toDays(timer));

        } else if (timeHistory > 1440*30)
        {
            if(currentDate.getYear()-date.getYear()>0)
            {
                currentTimeHistory[5]=String.valueOf(currentDate.getYear()-date.getYear());
            }
            else
            {
                currentTimeHistory[4]=String.valueOf(currentDate.getMonth()-date.getMonth());
            }
        }
        return currentTimeHistory;
    }

    public static String getDatePostHistory(String _CreateDatePostHistory) {
        Date date = new DateTime(_CreateDatePostHistory).toDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String mDateTimePostHistory = df.format(date);
        return mDateTimePostHistory;
    }

    public static String getTimeWork(String _TimeWork) {
        String mTimeWork = null;
        Date date = new DateTime(_TimeWork).toDate();
        SimpleDateFormat time = new SimpleDateFormat("hh:mm a", Locale.US);
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
        // OVERRIDE SOME symbols WHILE RETAINING OTHERS
        symbols.setAmPmStrings(new String[] { "am", "pm" });
        time.setDateFormatSymbols(symbols);
        try{
            mTimeWork = time.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mTimeWork;
    }

    public static boolean compareDays(String timeEndWork) {
        long time = System.currentTimeMillis();
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        Date date = parser.parseDateTime(timeEndWork).toDate();
        long millisecond = date.getTime();
        long timer = (millisecond - time);
        if(timer < 0) {
            return false;
        }
        return true;
    }

    public static void setWorkTimeRegister(Context context, TextView txtTimePostHistory, String _timePostHistory) {
        String[] mWorkTimeHistory = workTimeValidate(_timePostHistory);
        if (!mWorkTimeHistory[3].equals("0")) {
            txtTimePostHistory.setText(mWorkTimeHistory[3] + " "+ context.getResources().getString(R.string.before,context.getResources().getQuantityString(R.plurals.day,Integer.parseInt(mWorkTimeHistory[2]))));
        } else if (!mWorkTimeHistory[2].equals("0")) {
            txtTimePostHistory.setText(mWorkTimeHistory[2] + " "+ context.getResources().getString(R.string.before,context.getResources().getQuantityString(R.plurals.hour,Integer.parseInt(mWorkTimeHistory[1]))));
        } else if (!mWorkTimeHistory[1].equals("0")) {
            txtTimePostHistory.setText(mWorkTimeHistory[1] + " "+ context.getResources().getString(R.string.before,context.getResources().getQuantityString(R.plurals.minute,Integer.parseInt(mWorkTimeHistory[0]))));
        } else if (!mWorkTimeHistory[0].equals("0")) {
            txtTimePostHistory.setText("Vừa xong");
        }
    }
}
