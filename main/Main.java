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
            case 2 -> {
                System.out.println("Available Items:");
                productService.getAllProducts().forEach(System.out::println);
            }
            default -> System.out.println("Invalid option");
        }

        sc.close();
    }
}
