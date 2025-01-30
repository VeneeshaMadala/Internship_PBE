package com.campus_training.abstractclass;

public class PayrollSystem {
    public static void main(String[] args) { try{
        Role1 r1 = new Role1(20, "Kiran Kulkarni");
        Role2 r2 = new Role2(21, "Ankisha");

        r1.displayInfo();
        r1.defineWorkingHours(8);
        r1.assignProject("Project 1");
        System.out.println();

        r2.displayInfo();
        r2.defineWorkingHours(6);
        r2.assignProject("Project 2");
        System.out.println();
    } catch(IllegalArgumentException e){
        System.out.println(e.getMessage());
    }
    }
}
