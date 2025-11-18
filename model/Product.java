/*
Author: Prakarsha Poudel
 */
package model;

public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private String brand;

    public Product(int id, String name, String category, double price, String brand) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.brand = brand;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getBrand() { return brand; }

    // Set product price
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + category + " | " + brand + " | $" + String.format("%.2f", price);
    }

    public String editString() {
        return id + "," + name + "," + category + "," + price + "," + brand;
    }
}