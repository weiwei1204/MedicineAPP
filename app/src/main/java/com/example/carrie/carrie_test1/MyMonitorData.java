package com.example.carrie.carrie_test1;

/**
 * Created by mindy on 2017/8/6.
 */

class MyMonitorData {
    private int id;
    private String name;
    private String google_id;

    public MyMonitorData(int id, String name, String google_id) {
        this.id = id;
        this.name = name;
        this.google_id = google_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }
}
