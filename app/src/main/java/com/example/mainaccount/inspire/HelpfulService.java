package com.example.mainaccount.inspire;

import java.util.ArrayList;

/**
 * Created by mainaccount on 08/07/2017.
 */

public class HelpfulService {
    private int id;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private String url;
    private ArrayList<HelpfulService> list;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HelpfulService(){

    }

    public HelpfulService(int id, String name, String address, String phoneNo, String email, String url){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
