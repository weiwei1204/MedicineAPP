package com.example.carrie.carrie_test1;

/**
 * Created by shana on 2017/9/5.
 */

public class RepairData {

    public static String name;
    public static String email;
    public static String gender_man;
    public static String weight;
    public static String height;
    public static String birth;
    public static String google_id;

    public RepairData(String name, String email, String gender_man, String weight, String height, String birth, String google_id){
        this.name= name;
        this.email=email;
        this.gender_man=gender_man;
        this.weight=weight;
        this.height=height;
        this.birth=birth;
        this.google_id=google_id;

    }
    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setGender_man(String gender_man) {
        this.gender_man = gender_man;
    }

    public String getGender_man() {
        return gender_man;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getBirth() {
        return birth;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getGoogle_id() {return google_id;}



}
