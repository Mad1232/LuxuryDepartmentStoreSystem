package model;

public class StoreInventoryItem {
    private int id;
    private int productId;   // reference to Product.id (catalog)
    private int storeId;     // reference to Store.id
    private int quantity;
    private Double priceOverride; // nullable: if set, overrides product price for this store

    public StoreInventoryItem(int id, int productId, int storeId, int quantity, Double priceOverride) {
        this.id = id;
        this.productId = productId;
        this.storeId = storeId;
        this.quantity = quantity;
        this.priceOverride = priceOverride;
    }

    // getters/setters
    public int getId() { return id; }
    public int getProductId() { return productId; }
    public int getStoreId() { return storeId; }
    public int getQuantity() { return quantity; }
    public Double getPriceOverride() { return priceOverride; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPriceOverride(Double priceOverride) { this.priceOverride = priceOverride; }

    @Override
    public String toString() {
        return String.format("InvID:%d | ProductID:%d | StoreID:%d | Qty:%d | PriceOverride:%s",
                id, productId, storeId, quantity, priceOverride == null ? "none" : String.format("$%.2f", priceOverride));
    }
}