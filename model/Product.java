/*
Author: Prakarsha Poudel
 */
package model;

public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;
    private int quantity;

    public Product(int id, String name, String brand, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // Set product price
    public void setPrice(Double price) {
        this.price = price;
    }

    // Set product quantity (used when purchasing)
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + brand + " | $" + price + " | Qty: " + quantity;
    }
}