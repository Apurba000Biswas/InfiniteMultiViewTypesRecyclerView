package com.apurba.infinitemultiviewtypesrecyclerview;

public class DataItem {


    private String name;
    private int id;
    public DataItem(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }
}
