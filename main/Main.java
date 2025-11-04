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
            System.out.println("3. Edit An Item");
            System.out.println("4. Purchase Item");
            System.out.println("5. Apply Discount");
            System.out.println("6. View Sales");
            System.out.println("7. Quit");
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

                case 3-> {
                    System.out.println("\n===== Edit an Item =====");
                    System.out.print("Enter item id to edit: ");
                    int id;
                    try {
                        id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid item ID.");
                        break;
                    }

                    Product item = productService.getProductById(id);
                    System.out.println(item.editString());
                    System.out.println("Please enter data in same format as above ^^^");
                    System.out.println("Enter the updated information below:");
                    Scanner editItem = new Scanner(System.in);
                    String newText = editItem.nextLine();
                    String[] split = newText.split(",");
                    Product newItem = new Product(item.getId(),split[1],split[2],Double.parseDouble(split[3]),Integer.parseInt(split[4]));
                    productService.editProduct(newItem);

                }


                case 4 -> {
                    // Step 1: Display available items and allow customer to choose multiple products
                    System.out.println("\n===== Purchase Item =====");
                    System.out.println("\nAvailable Items:");
                    var products = productService.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("No products available for purchase.");
                        break;
                    }

                    // Display all available items
                    products.forEach(p ->
                        System.out.printf("ID: %d | %s - %s | Price: $%.2f | In Stock: %d%n",
                            p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getQuantity())
                    );

                    // Shopping cart to hold multiple items
                    java.util.List<Product> cart = new java.util.ArrayList<>();
                    java.util.List<Integer> quantities = new java.util.ArrayList<>();

                    // Add items to cart
                    boolean addingItems = true;
                    while (addingItems) {
                        System.out.print("\nEnter item ID to add to cart (or 0 to checkout): ");
                        int id;
                        try {
                            id = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid item ID.");
                            continue;
                        }

                        if (id == 0) {
                            if (cart.isEmpty()) {
                                System.out.println("Cart is empty. Purchase cancelled.");
                            }
                            addingItems = false;
                            continue;
                        }

                        Product item = productService.getProductById(id);
                        if (item == null) {
                            System.out.println("Item not found. Please try again.");
                            continue;
                        }

                        if (item.getQuantity() == 0) {
                            System.out.println("This item is out of stock.");
                            continue;
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
                            continue;
                        }

                        if (qty <= 0) {
                            System.out.println("Quantity must be positive.");
                            continue;
                        }

                        if (qty > item.getQuantity()) {
                            System.out.println("Insufficient stock. Only " + item.getQuantity() + " available.");
                            continue;
                        }

                        cart.add(item);
                        quantities.add(qty);
                        System.out.println("✓ Added to cart: " + qty + "x " + item.getName());
                    }

                    if (cart.isEmpty()) {
                        break;
                    }

                    // Step 2: System calculates total with tax
                    double subtotal = 0.0;
                    System.out.println("\n--- Cart Summary ---");
                    for (int i = 0; i < cart.size(); i++) {
                        Product p = cart.get(i);
                        int q = quantities.get(i);
                        double itemTotal = p.getPrice() * q;
                        subtotal += itemTotal;
                        System.out.printf("%dx %s - %s @ $%.2f = $%.2f%n",
                                q, p.getName(), p.getBrand(), p.getPrice(), itemTotal);
                    }

                    double tax = subtotal * 0.07; // 7% tax
                    double totalWithTax = subtotal + tax;

                    System.out.println("----------------------------");
                    System.out.println("Subtotal: $" + String.format("%.2f", subtotal));
                    System.out.println("Tax (7%): $" + String.format("%.2f", tax));
                    System.out.println("----------------------------");
                    System.out.println("TOTAL: $" + String.format("%.2f", totalWithTax));

                    System.out.print("\nConfirm purchase? (y/n): ");
                    String confirm = sc.nextLine().trim().toLowerCase();
                    if (!confirm.equals("y") && !confirm.equals("yes")) {
                        System.out.println("Purchase cancelled.");
                        break;
                    }

                    // Step 3 & 4: Payment recorded and inventory reduced for each item
                    java.util.List<Sale> completedSales = new java.util.ArrayList<>();
                    boolean allSuccessful = true;

                    for (int i = 0; i < cart.size(); i++) {
                        Product p = cart.get(i);
                        int q = quantities.get(i);
                        boolean ok = productService.purchaseProduct(p.getId(), q);
                        if (ok) {
                            // Get the last sale that was just recorded
                            var allSales = salesService.getAllSales();
                            if (!allSales.isEmpty()) {
                                completedSales.add(allSales.get(allSales.size() - 1));
                            }
                        } else {
                            System.out.println("Failed to process: " + p.getName());
                            allSuccessful = false;
                        }
                    }

                    // Step 5: Generate combined receipt
                    if (!completedSales.isEmpty()) {
                        System.out.println("\n========== RECEIPT ==========");
                        System.out.println("Date: " + java.time.LocalDateTime.now().format(
                                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        System.out.println("----------------------------");

                        double receiptSubtotal = 0.0;
                        for (Sale s : completedSales) {
                            System.out.printf("%dx %s @ $%.2f = $%.2f%n",
                                    s.getQuantity(), s.getProductName(), s.getUnitPrice(), s.getTotalPrice());
                            receiptSubtotal += s.getTotalPrice();
                        }

                        double receiptTax = receiptSubtotal * 0.07;
                        double receiptTotal = receiptSubtotal + receiptTax;

                        System.out.println("----------------------------");
                        System.out.println("Subtotal: $" + String.format("%.2f", receiptSubtotal));
                        System.out.println("Tax (7%): $" + String.format("%.2f", receiptTax));
                        System.out.println("----------------------------");
                        System.out.println("TOTAL: $" + String.format("%.2f", receiptTotal));
                        System.out.println("============================");
                        System.out.println("Payment recorded. Thank you!");
                        System.out.println("Inventory updated.");
                    }

                    if (!allSuccessful) {
                        System.out.println("\nNote: Some items could not be processed.");
                    }
                }

                case 5 -> {
                    System.out.print("Enter item id: ");
                    int id;
                    try {
                        id = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        break;
                    }

                    Product item = productService.getProductById(id);
                    if (item == null) {
                        System.out.println("Item not found.");
                        break;
                    }

                    System.out.println("Current price: $" + item.getPrice());
                    System.out.print("Enter discount percentage: ");
                    int discount;
                    try {
                        discount = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        break;
                    }

                    if (discount < 0) {
                        System.out.println("Discount cannot be negative.");
                        break;
                    }

                    if (discount > 100) {
                        System.out.println("Discount cannot be greater than 100%.");
                        break;
                    }

                    productService.discountProduct(item, discount);
                    System.out.println("New price: $" + item.getPrice());
                }

                case 6 -> {
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

                case 7 -> {
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
