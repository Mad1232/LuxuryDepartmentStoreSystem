package model;

public class Reservation {
    private int id;
    private int productId;
    private String customerName;
    private String dateReserved;
    private boolean fulfilled;

    public Reservation(int id, int productId, String customerName, String dateReserved) {
        this.id = id;
        this.productId = productId;
        this.customerName = customerName;
        this.dateReserved = dateReserved;
        this.fulfilled = false;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public String getCustomerName() { return customerName; }
    public boolean isFulfilled() { return fulfilled; }

    public void markFulfilled() { this.fulfilled = true; }

    @Override
    public String toString() {
        return id + "," + productId + "," + customerName + "," + dateReserved + "," + fulfilled;
    }
}