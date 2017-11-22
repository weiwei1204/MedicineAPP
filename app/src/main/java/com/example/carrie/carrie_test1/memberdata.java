package com.example.carrie.carrie_test1;

import java.util.ArrayList;

/**
 * Created by rita on 2017/9/14.
 */

public class memberdata {
    private static String member_id,google_id,my_mon_id;
    private static ArrayList<String> needBeacon = new ArrayList<String>();
    private static ArrayList<String> Beaconcal = new ArrayList<String>();
    private static ArrayList<String> storeAPBSSID = new ArrayList<String>();
    private static String name,email;
    public static int measure_id;
    public static String bs_first;
    public static String bs_second;
    public static String bs_third;
    public static String bp_first;

    public static int getMeasure_id() {
        return measure_id;
    }

    public static void setMeasure_id(int measure_id) {
        memberdata.measure_id = measure_id;
    }

    public static String getBs_first() {
        return bs_first;
    }

    public static void setBs_first(String bs_first) {
        memberdata.bs_first = bs_first;
    }

    public static String getBs_second() {
        return bs_second;
    }

    public static void setBs_second(String bs_second) {
        memberdata.bs_second = bs_second;
    }

    public static String getBs_third() {
        return bs_third;
    }

    public static void setBs_third(String bs_third) {
        memberdata.bs_third = bs_third;
    }

    public static String getBp_first() {
        return bp_first;
    }

    public static void setBp_first(String bp_first) {
        memberdata.bp_first = bp_first;
    }

    public static String getBp_second() {
        return bp_second;
    }

    public static void setBp_second(String bp_second) {
        memberdata.bp_second = bp_second;
    }

    public static String getBp_third() {
        return bp_third;
    }

    public static void setBp_third(String bp_third) {
        memberdata.bp_third = bp_third;
    }

    public static String bp_second;
    public static String bp_third;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        memberdata.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        memberdata.email = email;
    }

    public static String getMember_id() {
        return member_id;
    }

    public static void setMember_id(String member_id) {
        memberdata.member_id = member_id;
    }

    public static String getGoogle_id() {
        return google_id;
    }

    public static void setGoogle_id(String google_id) {
        memberdata.google_id = google_id;
    }

    public static String getMy_mon_id() {
        return my_mon_id;
    }

    public static void setMy_mon_id(String my_mon_id) {
        memberdata.my_mon_id = my_mon_id;
    }

    public static ArrayList<String> getNeedBeacon() {
        return needBeacon;
    }

    public static void setNeedBeacon(ArrayList<String> needBeacon) {
        memberdata.needBeacon = needBeacon;
    }

    public static ArrayList<String> getBeaconcal() {
        return Beaconcal;
    }

    public static void setBeaconcal(ArrayList<String> Beaconcal) {
        memberdata.Beaconcal = Beaconcal;
    }

    public static ArrayList<String> getStoreAPBSSID() {
        return storeAPBSSID;
    }

    public static void setStoreAPBSSID(ArrayList<String> storeAPBSSID) {
        memberdata.storeAPBSSID = storeAPBSSID;
    }
}
