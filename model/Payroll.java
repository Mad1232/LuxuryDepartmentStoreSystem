package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Payroll {
    private int payrollId;
    private int employeeId;
    private String employeeName;
    private double amount;
    private String payPeriodStart; // MM/DD/YYYY
    private String payPeriodEnd;   // MM/DD/YYYY
    private String paymentDate;    // MM/DD/YYYY
    private String status; // PENDING, PAID, CANCELLED

    public Payroll(int payrollId, int employeeId, String employeeName, double amount,
                   String payPeriodStart, String payPeriodEnd, String paymentDate, String status) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.amount = amount;
        this.payPeriodStart = payPeriodStart;
        this.payPeriodEnd = payPeriodEnd;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getAmount() {
        return amount;
    }

    public String getPayPeriodStart() {
        return payPeriodStart;
    }

    public String getPayPeriodEnd() {
        return payPeriodEnd;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("PayrollID:%d | Employee:%s (ID:%d) | Amount:$%.2f | Period:%s to %s | PayDate:%s | Status:%s",
                payrollId, employeeName, employeeId, amount, payPeriodStart, payPeriodEnd, paymentDate, status);
    }

    public String toFileString() {
        return payrollId + "," + employeeId + "," + employeeName + "," + amount + "," +
                payPeriodStart + "," + payPeriodEnd + "," + paymentDate + "," + status;
    }
}

