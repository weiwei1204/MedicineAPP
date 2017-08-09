package com.example.carrie.carrie_test1;

/**
 * Created by shana on 2017/8/6.
 */

class MyData {

    public int id;
    public String image_link;
   public String indication, englishName, chineseName,licenseNumber;
    public String category, component, maker_Country, applicant, maker_Name;



    public MyData(int id, String chineseName, String image_link, String indication, String englishName, String licenseNumber
    , String category, String component, String maker_Country, String applicant, String maker_Name) {
        this.id = id;
        this.chineseName= chineseName;
        this.image_link = image_link;
        this.indication= indication;
        this.englishName= englishName;
        this.licenseNumber=licenseNumber;
        this.category=category;
        this.component=component;
        this.maker_Country=maker_Country;
        this.applicant=applicant;
        this.maker_Name=maker_Name;
      //  this.indication= indication;
    }


    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication= indication;
    }
    public  String getEnglishName(){
        return englishName;
    }
    public void setEnglishName(String englishName){
        this.englishName= englishName;
    }
    public String getChineseName(){
        return chineseName;
    }
    public void setChineseName(String chineseName){
        this.chineseName= chineseName;
    }
    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber=licenseNumber;
    }
    public  String getLicenseNumber(){
        return licenseNumber;
    }
    public void setComponent(String component) {
        this.component= component;
    }
    public  String getComponent(){
        return component;
    }
    public void setCategory(String category) {
        this.category= category;
    }
    public  String getCategory(){
        return category;
    }
    public void setMaker_Country(String maker_Country) {
        this.maker_Country= maker_Country;
    }
    public  String getMaker_Country(){
        return maker_Country;
    }
    public void setMaker_Name(String maker_name) {
        this.maker_Name= maker_name;
    }
    public  String getMaker_Name(){
        return maker_Name;
    }
    public void setApplicant(String applicant) {
        this.indication= applicant;
    }
    public  String getApplicant(){
        return applicant;
    }
}
