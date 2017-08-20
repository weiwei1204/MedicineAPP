package com.example.carrie.carrie_test1;

import java.util.Date;

/**
 * Created by jonathan on 2017/8/17.
 */

public class BloodPressure {
    public int id;
    public String member_id;
    public String highmmhg;
    public String lowmmhg;
    public String bpm;
    public String sugarvalue;
    public String savetime;
    public BloodPressure(int id, String member_id, String highmmhg, String lowmmhg, String bpm, String sugarvalue, String savetime) {
        this.id = id;
        this.member_id = member_id;
        this.highmmhg = highmmhg;
        this.lowmmhg = lowmmhg;
        this.bpm = bpm;
        this.sugarvalue = sugarvalue;
        this.savetime = savetime;
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

    public String getHighmmhg() {
        return highmmhg;
    }

    public void setHighmmhg(String highmmhg) {
        this.highmmhg = highmmhg;
    }

    public String getLowmmhg() {
        return lowmmhg;
    }

    public void setLowmmhg(String lowmmhg) {
        this.lowmmhg = lowmmhg;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getSugarvalue() {
        return sugarvalue;
    }

    public void setSugarvalue(String sugarvalue) {
        this.sugarvalue = sugarvalue;
    }

    public String getSavetime() {
        return savetime;
    }

    public void setSavetime(String savetime) {
        this.savetime = savetime;
    }


}
