package com.example.wheelsdb;

/*public class Cars
{
    String name,brand,price,engine;

    public Cars(String name, String brand, String price, String engine)
    {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.engine = engine;
    }

    public Cars()
    {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}*/


/*
public class Cars {
    String name,brand,price,engine;


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }



    public Cars(String name, String price, String brand, String engine) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.engine=engine;
        //this.image=image;
    }


    public String getEngine() {
        return engine;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getImage() {return image;}
}

 */

public class Cars
{
    String name;
    String brand;
    String price;

    String engine;
    String key;

    public Cars()
    {
    }

    public Cars(String name, String brand, String price, String engine,String key) {

        this.name = name;
        this.brand = brand;
        this.price = price;

        this.engine=engine;
        this.key = key;

    }

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

