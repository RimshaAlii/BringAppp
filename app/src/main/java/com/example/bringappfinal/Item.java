package com.example.bringappfinal;

public class Item {
    private int itemid;
    private int itemlistid;
    private String itemname;
    private String itemprice;
    private String itemquantity;

    public void setItemid(int itemid) { this.itemid=itemid; }
    public void setItemlistid(int itemlistid) { this.itemlistid=itemlistid; }
    public void setItemname(String itemname) { this.itemname=itemname; }
    public void setItemprice(String itemprice) { this.itemprice=itemprice; }
    public void setItemquantity(String itemquantity) { this.itemquantity=itemquantity; }

    public int getItemid() {return itemid;}
    public int getItemlistid(){return itemlistid;}
    public String getItemname(){return itemname;}
    public String getItemprice() { return itemprice; }
    public String getItemquantity() { return itemquantity; }
}
