package com.example.myquiz;

public class Category {
    public static final int PROGRAMMING = 1;
    public static final int APTITUDE = 2;
    public static final int SQL = 3;
    public static final int CN = 4;


    private int id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}