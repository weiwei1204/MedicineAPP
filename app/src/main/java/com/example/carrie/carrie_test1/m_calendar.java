package com.example.carrie.carrie_test1;

/**
 * Created by rita on 2017/8/16.
 */

public class m_calendar {
    private int id;
    private String mcalname;
    private String mcaldelay;
    private String mcalpercent;

    public m_calendar(int id, String mcalname, String mcalpercent, String mcaldelay) {
        this.id = id;
        this.mcalname = mcalname;
        this.mcaldelay = mcaldelay;
        this.mcalpercent = mcalpercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMcalname() {
        return mcalname;
    }

    public void setMcalname(String mcalname) {
        this.mcalname = mcalname;
    }

    public String getMcaldelay() {
        return mcaldelay;
    }

    public void setMcaldelay(String mcaldelay) {
        this.mcaldelay = mcaldelay;
    }

    public String getMcalpercent() {
        return mcalpercent;
    }

    public void setMcalpercent(String mcalpercent) {
        this.mcalpercent = mcalpercent;
    }
}
