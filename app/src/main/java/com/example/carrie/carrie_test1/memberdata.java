package com.example.carrie.carrie_test1;

/**
 * Created by rita on 2017/9/14.
 */

public class memberdata {
    private static String member_id,google_id,my_mon_id;

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
}
