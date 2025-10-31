/*
Author: Generated
*/
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sale {
    private static final double TAX_RATE = 0.07; // 7% sales tax
    private int saleId;
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice; // Before tax (for revenue tracking)
    private double taxAmount;
    private double totalWithTax;
    private String timestamp;

    public Sale(int saleId, int productId, String productName, int quantity, double unitPrice) {
        this.saleId = saleId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
        this.taxAmount = this.totalPrice * TAX_RATE;
        this.totalWithTax = this.totalPrice + this.taxAmount;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public int getSaleId() { return saleId; }
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public double getTaxAmount() { return taxAmount; }
    public double getTotalWithTax() { return totalWithTax; }
    public String getTimestamp() { return timestamp; }

    // Generate a formatted receipt for this sale
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("\n========== RECEIPT ==========\n");
        receipt.append("Sale ID: ").append(saleId).append("\n");
        receipt.append("Date: ").append(timestamp).append("\n");
        receipt.append("----------------------------\n");
        receipt.append("Product: ").append(productName).append("\n");
        receipt.append("Product ID: ").append(productId).append("\n");
        receipt.append("Unit Price: $").append(String.format("%.2f", unitPrice)).append("\n");
        receipt.append("Quantity: ").append(quantity).append("\n");
        receipt.append("----------------------------\n");
        receipt.append("Subtotal: $").append(String.format("%.2f", totalPrice)).append("\n");
        receipt.append("Tax (7%): $").append(String.format("%.2f", taxAmount)).append("\n");
        receipt.append("----------------------------\n");
        receipt.append("TOTAL: $").append(String.format("%.2f", totalWithTax)).append("\n");
        receipt.append("============================\n");
        receipt.append("Payment recorded. Thank you!\n");
        receipt.append("Inventory updated.\n");
        return receipt.toString();
    }

    @Override
    public String toString() {
        return saleId + "," + productId + "," + productName + "," + quantity + "," + unitPrice + "," + totalPrice + "," + taxAmount + "," + totalWithTax + "," + timestamp;
    }

    public static Sale fromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 9) return null;
        try {
            int sid = Integer.parseInt(parts[0]);
            int pid = Integer.parseInt(parts[1]);
            String pname = parts[2];
            int qty = Integer.parseInt(parts[3]);
            double up = Double.parseDouble(parts[4]);
            // parts[5] is totalPrice, parts[6] is taxAmount, parts[7] is totalWithTax
            String ts = parts[8];
            Sale s = new Sale(sid, pid, pname, qty, up);
            s.timestamp = ts;
            return s;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

