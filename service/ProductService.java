package service;

import model.Product;
import util.FileHandler;
import java.util.*;

public class ProductService {
    private final String PRODUCT_FILE = "products.txt";

    public void addProduct(Product product) {
        FileHandler.writeLine(PRODUCT_FILE, product.toString());
        System.out.println("✅ Product added successfully!");
    }

    public List<Product> getAllProducts() {
        List<String> lines = FileHandler.readAllLines(PRODUCT_FILE);
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            Product p = Product.fromString(line);
            if (p != null) products.add(p);
        }
        return products;
    }

    public int getNextProductId() {
        List<Product> products = getAllProducts();
        return products.isEmpty() ? 1 : products.get(products.size() - 1).getId() + 1;
    }
}
