package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/24.
 */

public class BloodSugar {
    public int id;
    public String member_id;

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

    public String getBloodsugar() {
        return bloodsugar;
    }

    public void setBloodsugar(String bloodsugar) {
        this.bloodsugar = bloodsugar;
    }

    public String getSavetime() {
        return savetime;
    }

    public void setSavetime(String savetime) {
        this.savetime = savetime;
    }

    @Override
    public String toString() {
        return
                "血糖:" + bloodsugar + "mg/dL" + "\n" +
                "記錄時間:" + savetime ;
    }

    public String bloodsugar;
    public String savetime;
    public BloodSugar(int id, String member_id, String bloodsugar, String savetime) {
        this.id = id;
        this.member_id = member_id;
        this.bloodsugar = bloodsugar;
        this.savetime = savetime;
    }


}
