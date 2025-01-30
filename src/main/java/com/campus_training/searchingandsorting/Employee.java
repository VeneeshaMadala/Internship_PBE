package com.campus_training.searchingandsorting;

class Employee implements Comparable<Employee> {
    private int employeeId;
    private String name;
    private String department;
    private double salary;
    public Employee(int employeeId, String name, String department, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public String getName() {
        return name;
    }
    public String getDepartment() {
        return department;
    }
    public double getSalary() {
        return salary;
    }
    @Override
    public int compareTo(Employee other) {
        return this.name.compareTo(other.name);
    }
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Dept: %s, Salary: %.2f",
                employeeId, name, department, salary);
    }
}
