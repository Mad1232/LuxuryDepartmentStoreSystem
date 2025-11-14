/*
Author: Prakarsha Poudel
 */
package model;

public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;

    public Product(int id, String name, String brand, double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }

    // Set product price
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + brand + " | $" + String.format("%.2f", price);
    }

    public String editString() {
        return id + "," + name + "," + brand + "," + price;
    }
}