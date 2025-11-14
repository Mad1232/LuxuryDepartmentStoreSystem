package model;
import java.util.*;

public class Employee {
    private int employeeId;
    private String name;
    private Role role;
    private double salary;
    private boolean active;
    private int storeId; // Store assignment

    public Employee(int employeeId, String name, Role role, double salary, boolean active) {
        this(employeeId, name, role, salary, active, 0); // Default to store 0 (unassigned)
    }

    public Employee(int employeeId, String name, Role role, double salary, boolean active, int storeId) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.salary = salary;
        this.active = active;
        this.storeId = storeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public double getSalary() {
        return salary;
    }

    public boolean isActive() {
        return active;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return employeeId + " | " + name + " | " + role + " | $" + salary + " | Active: " + active + " | Store: " + storeId;
    }

    public String editString() {
        return employeeId + "," + name + "," + role + "," + salary + "," + active + "," + storeId;
    }
}
