package com.example.carrie.carrie_test1;

/**
 * Created by rita on 2017/8/16.
 */

public class m_calendar {
    private int id;
    private String mcalname;
    private String mcaldate;
    private String mcalpercent;

    public m_calendar(int id, String mcalname, String mcaldate, String mcalpercent) {
        this.id = id;
        this.mcalname = mcalname;
        this.mcaldate = mcaldate;
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

    public String getMcaldate() {
        return mcaldate;
    }

    public void setMcaldate(String mcaldate) {
        this.mcaldate = mcaldate;
    }

    public String getMcalpercent() {
        return mcalpercent;
    }

    public void setMcalpercent(String mcalpercent) {
        this.mcalpercent = mcalpercent;
    }
}
