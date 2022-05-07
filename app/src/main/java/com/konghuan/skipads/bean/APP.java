package com.konghuan.skipads.bean;

import android.graphics.drawable.Drawable;

public class APP {
    private Drawable Img;
    private String Name;
    private String Edition;

    public APP() {
    }

    public APP(String name,String edition, Drawable img) {
        this.Img = img;
        this.Name = name;
        this.Edition = edition;
    }

    public Drawable getImg() {
        return Img;
    }

    public void setImg(Drawable img) {
        Img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEdition() {
        return Edition;
    }

    public void setEdition(String edition) {
        Edition = edition;
    }

    public void setImageResourceId(Drawable imageResourceId) {
        this.Img = imageResourceId;
    }

}
