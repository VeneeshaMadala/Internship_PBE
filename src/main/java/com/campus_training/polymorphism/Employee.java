package com.campus_training.polymorphism;

class Employee {
    private int employeeId;
    private String name;
    private double baseSalary;

    public Employee(int employeeId, String name, double baseSalary) {
        this.employeeId = employeeId;
        this.name = name;
        if(baseSalary >= 0) {
            this.baseSalary = baseSalary;
        } else {
            System.out.println("Invalid");
        }
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        if (employeeId < 0) System.out.println("Invalid");
        else this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Invalid");
        else this.name = name;
    }



    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        if (baseSalary >= 0) this.baseSalary = baseSalary;
        else throw new IllegalArgumentException("Invalid");
    }


    public void displayEmployeeInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name);
        System.out.println("Base Salary: " + baseSalary);
    }

    public double calculateSalary() {
        return baseSalary;
    }
}

class FullTimeEmployee extends Employee {
    private double annualBonus;

    public FullTimeEmployee(int employeeId, String name, double baseSalary, double annualBonus) {
        super(employeeId, name, baseSalary);
        if(annualBonus >= 0) {
            this.annualBonus = annualBonus;
        } else {
            throw new IllegalArgumentException("Invalid");
        }
    }

    public double getAnnualBonus() {
        return annualBonus;
    }

    public void setAnnualBonus(double annualBonus) {
        if(annualBonus >= 0) {
            this.annualBonus = annualBonus;
        } else {
            throw new IllegalArgumentException("Invalid");
        }
    }

    @Override
    public void displayEmployeeInfo() {
        super.displayEmployeeInfo();
        System.out.println("Annual Bonus: " + annualBonus);
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + annualBonus;
    }
}
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int employeeId, String name, double baseSalary, int hoursWorked, double hourlyRate) {
        super(employeeId, name, baseSalary);
        if(hoursWorked >= 0) {
            this.hoursWorked = hoursWorked;
        } else {
            throw new IllegalArgumentException("Invalid");
        }
        if(hourlyRate >= 0) {
            this.hourlyRate = hourlyRate;
        } else {
            throw new IllegalArgumentException("Invalid");
        }
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        if(hoursWorked >= 0) {
            this.hoursWorked = hoursWorked;
        } else {
            throw new IllegalArgumentException("Invalid");
        }
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if(hourlyRate >= 0) {
            this.hourlyRate = hourlyRate;
        } else {
            throw new IllegalArgumentException("Invalid");
        }
    }

    @Override
    public void displayEmployeeInfo() {
        super.displayEmployeeInfo();
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Hourly Rate: " + hourlyRate);
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + (hoursWorked * hourlyRate);
    }
}



