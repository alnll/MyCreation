package com.example.happy.mycreation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HaPpY on 4/17/2017.
 */

public class ProductDetails {
    private String buyerID;
    private String title;
    private String price;
    private String quantity;
    private String prodURI;

    public ProductDetails(){
    }

    public ProductDetails(String buyerID,String title, String price, String quantity, String prodURI){
        this.buyerID = buyerID;
        this.title=title;
        this.price=price;
        this.quantity=quantity;
        this.prodURI=prodURI;
    }

    public String getBuyerID(){
        return buyerID;
    }

    public void setBuyerID(String buyerID){
        this.buyerID = buyerID;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){this.title = title;}

    public String getPrice(){return price;}

    public void setPrice(String price){this.price = price;}

    public String getQuantity(){return quantity;}

    public void setQuantity(String quantity){this.quantity = quantity;}

    public String getProdURI(){
        return prodURI;
    }

    public void setProdURI(String prodURI){
        this.prodURI = prodURI;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ProductTitle", title);
        result.put("ProductPrice", price);
        result.put("ProductQuantity", quantity);
        return result;
    }

}
