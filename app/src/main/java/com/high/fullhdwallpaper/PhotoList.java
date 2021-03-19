package com.high.fullhdwallpaper;

public class PhotoList {
    int id;
    String larageurl,mediumurl;

    public PhotoList(int id, String larageurl, String mediumurl) {
        this.id = id;
        this.larageurl = larageurl;
        this.mediumurl = mediumurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLarageurl() {
        return larageurl;
    }

    public void setLarageurl(String larageurl) {
        this.larageurl = larageurl;
    }

    public String getMediumurl() {
        return mediumurl;
    }

    public void setMediumurl(String mediumurl) {
        this.mediumurl = mediumurl;
    }
}
