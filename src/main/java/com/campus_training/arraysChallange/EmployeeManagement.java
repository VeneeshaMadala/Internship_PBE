package com.campus_training.arraysChallange;
import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeManagement {
    static class EmployeeArray {
        private String[] employees;
        private int count;

        public EmployeeArray(int capacity) {
            employees = new String[capacity];
            count = 0;
        }

        public void addEmployee(String employeeName) {
            if(employeeName == null || employeeName.isEmpty()) return;
            if (count == employees.length) {
                System.out.println("Array is full. Cannot add more employees.");
                return;
            }
            employees[count++] = employeeName;
        }

        public void removeEmployee(String employeeName) {
            if(employeeName == null || employeeName.isEmpty()) return;
            for (int i = 0; i < count; i++) {
                if (employees[i].equals(employeeName)) {
                    for (int j = i; j < count - 1; j++) {
                        employees[j] = employees[j + 1];
                    }
                    employees[--count] = null;
                    return;
                }
            }
            System.out.println("Employee not found.");
        }

        public void displayEmployees() {
            System.out.println("Array Employees: " + Arrays.toString(Arrays.copyOf(employees, count)));
        }
    }


    static class EmployeeArrayList {
        private ArrayList<String> employees;

        public EmployeeArrayList() {
            employees = new ArrayList<>();
        }

        public void addEmployee(String employeeName) {
            if(employeeName == null || employeeName.isEmpty()) return;
            employees.add(employeeName);
        }

        public void removeEmployee(String employeeName) {
            if(employeeName == null || employeeName.isEmpty()) return;
            if (!employees.remove(employeeName) ) {
                System.out.println("Employee not found.");
            }
        }

        public void displayEmployees() {
            System.out.println("ArrayList Employees: " + employees);
        }
    }

    public static void main(String[] args) {
        System.out.println("Using Array:");
        EmployeeArray empArray = new EmployeeArray(3);
        empArray.addEmployee("V");
        empArray.addEmployee("JK");
        empArray.addEmployee("RM");
        empArray.displayEmployees();

        empArray.removeEmployee("RM");
        empArray.displayEmployees();

        empArray.addEmployee("JH");
        empArray.displayEmployees();

        System.out.println("\nUsing ArrayList:");
        EmployeeArrayList empList = new EmployeeArrayList();
        empList.addEmployee("V");
        empList.addEmployee("JK");
        empList.addEmployee("RM");
        empList.displayEmployees();

        empList.removeEmployee("V");
        empList.displayEmployees();

        empList.addEmployee("TH");
        empList.addEmployee("YG");
        empList.displayEmployees();
    }
}

