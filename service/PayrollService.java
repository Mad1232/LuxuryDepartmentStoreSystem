package service;

import model.Payroll;
import model.Employee;
import util.FileHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PayrollService {
    private static final String FILE_PATH = "data/payroll.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void addPayroll(Payroll payroll) {
        FileHandler.writeLine(FILE_PATH, payroll.toFileString());
    }

    public List<Payroll> getAllPayrolls() {
        List<String> lines = FileHandler.readAllLines(FILE_PATH);
        List<Payroll> payrolls = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 8) {
                try {
                    int id = Integer.parseInt(parts[0]);
                    int empId = Integer.parseInt(parts[1]);
                    String empName = parts[2];
                    double amount = Double.parseDouble(parts[3]);
                    String periodStart = parts[4];
                    String periodEnd = parts[5];
                    String payDate = parts[6];
                    String status = parts[7];
                    payrolls.add(new Payroll(id, empId, empName, amount, periodStart, periodEnd, payDate, status));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid payroll line: " + line);
                }
            }
        }
        return payrolls;
    }

    public int getNextPayrollId() {
        List<Payroll> payrolls = getAllPayrolls();
        if (payrolls.isEmpty()) return 1;
        return payrolls.get(payrolls.size() - 1).getPayrollId() + 1;
    }

    public Payroll getPayrollById(int id) {
        List<Payroll> payrolls = getAllPayrolls();
        for (Payroll payroll : payrolls) {
            if (payroll.getPayrollId() == id) {
                return payroll;
            }
        }
        return null;
    }

    public List<Payroll> getPayrollsByEmployee(int employeeId) {
        List<Payroll> all = getAllPayrolls();
        List<Payroll> result = new ArrayList<>();
        for (Payroll p : all) {
            if (p.getEmployeeId() == employeeId) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Payroll> getPendingPayrolls() {
        List<Payroll> all = getAllPayrolls();
        List<Payroll> pending = new ArrayList<>();
        for (Payroll p : all) {
            if (p.getStatus().equalsIgnoreCase("PENDING")) {
                pending.add(p);
            }
        }
        return pending;
    }

    public void updatePayrollStatus(int payrollId, String newStatus) {
        List<Payroll> payrolls = getAllPayrolls();
        boolean found = false;
        for (int i = 0; i < payrolls.size(); i++) {
            if (payrolls.get(i).getPayrollId() == payrollId) {
                Payroll old = payrolls.get(i);
                Payroll updated = new Payroll(old.getPayrollId(), old.getEmployeeId(), old.getEmployeeName(),
                        old.getAmount(), old.getPayPeriodStart(), old.getPayPeriodEnd(),
                        old.getPaymentDate(), newStatus);
                payrolls.set(i, updated);
                found = true;
                break;
            }
        }
        if (found) {
            List<String> lines = new ArrayList<>();
            for (Payroll p : payrolls) {
                lines.add(p.toFileString());
            }
            FileHandler.writeAllLines(FILE_PATH, lines);
        }
    }

    // Generate bi-weekly payroll for an employee
    public Payroll generateBiWeeklyPayroll(Employee employee, String payPeriodStart, String payPeriodEnd, String paymentDate) {
        int payrollId = getNextPayrollId();
        // Bi-weekly = yearly salary / 26 pay periods
        double biWeeklyAmount = employee.getSalary() / 26.0;
        return new Payroll(payrollId, employee.getEmployeeId(), employee.getName(),
                biWeeklyAmount, payPeriodStart, payPeriodEnd, paymentDate, "PENDING");
    }

    // Generate monthly payroll for an employee
    public Payroll generateMonthlyPayroll(Employee employee, String payPeriodStart, String payPeriodEnd, String paymentDate) {
        int payrollId = getNextPayrollId();
        // Monthly = yearly salary / 12 months
        double monthlyAmount = employee.getSalary() / 12.0;
        return new Payroll(payrollId, employee.getEmployeeId(), employee.getName(),
                monthlyAmount, payPeriodStart, payPeriodEnd, paymentDate, "PENDING");
    }

    public double getTotalPayrollCost() {
        List<Payroll> payrolls = getAllPayrolls();
        double total = 0.0;
        for (Payroll p : payrolls) {
            if (p.getStatus().equalsIgnoreCase("PAID")) {
                total += p.getAmount();
            }
        }
        return total;
    }

    public double getTotalPendingPayroll() {
        List<Payroll> payrolls = getAllPayrolls();
        double total = 0.0;
        for (Payroll p : payrolls) {
            if (p.getStatus().equalsIgnoreCase("PENDING")) {
                total += p.getAmount();
            }
        }
        return total;
    }
}

