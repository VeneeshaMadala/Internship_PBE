package com.campus_training.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface PayrollAdjustment {
    String adjustmentType();
}

class PayrollProcessor {

    public double calculateBasicSalary(Double hoursWorked, Double hourlyRate) {
        if (hoursWorked == null || hourlyRate == null || hoursWorked <= 0 || hourlyRate <= 0) {
            throw new IllegalArgumentException("Invalid hours worked or hourly rate");
        }
        return hoursWorked * hourlyRate;
    }

    @PayrollAdjustment(adjustmentType = "Tax")
    public double applyTax(Double salary) {
        if (salary == null || salary <= 0) {
            throw new IllegalArgumentException("Salary cannot be null or zero");
        }
        double taxRate = 0.2;
        return salary - (salary * taxRate);
    }

    @PayrollAdjustment(adjustmentType = "Overtime")
    public double applyOvertime(Double salary, Double overtimeHours, Double overtimeRate) {
        if (salary == null || overtimeHours == null || overtimeRate == null || overtimeHours < 0 || overtimeRate < 0) {
            throw new IllegalArgumentException("Invalid overtime values");
        }
        return salary + (overtimeHours * overtimeRate);
    }

    public double calculateFinalPayroll(Double hoursWorked, Double hourlyRate, Double overtimeHours, Double overtimeRate) {
        double basicSalary = calculateBasicSalary(hoursWorked, hourlyRate);
        basicSalary = applyTax(basicSalary);
        return applyOvertime(basicSalary, overtimeHours, overtimeRate);
    }
}

class PayrollAdjustmentProcessor {

    public static double processPayrollAdjustment(Object object, String adjustmentType, Double salary) throws Exception {
        if (salary == null || salary <= 0) {
            throw new IllegalArgumentException("Salary cannot be null or zero");
        }

        Method[] methods = object.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(PayrollAdjustment.class)) {
                PayrollAdjustment payrollAdjustment = method.getAnnotation(PayrollAdjustment.class);

                if (payrollAdjustment.adjustmentType().equals(adjustmentType)) {
                    method.setAccessible(true);
                    if (adjustmentType.equals("Tax")) {
                        return (double) method.invoke(object, salary);
                    } else if (adjustmentType.equals("Overtime")) {
                        return (double) method.invoke(object, salary, 10, 25);
                    }
                }
            }
        }
        return salary;
    }
}

