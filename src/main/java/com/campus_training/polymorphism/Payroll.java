package com.campus_training.polymorphism;

public class Payroll {
    public static void main(String[] args) { try{
        FullTimeEmployee FTemp = new FullTimeEmployee(20, "Kiran Kulkarni", 25000, 5000);
        System.out.println("Full Time Employee Details:");
        FTemp.displayEmployeeInfo();
        System.out.println("Full Time Employee Salary: " + FTemp.calculateSalary());
        System.out.println();

        PartTimeEmployee PTemp = new PartTimeEmployee(21, "John Doe", 15000, 500, 20);
        System.out.println("Part Time Employee Details:");
        PTemp.displayEmployeeInfo();
        System.out.println("Part Time Employee Salary: " + PTemp.calculateSalary());
    } catch ( IllegalArgumentException e){
        System.out.println(e.getMessage());
    }
    }
}