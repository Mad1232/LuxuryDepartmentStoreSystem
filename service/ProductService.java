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
                product.getBrand() + "," + product.getPrice() + "," +
                product.getQuantity();
        FileHandler.writeLine(FILE_PATH, line);
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

    // Get specific product by ID
    public Product getProductById(int id) {
        List<Product> products = getAllProducts();
        // Check list for specific product ID
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    // Calculate and apply discount to products.txt file
    public void discountProduct(Product product, Integer discountPercentage) {

        // Update text file
        List<Product> products = getAllProducts();
        List<String> updatedLines = new ArrayList<>();

        // Calculate discount from percentage
        Double discountedPrice = product.getPrice() * (1 - discountPercentage / 100.0);
        product.setPrice(discountedPrice);

        for (Product p : products) {
            // Discounted product
            if (p.getId() == product.getId()) {
                updatedLines.add(p.getId() + "," + p.getName() + "," +
                        p.getBrand() + "," + product.getPrice() + "," +
                        p.getQuantity());
            }
            // Not discounted product
            else {
                updatedLines.add(p.getId() + "," + p.getName() + "," +
                        p.getBrand() + "," + p.getPrice() + "," +
                        p.getQuantity());
            }
        }
        FileHandler.writeAllLines(FILE_PATH, updatedLines);
    }

    // Purchase product: decrement stock and return true if successful
    public boolean purchaseProduct(int productId, int purchaseQuantity) {
        if (purchaseQuantity <= 0) return false;
        List<Product> products = getAllProducts();
        List<String> updatedLines = new ArrayList<>();
        Product target = null;
        for (Product p : products) {
            if (p.getId() == productId) {
                target = p;
                break;
            }
        }
        if (target == null) return false; // product not found
        if (target.getQuantity() < purchaseQuantity) return false; // insufficient stock

        // decrement
        target.setQuantity(target.getQuantity() - purchaseQuantity);

        // rewrite product list
        for (Product p : products) {
            if (p.getId() == target.getId()) {
                updatedLines.add(p.getId() + "," + p.getName() + "," + p.getBrand() + "," + p.getPrice() + "," + p.getQuantity());
            } else {
                updatedLines.add(p.getId() + "," + p.getName() + "," + p.getBrand() + "," + p.getPrice() + "," + p.getQuantity());
            }
        }
        FileHandler.writeAllLines(FILE_PATH, updatedLines);

        // Record sale
        SalesService salesService = new SalesService();
        int saleId = salesService.getNextSaleId();
        model.Sale sale = new model.Sale(saleId, target.getId(), target.getName(), purchaseQuantity, target.getPrice());
        salesService.recordSale(sale);
        return true;
    }
}
