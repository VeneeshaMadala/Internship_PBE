package com.campus_training.collectionframework;

import java.util.*;


class Employee {
    private int id;
    private String name;


    public Employee(int id, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "'}";
    }
}


interface Payable {
    void processPayroll();
}


class Payroll implements Payable {
    private List<Employee> employees;


    public Payroll() {
        this.employees = new ArrayList<>();
    }


    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        employees.add(employee);
    }





    @Override
    public void processPayroll() {
        if (employees.isEmpty()) {
            System.out.println("No employees to process payroll for.");
        } else {
            System.out.println("Processing payroll for employees:");
            for (Employee employee : employees) {
                System.out.println("Processed payroll for " + employee.getName());
            }
        }
    }


    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
        } else {
            System.out.println("Employees in Payroll:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }
}


class Benefit {
    private Map<Employee, String> benefits;


    public Benefit() {
        this.benefits = new HashMap<>();
    }


    public void addBenefit(Employee employee, String benefit) {
        if (employee == null || benefit == null || benefit.isEmpty()) {
            throw new IllegalArgumentException("Invalid employee or benefit.");
        }
        benefits.put(employee, benefit);
    }


    public void displayBenefits() {
        if (benefits.isEmpty()) {
            System.out.println("No benefits to display.");
        } else {
            System.out.println("Employee Benefits:");
            for (Map.Entry<Employee, String> entry : benefits.entrySet()) {
                System.out.println(entry.getKey().getName() + ": " + entry.getValue());
            }
        }
    }
}


class PayoutQueue {
    private Queue<Employee> payoutQueue;


    public PayoutQueue() {
        this.payoutQueue = new LinkedList<>();
    }


    public void addToQueue(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        payoutQueue.add(employee);
    }


    public void processPayouts() {
        if (payoutQueue.isEmpty()) {
            System.out.println("No payouts to process.");
        } else {
            System.out.println("Processing payouts:");
            while (!payoutQueue.isEmpty()) {
                System.out.println("Paid: " + payoutQueue.poll().getName());
            }
        }
    }
}


