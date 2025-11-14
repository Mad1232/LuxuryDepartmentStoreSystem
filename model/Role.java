package model;

public enum Role {
    CASHIER,
    MANAGER,
    ADMIN,
    STOCKER,
    CUSTOMER_SERVICE,
    TEAM_LEAD;

    public static Role fromString(String s){
        if (s == null || s.isEmpty()) return null;
        try {
            return Role.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
