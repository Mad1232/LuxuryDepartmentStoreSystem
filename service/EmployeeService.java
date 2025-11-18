/*
Author: Generated
*/
package service;

import model.Employee;
import model.Role;
import util.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private static final String FILE_PATH = "data/employees.txt";

    public void addEmployee(Employee employee) {
        String line = employee.getEmployeeId() + "," + employee.getName() + "," +
                employee.getRole() + "," + employee.getSalary() + "," +
                employee.isActive() + "," + employee.getStoreId();
        FileHandler.writeLine(FILE_PATH, line);
    }

    public void editEmployee(Employee employee) {
        List<Employee> employees = getAllEmployees();
        int replace = -1;
        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i).getEmployeeId() == employee.getEmployeeId()){
                replace = i;
                break;
            }
        }
        if (replace != -1) {
            employees.set(replace, employee);
            FileHandler.writeAllLines(FILE_PATH, employees.stream().map(Employee::editString).toList());
        }
    }


    public List<Employee> getAllEmployees() {
        List<String> lines = FileHandler.readAllLines(FILE_PATH);
        List<Employee> employees = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                try {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    Role role = Role.fromString(parts[2]);
                    double salary = Double.parseDouble(parts[3]);
                    boolean active = Boolean.parseBoolean(parts[4]);
                    int storeId = parts.length > 5 ? Integer.parseInt(parts[5]) : 0;
                    employees.add(new Employee(id, name, role, salary, active, storeId));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        }
        return employees;
    }

    public int getNextEmployeeId() {
        List<Employee> employees = getAllEmployees();
        if (employees.isEmpty()) return 1;
        return employees.get(employees.size() - 1).getEmployeeId() + 1;
    }

    public Employee getEmployeeById(int id) {
        List<Employee> employees = getAllEmployees();
        for (Employee employee : employees) {
            if (employee.getEmployeeId() == id) {
                return employee;
            }
        }
        return null;
    }

    public boolean removeEmployeeById(int id) {
        List<Employee> employees = getAllEmployees();
        boolean removed = employees.removeIf(emp -> emp.getEmployeeId() == id);
        if (removed) {
            FileHandler.writeAllLines(FILE_PATH, employees.stream().map(Employee::editString).toList());
        }
        return removed;
    }
}

