package com.campus_training.abstractcalassAndInterface;

public class PayrollSystem {
    public static void main(String[] args) {
        try {
            Contractor contractor = new Contractor(101, "John Doe");
            contractor.displayEmployeeInfo();
            int contractorHours = contractor.calculateWorkHours(5);
            contractor.calculatePay(contractorHours, 25.0);
            System.out.println();
            PermanentEmployee permanentEmployee = new PermanentEmployee(102, "Jane Smith");
            permanentEmployee.displayEmployeeInfo();
            int permanentHours = permanentEmployee.calculateWorkHours(5);
            permanentEmployee.calculatePay(permanentHours, 20.0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}