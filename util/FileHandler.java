/*
Author: Prakarsha Poudel
 */
package util;

import model.Reservation;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public static void writeLine(String filename, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<String> readAllLines(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }

    public static void writeAllLines(String filename, List<String> lines) {
        try(BufferedWriter w = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                w.write(line);
                w.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<Reservation> readReservations(String filename) {
        List<Reservation> reservations = new ArrayList<>();
        List<String> lines = readAllLines(filename);
        for(String line : lines) {
            String[] parts = line.split(",");
            if(parts.length == 5) {
                int id = Integer.parseInt(parts[0]);
                int productId = Integer.parseInt(parts[1]);
                String customerName = parts[2];
                String dateReserved = parts[3];
                boolean fulfilled = Boolean.parseBoolean(parts[4]);

                Reservation r = new Reservation(id, productId, customerName, dateReserved);
                if(fulfilled) r.markFulfilled();
                reservations.add(r);
            }
        }
        return reservations;
    }

    public static void writeReservations(String filename, List<Reservation> reservations) {
        List<String> lines = new ArrayList<>();
        for(Reservation r : reservations) {
            lines.add(r.toString());
        }
        writeAllLines(filename, lines);
    }
}
