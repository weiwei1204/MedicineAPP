package com.example.carrie.carrie_test1;

import java.util.ArrayList;

/**
 * Created by rita on 2017/8/23.
 */

public class mcaldata {
    private static String mcalname1,mcaldate1,mcalday1,memberid1;
    private static int mbeaconid1;
    private static ArrayList<ArrayList<String>> mcaltimes1 = new ArrayList<ArrayList<String>>();
    private static ArrayList<ArrayList<String>> mcaldrugs1 = new ArrayList<ArrayList<String>>();
    private static ArrayList<ArrayList<String>> newdrugs1 = new ArrayList<ArrayList<String>>();
    private static mcaldata mcaldata = new mcaldata();
    private mcaldata(){}

    public static String getMemberid() {
        return memberid1;
    }

    public static void setMemberid(String memberid) {
       memberid1 = memberid;
    }

    public static String getMcalname() {
        return mcalname1;
    }

    public static void setMcalname(String mcalname) {
        mcalname1 = mcalname;
    }

    public static String getMcaldate() {
        return mcaldate1;
    }

    public static void setMcaldate(String mcaldate) {
        mcaldate1 = mcaldate;
    }

    public static String getMcalday() {
        return mcalday1;
    }

    public static void setMcalday(String mcalday) {
        mcalday1 = mcalday;
    }

    public static int getMbeaconid() {
        return mbeaconid1;
    }

    public static void setMbeaconid(int mbeaconid) {
        mbeaconid1 = mbeaconid;
    }

    public static ArrayList getMcaltimes() {
        return mcaltimes1;
    }

    public static void setMcaltimes(ArrayList mcaltimes) {
        mcaltimes1 = mcaltimes;
    }

    public static ArrayList getMcaldrugs() {
        return mcaldrugs1;
    }

    public static void setMcaldrugs(ArrayList mcaldrugs) {
        mcaldrugs1 = mcaldrugs;
    }

    public static ArrayList<ArrayList<String>> getNewdrugs1() {
        return newdrugs1;
    }

    public static void setNewdrugs1(ArrayList<ArrayList<String>> newdrugs) {
        newdrugs1 = newdrugs;
    }
}
