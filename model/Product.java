package model;

public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private double discount;

    public Product(int id, String name, String brand, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.discount = 0.0;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public double getDiscount() { return discount; }

    public void setDiscount(double discount) { this.discount = discount; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return id + "," + name + "," + brand + "," + price + "," + quantity + "," + discount;
    }

    public static Product fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 6) return null;
        Product p = new Product(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                Double.parseDouble(parts[3]),
                Integer.parseInt(parts[4])
        );
        p.setDiscount(Double.parseDouble(parts[5]));
        return p;
    }
}
