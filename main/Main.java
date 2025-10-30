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
            case 2 -> {
                System.out.println("Available Items:");
                productService.getAllProducts().forEach(System.out::println);
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

        sc.close();
    }
}
