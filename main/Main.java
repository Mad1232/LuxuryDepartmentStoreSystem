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

        System.out.println("===== Luxury Department Store Management System =====");
        System.out.println("1. Add New Luxury Item");
        System.out.println("2. View All Items");
        System.out.println("3. Apply Discount");
        System.out.print("Select option: ");
        int choice = sc.nextInt();
        sc.nextLine(); // clear buffer

        switch (choice) {
            case 1 -> {
                int id = productService.getNextProductId();
                System.out.print("Enter item name: ");
                String name = sc.nextLine();
                System.out.print("Enter brand: ");
                String brand = sc.nextLine();
                System.out.print("Enter price: ");
                double price = sc.nextDouble();
                System.out.print("Enter quantity: ");
                int quantity = sc.nextInt();

                Product product = new Product(id, name, brand, price, quantity);
                productService.addProduct(product);
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
            case 3 -> {
                System.out.println("Enter item id:");
                Product item_discounting = productService.getProductById(Integer.parseInt(sc.nextLine()));
                System.out.println("Current price: " + item_discounting.getPrice());
                System.out.println("Enter discount percentage:");
                Integer discount_percentage = Integer.parseInt(sc.nextLine());
                productService.discountProduct(item_discounting, discount_percentage);
                System.out.println("New price: " + item_discounting.getPrice());
            }
            default -> System.out.println("Invalid option");
        }
    }
}