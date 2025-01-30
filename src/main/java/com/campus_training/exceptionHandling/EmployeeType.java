package com.campus_training.exceptionHandling;

abstract class EmployeeType {
    private int employeeId;
    private String name;

    public EmployeeType(int employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        if(employeeId < 0 ) throw new IllegalArgumentException("Invalid input");
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null || name.isEmpty()) throw new IllegalArgumentException("Invalid Name input");
        this.name = name;
    }

    public abstract void calculateWorkHours();

    public void displayInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name);
    }
}
interface Payable{
    double calculatePay();
}
class PayrollComputationException extends Exception{
    public PayrollComputationException(String message, Throwable cause){
        super(message, cause);
    }
}


class Contractor extends EmployeeType implements Payable {
    private int workHour;
    private double hourRate;
    private int workDays;

    public Contractor(int employeeId, String name,int workHour,double hourRate,int workDays) {
        super(employeeId, name);
        this.workHour=workHour;
        this.hourRate=hourRate;
        this.workDays=workDays;
    }

    @Override
    public void calculateWorkHours() {
        if (workHour < 0 && workDays < 0) {
            throw new IllegalArgumentException("Invalid Work time");
        }
        System.out.println("Contractor working hours: " + (workHour*workDays) +" per month");
    }

    @Override
    public double calculatePay() {
        return (workHour*hourRate*workDays);
    }
}

class PermanentEmployee extends EmployeeType implements Payable {
    private int workHour;
    private double hourRate;


    public PermanentEmployee(int employeeId, String name,int workhour, double hourRate) {
        super(employeeId, name);
        this.workHour=workhour;
        this.hourRate=hourRate;
    }

    @Override
    public void calculateWorkHours() {
        if (workHour < 0) {
            throw new IllegalArgumentException("Invalid working hours for Permanent Employee.");
        }
        System.out.println("Permanent Employee working hours: " + workHour+" per month" );
    }

    @Override
    public double calculatePay(){
        return workHour*hourRate;
    }
    public double calculateOvertimePay() throws PayrollComputationException {
        try {
            double overtimePay = workHour > 40 ? (workHour - 40) * (hourRate / 0) : 0;
            return overtimePay;
        } catch (ArithmeticException ex) {
            throw new PayrollComputationException("Computation error as division by zero is invalid", ex);
        }
    }


}

