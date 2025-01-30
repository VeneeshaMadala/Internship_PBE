package com.campus_training.encapsulatingSensitiveData;

public class Main{
    public static void main(String[] args){
        try{
            Employee emp=new Employee("Veneesha",100000,50000);
            emp.displayInfo();;
            emp.setBonus(6000);
            emp.displayInfo();;
            emp.setBaseSalary(-12);
            emp.displayInfo();;
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}