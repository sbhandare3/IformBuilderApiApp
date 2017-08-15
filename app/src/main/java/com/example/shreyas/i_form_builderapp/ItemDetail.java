package com.example.shreyas.i_form_builderapp;

/**
 * Created by shreyas on 8/15/2017.
 */

public class ItemDetail {

    private String I_id;
    private String I_image;
    private String I_name;
    private String I_age;
    private String I_phone;
    private String I_date;

    public ItemDetail(){
        I_id = null;
        I_image = null;
        I_name = null;
        I_age = null;
        I_phone = null;
        I_date = null;
    }

    public void setItemId(String id){
        this.I_id = id;
    }

    public String getItemId(){
        return I_id;
    }

    public String getName(){
        return I_name;
    }

    public void setName(String name) {
        this.I_name = name;
    }

    public String getPhone(){
        return I_phone;
    }

    public void setPhone(String phone) {
        this.I_phone = phone;
    }

    public String getAge(){
        return I_age;
    }

    public void setAge(String age) {
        this.I_age = age;
    }

    public String getDate(){
        return I_date;
    }

    public void setDate(String date) {
        this.I_date = date;
    }

    public void setImage(String img){
        this.I_image = img;
    }

    public String getImage(){
        return I_image;
    }
}
