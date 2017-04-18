package com.example.happy.mycreation;

public class Item {

    private String name, price, qty, prodURI;

    public Item(){

    }

    public Item(String name, String price,String qty, String prodURI){
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.prodURI = prodURI;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProdURI() {
        return prodURI;
    }

}
