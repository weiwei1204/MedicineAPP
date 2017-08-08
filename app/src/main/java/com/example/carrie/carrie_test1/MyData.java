package com.example.carrie.carrie_test1;

/**
 * Created by shana on 2017/8/6.
 */

class MyData {

    public int id;
    public String image_link;
   public String indication, englishName, chineseName;



    public MyData(int id, String chineseName, String image_link, String indication, String englishName) {
        this.id = id;
        this.chineseName= chineseName;
        this.image_link = image_link;
        this.indication= indication;
        this.englishName= englishName;
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
}
