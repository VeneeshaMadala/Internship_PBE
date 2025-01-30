package com.campus_training.abstractcalassAndInterface;

abstract class EmploymentType {
    private int employeeId;
    private String name;
    public EmploymentType(int employeeId, String name) {
        if (employeeId <= 0) throw new IllegalArgumentException("Invalid Employee ID");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid Name");
        this.employeeId = employeeId;
        this.name = name;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public String getName() {
        return name;
    }
    public abstract int calculateWorkHours(int days);
    public void displayEmployeeInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name);
    }
}
interface Payable {
    double calculatePay(int hoursWorked, double ratePerHour);
}
class Contractor extends EmploymentType implements Payable {
    public Contractor(int employeeId, String name) {
        super(employeeId, name);
    }
    @Override
    public int calculateWorkHours(int days) {
        int workHours = days * 8;
        System.out.println("Contractor work hours for " + days + " days: " + workHours);
        return workHours;
    }
    @Override
    public double calculatePay(int hoursWorked, double ratePerHour) {
        double pay = hoursWorked * ratePerHour;
        System.out.println("Contractor payment: $" + pay);
        return pay;
    }
}
class PermanentEmployee extends EmploymentType implements Payable {
    public PermanentEmployee(int employeeId, String name) {
        super(employeeId, name);
    }
    @Override
    public int calculateWorkHours(int days) {
        int workHours = days * 9;
        System.out.println("Permanent employee work hours for " + days + " days: " + workHours);
        return workHours;
    }
    @Override
    public double calculatePay(int hoursWorked, double ratePerHour) {
        double pay = hoursWorked * ratePerHour + 500;
        System.out.println("Permanent employee payment: $" + pay);
        return pay;
    }
}
