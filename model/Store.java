package model;
import java.util.*;

public class Store {
    private int storeId;
    private String name;
    private String location;
    private boolean active;
    private Employee manager;
    private List<Employee> employees = new ArrayList<>();


    public Store(){}

    public Store(int storeId, String name, String location, boolean active){
        this.storeId = storeId;
        this.name = name;
        this.location = location;
        this.active = active;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId){
        this.storeId = storeId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active){
        this.active = active;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }


    public void addEmployee(Employee e){
        if (e != null && !employees.contains(e)){
            employees.add(e);
        }
    }

    public boolean removeEmployee(Employee e){
        return employees.remove(e);
    }

    public List<Employee> getEmployees(){
        return Collections.unmodifiableList(employees);
    }

    @Override
    public String toString() {
        String managerName = manager != null ? manager.getName() : "None";
        return storeId + " | " + name + " | " + location + " | Active: " + active + " | Manager: " + managerName + " | Employees: " + employees.size();
    }

    public String editString() {
        int managerId = manager != null ? manager.getEmployeeId() : 0;
        return storeId + "," + name + "," + location + "," + active + "," + managerId;
    }
}
