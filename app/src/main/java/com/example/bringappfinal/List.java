package com.example.bringappfinal;
public class List {
    private int listid;
    private String listname;
    private String listdate;

    public String getListdate() {
        return listdate;
    }

    public void setListdate(String listdate) {
        this.listdate = listdate;
    }

    private String budget;

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public int getListid()
    {
        return  listid;
    }
    public void setListid(int listid)
    {
        this.listid=listid;
    }
    public String getListname()
    {
        return  listname;
    }
    public void setListname(String listname)
    {
        this.listname=listname;
    }
}
