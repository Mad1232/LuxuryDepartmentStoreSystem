package service;

import model.Feedback;
import util.FileHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService {
    private static final String FEEDBACK_FILE = "data/feedback.txt";
    private final FileHandler fileHandler = new FileHandler();

    public void addFeedback(Feedback feedback) {
        List<String> lines = fileHandler.readAllLines(FEEDBACK_FILE);
        lines.add(feedback.toString());
        fileHandler.writeAllLines(FEEDBACK_FILE, lines);
    }

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        List<String> lines = fileHandler.readAllLines(FEEDBACK_FILE);

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",", 8);
            if (parts.length < 8) continue;
            feedbacks.add(new Feedback(
                    Integer.parseInt(parts[0].trim()),
                    parts[1].trim(),
                    Integer.parseInt(parts[2].trim()),
                    Integer.parseInt(parts[3].trim()),
                    Boolean.parseBoolean(parts[4].trim()),
                    parts[5].trim(),
                    parts[6].trim(),
                    parts[7].trim()
            ));
        }
        return feedbacks;
    }

    public void addFeedbackFromUser(int productId, String name, int productRating, int experienceRating,
                                    boolean wouldRecommend, String comment, String store) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
        Feedback feedback = new Feedback(productId, name, productRating, experienceRating, wouldRecommend, comment, store, timestamp);
        addFeedback(feedback);
    }

    public void printFeedbackForProduct(int productId) {
        List<Feedback> feedbacks = getAllFeedbacks();
        boolean found = false;
        for (Feedback fb : feedbacks) {
            if (fb.getProductId() == productId) {
                System.out.println(fb.displayString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No feedback found for this product.");
        }
    }
}
