package com.example.wheelsdb;
public class Cars
{
    String name,brand,price,engine,key,stock;
    public Cars()
    {

    }
    public Cars(String name, String brand, String price, String engine,String stock,String key) {

        this.name = name;
        this.brand = brand;
        this.price = price;
        this.engine=engine;
        this.key = key;
        this.stock=stock;

    }

    public String getStock() {return stock;}

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getEngine() {
        return engine;
    }



}

