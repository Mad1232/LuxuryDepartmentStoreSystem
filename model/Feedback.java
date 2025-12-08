package model;

public class Feedback {
    private int productId;
    private String customerName;
    private int productRating;         // 1–5 stars
    private int experienceRating;      // 1–5 stars (store/checkout experience)
    private boolean wouldRecommend;    // yes/no
    private String comment;
    private String storeLocation;
    private String timestamp;          // includes date + time

    public Feedback(int productId, String customerName, int productRating, int experienceRating,
                    boolean wouldRecommend, String comment, String storeLocation, String timestamp) {
        this.productId = productId;
        this.customerName = customerName;
        this.productRating = productRating;
        this.experienceRating = experienceRating;
        this.wouldRecommend = wouldRecommend;
        this.comment = comment;
        this.storeLocation = storeLocation;
        this.timestamp = timestamp;
    }

    public int getProductId() {
        return productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getProductRating() {
        return productRating;
    }

    public int getExperienceRating() {
        return experienceRating;
    }

    public boolean isWouldRecommend() {
        return wouldRecommend;
    }

    public String getComment() {
        return comment;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return productId + "," + customerName + "," + productRating + "," + experienceRating + ","
                + wouldRecommend + "," + comment + "," + storeLocation + "," + timestamp;
    }

    public String displayString() {
        return String.format(
                "\nProduct ID: %d\nCustomer: %s\nProduct Rating: %d/5\nExperience Rating: %d/5\nRecommended: %s\nStore: %s\nDate: %s\nComment: %s\n",
                productId, customerName, productRating, experienceRating,
                wouldRecommend ? "Yes" : "No", storeLocation, timestamp, comment
        );
    }
}
