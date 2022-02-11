package com.troy.mysqlapp;

public class Fruit {
    String name;
    double price;

    public Fruit() {
    setName("");
    setPrice(0.0);
    }

    public Fruit(String name, double price) {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
