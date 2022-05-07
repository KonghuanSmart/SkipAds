package com.konghuan.skipads.bean;

import android.graphics.drawable.Drawable;

public class APP {
    private Drawable img;
    private String name;
    private String edition;
    private String packageName;

    public APP() {
    }

    public APP(String name,String edition, Drawable img) {
        this.img = img;
        this.name = name;
        this.edition = edition;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setImageResourceId(Drawable imageResourceId) {
        this.img = imageResourceId;
    }

}
