package com.example.carrie.carrie_test1;

/**
 * Created by mindy on 2017/8/6.
 */

public class Drug {
    public int id;
    public  String chineseName;
    public String licenseNumber;
    public String indication;
    public String englishName;
    public String category;
    public String image;
    public String component;
    public String delivery;
    public String maker_Name;
    public String maker_Country;
    public String applicant;
    public String sideEffect;
    public String QRCode;
    public int searchTimes;

    public Drug(int id, String chineseName, String licenseNumber, String indication, String englishName, String category, String image, String delivery, String maker_Name, String maker_Country, String applicant) {
        this.id = id;
        this.chineseName = chineseName;
        this.licenseNumber = licenseNumber;
        this.indication = indication;
        this.englishName = englishName;
        this.category = category;
        this.image = image;
        //this.component = component;
        this.delivery = delivery;
        this.maker_Name = maker_Name;
        this.maker_Country = maker_Country;
        this.applicant = applicant;
        //this.sideEffect = sideEffect;
        //this.QRCode = QRCode;
        //this.searchTimes = searchTimes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getMaker_Name() {
        return maker_Name;
    }

    public void setMaker_Name(String maker_Name) {
        this.maker_Name = maker_Name;
    }

    public String getMaker_Country() {
        return maker_Country;
    }

    public void setMaker_Country(String maker_Country) {
        this.maker_Country = maker_Country;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public int getSearchTimes() {
        return searchTimes;
    }

    public void setSearchTimes(int searchTimes) {
        this.searchTimes = searchTimes;
    }
}
