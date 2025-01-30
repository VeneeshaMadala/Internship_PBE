package com.campus_training.objectandclass;

//object and class
    public class Employee {
    private int employeeId;
    private String name;
    private String department;
    private double baseSalary;

    public Employee(int employeeId, String name, String department, double baseSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        if (employeeId < 0) System.out.println("Invalid");
        else this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid");
        else this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.isEmpty()) throw new IllegalArgumentException("Invalid");
        else this.department = department;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        if (baseSalary >= 0) this.baseSalary = baseSalary;
        else throw new IllegalArgumentException("Invalid");
    }

    public void displayInfoEmployee() {
        System.out.println("EmployeeId : " + employeeId);
        System.out.println("Name : " + name);
        System.out.println("Department : " + department);
        System.out.println("Base Salary : " + baseSalary);
    }

    public static void main(String[] args) {
        try {
            Employee emp = new Employee(1, "ven", "CSE", 20000);
            emp.displayInfoEmployee();
            emp.setBaseSalary(-100);
            emp.displayInfoEmployee();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}