/*
Author: Prakarsha Poudel
 */
package service;

import model.Product;
import util.FileHandler;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String FILE_PATH = "data/products.txt";

    public void addProduct(Product product) {
        String line = product.getId() + "," + product.getName() + "," +
                product.getBrand() + "," + product.getPrice();
        FileHandler.writeLine(FILE_PATH, line);
    }

    public void editProduct(Product product) {
        List<Product> products = getAllProducts();
        int replace = -1;
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getId() == product.getId()){
                replace = i;
                break;
            }
        }
        if (replace != -1) {
            products.set(replace, product);
            FileHandler.writeAllLines(FILE_PATH, products.stream().map(Product::editString).toList());
        }
    }

    public List<Product> getAllProducts() {
        List<String> lines = FileHandler.readAllLines(FILE_PATH);
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                try {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String brand = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    products.add(new Product(id, name, brand, price));
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

    // Get specific product by ID
    public Product getProductById(int id) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // Calculate and apply discount to products.txt file
    public void discountProduct(Product product, Integer discountPercentage) {
        // Calculate discount from percentage
        Double discountedPrice = product.getPrice() * (1 - discountPercentage / 100.0);
        product.setPrice(discountedPrice);

        // Update the product in file
        editProduct(product);
    }
}
