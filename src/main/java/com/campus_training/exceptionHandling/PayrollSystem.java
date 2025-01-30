package com.campus_training.exceptionHandling;

public class PayrollSystem {
    public static void main(String[] args) {
        try{
            Contractor con = new Contractor(20, "Kiran Kulkarni",8,100,20);
            PermanentEmployee perm = new PermanentEmployee(21, "Ankisha",100,120);

            con.displayInfo();
            con.calculateWorkHours();
            System.out.println("Pay: "+con.calculatePay());
            perm.displayInfo();
            perm.calculateWorkHours();
            System.out.println("Pay: "+perm.calculatePay());


        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        try {
            PermanentEmployee perm1 = new PermanentEmployee(21, "Diya", 100, 120);
            System.out.println("Overtime: " + perm1.calculateOvertimePay());
        } catch (PayrollComputationException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
