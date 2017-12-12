package com.example.carrie.carrie_test1;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by mindy on 2017/9/4.
 */

public class BsBpMeasureObject implements Serializable {
    public int id;
    public String member_id;
    public String bs_first;
    public String bs_second;
    public String bs_third;
    public String bp_first;
    public String bp_second;
    public String bp_third;

    public BsBpMeasureObject(int id, String member_id, String bs_first, String bs_second, String bs_third, String bp_first, String bp_second, String bp_third) throws ParseException {
        this.id = id;
        this.member_id = member_id;
        this.bs_first = getCurrentTimeStamp(bs_first);
        this.bs_second = getCurrentTimeStamp(bs_second);
        this.bs_third = getCurrentTimeStamp(bs_third);
        this.bp_first = getCurrentTimeStamp(bp_first);
        this.bp_second = getCurrentTimeStamp(bp_second);
        this.bp_third = getCurrentTimeStamp(bp_third);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getBs_first() {
        return bs_first;
    }

    public void setBs_first(String bs_first) {
        this.bs_first = bs_first;
    }

    public String getBs_second() {
        return bs_second;
    }

    public void setBs_second(String bs_second) {
        this.bs_second = bs_second;
    }

    public String getBs_third() {
        return bs_third;
    }

    public void setBs_third(String bs_third) {
        this.bs_third = bs_third;
    }

    public String getBp_first() {
        return bp_first;
    }

    public void setBp_first(String bp_first) {
        this.bp_first = bp_first;
    }

    public String getBp_second() {
        return bp_second;
    }

    public void setBp_second(String bp_second) {
        this.bp_second = bp_second;
    }

    public String getBp_third() {
        return bp_third;
    }

    public void setBp_third(String bp_third) {
        this.bp_third = bp_third;
    }
    public static String getCurrentTimeStamp(String dateString) throws ParseException {//時間格式轉換
        String date1 = dateString;
        String strDate = "";
        Locale locale = Locale.US;
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm",locale);//format yyyy-MM-dd HH:mm:ss to HH:mm
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale);
        Calendar calendar = new GregorianCalendar();

        Date date = sdf.parse(date1);
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH)+1;
        if (month==1){
            strDate ="";
        }else {
            strDate = sdfDate.format(date);
        }
        return strDate;
    }

}
