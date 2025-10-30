package service;

import model.Product;
import util.FileHandler;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String FILE_PATH = "data/products.txt";

    public void addProduct(Product product) {
        String line = product.getId() + "," + product.getName() + "," +
                product.getBrand() + "," + product.getPrice() + "," +
                product.getQuantity();
        FileHandler.writeLine(FILE_PATH, line);
        System.out.println("✅ Product added successfully!");
    }

    public List<Product> getAllProducts() {
        List<String> lines = FileHandler.readAllLines(FILE_PATH);
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 5) {
                try {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String brand = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);
                    products.add(new Product(id, name, brand, price, quantity));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        }
        return products;
    }

    public int getNextProductId() {
        List<Product> products = getAllProducts();
        if (products.isEmpty()) return 101;
        return products.get(products.size() - 1).getId() + 1;
    }
}