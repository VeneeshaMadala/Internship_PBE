package com.campus_training.collectionframework;

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Employee e1 = new Employee(101, "Kiran");
        Employee e2 = new Employee(102, "Veneesha");
        Employee e3 = new Employee(103, "Ankisha");



        Payroll payroll = new Payroll();
        payroll.addEmployee(e1);
        payroll.addEmployee(e2);
        payroll.displayEmployees();
        payroll.processPayroll();

        payroll.displayEmployees();



        Benefit benefit = new Benefit();
        benefit.addBenefit(e1, "Health Insurance");
        benefit.addBenefit(e3, "Stock Options");
        benefit.displayBenefits();



        PayoutQueue payoutQueue = new PayoutQueue();
        payoutQueue.addToQueue(e1);
        payoutQueue.addToQueue(e3);
        payoutQueue.processPayouts();
    }
}

