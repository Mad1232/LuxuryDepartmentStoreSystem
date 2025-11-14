/*
Author: Generated
*/
package service;

import model.Store;
import model.Employee;
import util.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class StoreService {
    private static final String STORE_FILE_PATH = "data/stores.txt";
    private EmployeeService employeeService;

    public StoreService() {
        this.employeeService = new EmployeeService();
    }

    // Add a new store
    public void addStore(Store store) {
        String line = store.editString();
        FileHandler.writeLine(STORE_FILE_PATH, line);
    }

    // Get all stores
    public List<Store> getAllStores() {
        List<String> lines = FileHandler.readAllLines(STORE_FILE_PATH);
        List<Store> stores = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                try {
                    int storeId = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String location = parts[2];
                    boolean active = Boolean.parseBoolean(parts[3]);
                    Store store = new Store(storeId, name, location, active);

                    // Load manager if specified
                    if (parts.length > 4 && !parts[4].isEmpty()) {
                        int managerId = Integer.parseInt(parts[4]);
                        Employee manager = employeeService.getEmployeeById(managerId);
                        if (manager != null) {
                            store.setManager(manager);
                        }
                    }

                    // Load all employees for this store
                    List<Employee> allEmployees = employeeService.getAllEmployees();
                    for (Employee emp : allEmployees) {
                        // Only add employees assigned to this store
                        if (emp.getStoreId() == storeId) {
                            store.addEmployee(emp);
                        }
                    }

                    stores.add(store);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid store line: " + line);
                }
            }
        }
        return stores;
    }

    // Get store by ID
    public Store getStoreById(int storeId) {
        List<Store> stores = getAllStores();
        for (Store store : stores) {
            if (store.getStoreId() == storeId) {
                return store;
            }
        }
        return null;
    }

    // Get next store ID
    public int getNextStoreId() {
        List<Store> stores = getAllStores();
        if (stores.isEmpty()) return 1;
        return stores.get(stores.size() - 1).getStoreId() + 1;
    }

    // Update store
    public void editStore(Store store) {
        List<Store> stores = getAllStores();
        int replace = -1;
        for(int i = 0; i < stores.size(); i++){
            if(stores.get(i).getStoreId() == store.getStoreId()){
                replace = i;
                break;
            }
        }
        if (replace != -1) {
            stores.set(replace, store);
            List<String> lines = new ArrayList<>();
            for (Store s : stores) {
                lines.add(s.editString());
            }
            FileHandler.writeAllLines(STORE_FILE_PATH, lines);
        }
    }

    // Employee management passthrough methods
    public void addEmployee(Employee e) {
        employeeService.addEmployee(e);
    }

    public void editEmployee(Employee e) {
        employeeService.editEmployee(e);
    }

    public boolean removeEmployeeById(int id) {
        return employeeService.removeEmployeeById(id);
    }

    public Employee getEmployeeById(int id) {
        return employeeService.getEmployeeById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public int getNextEmployeeId() {
        return employeeService.getNextEmployeeId();
    }

    public void assignManagerToStore(int storeId, int managerId) {
        Store s = getStoreById(storeId);
        if (s == null) return;
        s.setManager(employeeService.getEmployeeById(managerId));
        editStore(s); // reuse existing persistence method to save the change
    }
}

