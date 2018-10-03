package com.example.shahir.orderyourfood.Model;

import com.google.firebase.database.PropertyName;

/**
 * Created by ShahiR on 2/24/2018.
 */

public class Category {
    private  String Name;
    private String Image;
    private  String menuId;

    public Category() {

    }

    @PropertyName("MenuId")
    public String getMenuId() {
        return menuId;
    }

    @PropertyName("MenuId")
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Category(String name, String image, String MenuId) {
        Name = name;
        Image = image;
        menuId=MenuId;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
