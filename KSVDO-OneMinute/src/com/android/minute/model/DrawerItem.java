package com.android.minute.model;

/**
 * Created by xiangyi.wu on 2014/11/4.
 */
public class DrawerItem {
    private int img;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public DrawerItem(String name, int img) {
        this.name = name;
        this.img = img;
    }
}
