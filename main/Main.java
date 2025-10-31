/*
Author: Prakarsha Poudel
*/
package main;

import model.Product;
import model.Sale;
import service.ProductService;
import service.SalesService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductService productService = new ProductService();
        SalesService salesService = new SalesService();

        boolean running = true;

        System.out.println("===== Luxury Department Store Management System =====");

        while (running) {
            System.out.println("1. Add New Luxury Item");
            System.out.println("2. View All Items");
            System.out.println("3. Purchase Item");
            System.out.println("4. Apply Discount");
            System.out.println("5. View Sales");
            System.out.println("6. Quit");
            System.out.print("Select option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer
            switch (choice) {
                case 1 -> {
                    int id = productService.getNextProductId();

                    // Item name
                    String name;
                    while (true) {
                        System.out.print("Enter item name: ");
                        name = sc.nextLine().trim();
                        if (!name.isEmpty()) break;
                        System.out.println("Item name cannot be empty.");
                    }

                    // Brand
                    String brand;
                    while (true) {
                        System.out.print("Enter brand: ");
                        brand = sc.nextLine().trim();
                        if (!brand.isEmpty()) break;
                        System.out.println("Brand cannot be empty.");
                    }

                    // Price
                    double price;
                    while (true) {
                        System.out.print("Enter price: ");
                        try {
                            price = Double.parseDouble(sc.nextLine());
                            if (price >= 0) break;
                            System.out.println("Price cannot be negative.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid price. Please enter a number.");
                        }
                    }

                    // Quantity
                    int quantity;
                    while (true) {
                        System.out.print("Enter quantity: ");
                        try {
                            quantity = Integer.parseInt(sc.nextLine());
                            if (quantity >= 0) break;
                            System.out.println("Quantity cannot be negative.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid quantity. Please enter a whole number.");
                        }
                    }

                    // Add product
                    Product product = new Product(id, name, brand, price, quantity);
                    productService.addProduct(product);
                    System.out.println("Product added successfully!");
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
                    // Step 1: Customer chooses product
                    System.out.println("\n===== Purchase Item =====");
                    System.out.print("Enter item id to purchase: ");
                    int id;
                    try {
                        id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid item ID.");
                        break;
                    }

                    Product item = productService.getProductById(id);
                    if (item == null) {
                        System.out.println("Item not found.");
                        break;
                    }

                    System.out.println("Selected: " + item.getName() + " - " + item.getBrand());
                    System.out.println("Price: $" + String.format("%.2f", item.getPrice()));
                    System.out.println("Available quantity: " + item.getQuantity());

                    System.out.print("Enter quantity to purchase: ");
                    int qty;
                    try {
                        qty = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity.");
                        break;
                    }

                    if (qty <= 0) {
                        System.out.println("Quantity must be positive.");
                        break;
                    }

                    if (qty > item.getQuantity()) {
                        System.out.println("Insufficient stock. Only " + item.getQuantity() + " available.");
                        break;
                    }

                    // Step 2: System calculates total with tax
                    double subtotal = item.getPrice() * qty;
                    double tax = subtotal * 0.07; // 7% tax
                    double totalWithTax = subtotal + tax;

                    System.out.println("\n--- Order Summary ---");
                    System.out.println("Subtotal: $" + String.format("%.2f", subtotal));
                    System.out.println("Tax (7%): $" + String.format("%.2f", tax));
                    System.out.println("Total: $" + String.format("%.2f", totalWithTax));

                    System.out.print("\nConfirm purchase? (y/n): ");
                    String confirm = sc.nextLine().trim().toLowerCase();
                    if (!confirm.equals("y") && !confirm.equals("yes")) {
                        System.out.println("Purchase cancelled.");
                        break;
                    }

                    // Step 3: Payment recorded (via purchaseProduct)
                    // Step 4: Inventory reduced (via purchaseProduct)
                    boolean ok = productService.purchaseProduct(id, qty);
                    if (ok) {
                        // Step 5: Receipt generated
                        SalesService tempSalesService = new SalesService();
                        var allSales = tempSalesService.getAllSales();
                        if (!allSales.isEmpty()) {
                            model.Sale lastSale = allSales.get(allSales.size() - 1);
                            System.out.println(lastSale.generateReceipt());
                        }
                    } else {
                        System.out.println("Purchase failed. Please try again.");
                    }
                }

                case 4 -> {
                    System.out.print("Enter item id: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Product item = productService.getProductById(id);
                    if (item == null) {
                        System.out.println("Item not found.");
                        break;
                    }

                    System.out.println("Current price: $" + item.getPrice());
                    System.out.print("Enter discount percentage: ");
                    int discount = Integer.parseInt(sc.nextLine());
                    productService.discountProduct(item, discount);
                    System.out.println("New price: " + item.getPrice());
                }

                case 5 -> {
                    System.out.println("\n===== Sales Records =====");
                    var sales = salesService.getAllSales();
                    if (sales.isEmpty()) {
                        System.out.println("No sales yet.");
                    } else {
                        sales.forEach(s -> System.out.println(s.toString()));
                        System.out.println("\n--- Total Revenue ---");
                        double totalRevenue = salesService.calculateTotalRevenue();
                        System.out.println("Total Revenue: $" + String.format("%.2f", totalRevenue));
                    }
                }

                case 6 -> {
                    System.out.print("=== Goodbye ===");
                    running = false; // End program
                }

                default -> System.out.println("Invalid option. Please try again.");
            }
            System.out.println("\nPress any key to continue...");
            sc.nextLine();
        }
        sc.close();
    }
}
