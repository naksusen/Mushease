package com.example.herblife;

public class MushroomModel {
    String mushname;
    String mushsciname;
    String mushdesc;
    int mushimage;

    public MushroomModel(String mushname, String mushsciname, String mushdesc, int mushimage) {
        this.mushname = mushname;
        this.mushsciname = mushsciname;
        this.mushdesc =mushdesc;
        this.mushimage = mushimage;

    }

    public String getMushname() {
        return mushname;
    }

    public String getMushsciname() {
        return mushsciname;
    }

    public String getMushdesc(){return mushdesc; }

    public int getMushimage() {
        return mushimage;
    }


}
