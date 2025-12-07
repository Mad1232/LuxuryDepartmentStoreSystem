package model;

public class Reservation {
    private int id;
    private int productId;
    private String customerName;
    private String dateReserved;

    public Reservation(int id, int productId, String customerName, String dateReserved) {
        this.id = id;
        this.productId = productId;
        this.customerName = customerName;
        this.dateReserved = dateReserved;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public String getCustomerName() { return customerName; }


    @Override
    public String toString() {
        return id + "," + productId + "," + customerName + "," + dateReserved;
    }
}