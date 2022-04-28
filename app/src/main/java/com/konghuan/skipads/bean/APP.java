package com.konghuan.skipads.bean;

public class APP {
    private int Img;
    private String Name;

    public APP() {
    }

    public APP(String name, int img) {
        this.Img = img;
        this.Name = name;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImageResourceId(int imageResourceId) {
        this.Img = imageResourceId;
    }

}
