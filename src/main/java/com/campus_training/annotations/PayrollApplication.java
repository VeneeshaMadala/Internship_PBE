package com.campus_training.annotations;

public class PayrollApplication {

    public static void main(String[] args) throws Exception {
        PayrollProcessor payrollProcessor = new PayrollProcessor();

        Double hoursWorked = 160.0;
        Double hourlyRate = 25.0;
        Double overtimeHours = 20.0;
        Double overtimeRate = 37.5;

        try {
            double basicSalary = payrollProcessor.calculateBasicSalary(hoursWorked, hourlyRate);
            double finalSalary = payrollProcessor.calculateFinalPayroll(hoursWorked, hourlyRate, overtimeHours, overtimeRate);

            System.out.println("Initial Salary (without adjustments): " + basicSalary);
            System.out.println("Final Salary (after applying tax and overtime): " + finalSalary);

            double adjustedSalary = PayrollAdjustmentProcessor.processPayrollAdjustment(payrollProcessor, "Tax", basicSalary);
            adjustedSalary = PayrollAdjustmentProcessor.processPayrollAdjustment(payrollProcessor, "Overtime", adjustedSalary);

            System.out.println("Adjusted Salary after manual tax and overtime adjustments: " + adjustedSalary);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}