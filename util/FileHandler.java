/*
Author: Prakarsha Poudel
 */
package util;

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
}
