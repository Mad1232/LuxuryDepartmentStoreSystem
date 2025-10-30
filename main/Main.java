/*
Author: Prakarsha Poudel
*/
package main;

import model.Product;
import service.ProductService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductService productService = new ProductService();

        while (true) {
            System.out.println("\n===== Luxury Department Store Management System =====");
            System.out.println("1. Add New Luxury Item");
            System.out.println("2. View All Items");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    int id = productService.getNextProductId();

                    // Repeated input until valid item name
                    String name;
                    while (true) {
                        System.out.print("Enter item name: ");
                        name = sc.nextLine().trim();
                        if (!name.isEmpty()) break;
                        System.out.println("⚠️ Item name cannot be empty.");
                    }

                    // Repeated input until valid brand name
                    String brand;
                    while (true) {
                        System.out.print("Enter brand: ");
                        brand = sc.nextLine().trim();
                        if (!brand.isEmpty()) break;
                        System.out.println("⚠️ Brand cannot be empty.");
                    }

                    // Repeated input until valid price
                    double price;
                    while (true) {
                        System.out.print("Enter price: ");
                        try {
                            price = Double.parseDouble(sc.nextLine());
                            if (price >= 0) break;
                            System.out.println("⚠️ Price cannot be negative.");
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️ Invalid price. Please enter a number.");
                        }
                    }

                    // Repeated input until valid quantity
                    int quantity;
                    while (true) {
                        System.out.print("Enter quantity: ");
                        try {
                            quantity = Integer.parseInt(sc.nextLine());
                            if (quantity >= 0) break;
                            System.out.println("⚠️ Quantity cannot be negative.");
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️ Invalid quantity. Please enter a whole number.");
                        }
                    }

                    // Add product
                    Product product = new Product(id, name, brand, price, quantity);
                    productService.addProduct(product);
                    System.out.println("✅ Product added successfully!");
                }

                case 2 -> {
                    System.out.println("\nAvailable Items:");
                    var products = productService.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        products.forEach(System.out::println);
                    }
                }

                case 3 -> {
                    System.out.println("👋 Exiting system. Goodbye!");
                    sc.close();
                    return;
                }

                default -> System.out.println("⚠️ Invalid option. Please try again.");
            }
        }
    }
}