package com.example.carrie.carrie_test1;

/**
 * Created by mindy on 2017/9/13.
 */

public class PillListObject {
    public  int id;
    public int Medicine_Clander_id;
    public String eatDate;
    public String eatTime;
    public int finishcheck;

    public PillListObject(int id, int medicine_Clander_id, String eatDate, String eatTime, int finishcheck) {
        this.id = id;
        Medicine_Clander_id = medicine_Clander_id;
        this.eatDate = eatDate;
        this.eatTime = eatTime;
        this.finishcheck = finishcheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicine_Clander_id() {
        return Medicine_Clander_id;
    }

    public void setMedicine_Clander_id(int medicine_Clander_id) {
        Medicine_Clander_id = medicine_Clander_id;
    }

    public String getEatDate() {
        return eatDate;
    }

    public void setEatDate(String eatDate) {
        this.eatDate = eatDate;
    }

    public String getEatTime() {
        return eatTime;
    }

    public void setEatTime(String eatTime) {
        this.eatTime = eatTime;
    }

    public int getFinishcheck() {
        return finishcheck;
    }

    public void setFinishcheck(int finishcheck) {
        this.finishcheck = finishcheck;
    }
}
