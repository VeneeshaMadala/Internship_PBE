package com.campus_training.searchingandsorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PayrollManagement {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(101, "Alice", "HR", 70000),
                new Employee(102, "Bob", "IT", 90000),
                new Employee(103, "Charlie", "Finance", 85000),
                new Employee(104, "Diana", "IT", 75000)
        );
        Collections.sort(employees);
        System.out.println("Employees sorted by name:");
        employees.forEach(System.out::println);
        employees.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
        System.out.println("\nEmployees sorted by salary (descending):");
        employees.forEach(System.out::println);
        System.out.println("\nEmployees in IT department:");
        employees.stream()
                .filter(e -> e.getDepartment().equals("IT"))
                .forEach(System.out::println);
        Employee highestEarner = employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
        System.out.println("\nHighest earner:");
        System.out.println(highestEarner);
    }
}
