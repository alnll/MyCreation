package com.example.happy.mycreation;


import java.util.HashMap;
import java.util.Map;


public class CustomerUsers {

    private String CID;
    private String cusName;
    private String cusEmail;
    private String cusPhone;

    public CustomerUsers(){}
    public CustomerUsers(String CID, String cusName, String cusEmail, String cusPhone){
        this.CID = CID;
        this.cusName = cusName;
        this.cusEmail = cusEmail;
        this.cusPhone = cusPhone;
    }


    public String getCID(){
        return CID;
    }

    public void setCID(String CID){
        this.CID = CID;
    }

    public String getCusName(){
        return cusName;
    }

    public void setCusName(String cusName){
        this.cusName = cusName;
    }

    public String getCusEmail(){
        return cusEmail;
    }

    public void setCusEmail(String cusEmail){
        this.cusEmail= cusEmail;
    }

    public String getCusPhone(){
        return cusPhone;
    }

    public void setCusPhone(String cusPhone){
        this.cusPhone = cusPhone;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("CustomerName", cusName);
        result.put("CustomerEmail", cusEmail);
        result.put("CustomerPhoneNumber", cusPhone);
        return result;
    }

}

