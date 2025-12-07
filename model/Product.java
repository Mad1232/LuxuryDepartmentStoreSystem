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
    private boolean limited_edition;
    private String releaseDate;

    public Product(int id, String name, String category, double price, String brand, boolean limited_edition, String releaseDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.limited_edition = limited_edition;
        this.releaseDate = releaseDate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getBrand() { return brand; }
    public boolean isLimitedEdition() { return limited_edition; }
    public String getReleaseDate() { return releaseDate; }

    // Set product price
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + category + " | " + brand +
                " | $" + String.format("%.2f", price) +
                " | Limited Edition: " + limited_edition +
                " | Release Date: " + releaseDate;
    }

    public String editString() {
        return id + "," + name + "," + category + "," + price + "," + brand + "," + limited_edition + "," + releaseDate;
    }
}