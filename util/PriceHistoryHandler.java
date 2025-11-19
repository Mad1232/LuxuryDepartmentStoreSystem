/*
    Author: Dylan Rusch
*/

package util;
import model.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Logs when a product's price changes, updates price history file with timestamp and price
public class PriceHistoryHandler {
    private static final String PRICE_HISTORY_FILE = "data/price_history.txt";

    // Log price change in price history document
    public static void logPriceChange(Product product, double oldPrice, double newPrice) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // Formatted price change entry
        String price_change_entry = timestamp +
                " | ID: " + product.getId() +
                " | " + product.getBrand() + " " + product.getName() +
                " | $" + String.format("%.2f", oldPrice) +
                " to $" + String.format("%.2f", newPrice) +
                "\n";

        // Append entry to text file
        try (FileWriter writer = new FileWriter(PRICE_HISTORY_FILE, true)) {
            writer.write(price_change_entry);
        } catch (IOException e) {
            System.out.println("Could not write to price history txt document");
        }
    }

    // Read price history file and print all entries of a specific product id
    public static void printPriceHistoryForProduct(int productId) {
        boolean found = false;

        try (BufferedReader r = new BufferedReader(new FileReader(PRICE_HISTORY_FILE))) {
            String line;
            while ((line = r.readLine()) != null) {

                // Check for id
                if (line.contains("ID: " + productId + " ")) {

                    // Format print without product name and id
                    String[] parts = line.split("\\|");
                    String timestamp = parts[0].trim();
                    String priceChange = parts[3].trim();

                    System.out.println(timestamp + " | " + priceChange);
                    found = true;
                }
            }

        // Error reading price history
        } catch (IOException e) {
            System.out.println("Could not read price history txt file");
            return;
        }
        // No prices changes logged for product
        if (!found) {
            System.out.println("No previous price history found");
        }
    }
}
