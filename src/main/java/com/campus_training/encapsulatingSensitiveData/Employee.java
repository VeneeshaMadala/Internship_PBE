package com.campus_training.encapsulatingSensitiveData;

class Employee {
    private String name;
    private double baseSalary;
    private double bonus;
    Employee(String name, double basesalary, double bonus){
        this.name=name;
        this.baseSalary=basesalary;
        this.bonus=bonus;
    }
    public String getName(){
        return name;
    }
    public double getBaseSalary(){
        return baseSalary;
    }
    public double getBonus(){
        return bonus;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setBaseSalary(double baseSalary){
        if(baseSalary>=0) this.baseSalary=baseSalary;
        else throw new IllegalArgumentException("Invalid salary value.");
    }
    public void setBonus(double bonus){
        if(bonus>=0) this.bonus=bonus;
        else throw new IllegalArgumentException("Invalid bonus value.");
    }
    public void displayInfo(){
        System.out.println("Name: "+name);
        System.out.println("Base Salary: "+baseSalary);
        System.out.println("Bonus: "+bonus);
        System.out.println("Total earned: "+(baseSalary+bonus));
    }
}
