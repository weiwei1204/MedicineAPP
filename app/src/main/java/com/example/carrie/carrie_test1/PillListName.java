package com.example.carrie.carrie_test1;

/**
 * Created by mindy on 2017/9/13.
 */

public class PillListName {
    public int id ;
    public String name ;
    public int m_day ;
    public int finish ;
    public int time_count ;

    public PillListName(int id, String name, int m_day, int finish, int time_count) {
        this.id = id;
        this.name = name;
        this.m_day = m_day;
        this.finish = finish;
        this.time_count = time_count;
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

    public int getM_day() {
        return m_day;
    }

    public void setM_day(int m_day) {
        this.m_day = m_day;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getTime_count() {
        return time_count;
    }

    public void setTime_count(int time_count) {
        this.time_count = time_count;
    }
}
