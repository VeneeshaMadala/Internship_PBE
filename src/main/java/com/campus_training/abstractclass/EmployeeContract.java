package com.campus_training.abstractclass;

abstract class EmployeeContract {
    private int employeeId;
    private String name;

    public EmployeeContract(int employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    public int getEmployeeId() {

        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        if(employeeId<0) throw new IllegalArgumentException("Invalid");
        else this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null || name.isEmpty()) throw new IllegalArgumentException("Invalid");
        else this.name = name;
    }

    public abstract void defineWorkingHours(int workingHours);
    public abstract void assignProject(String projectName);

    public void displayInfo() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name);
    }
}

class Role1 extends EmployeeContract {
    private int workHour;
    private String project;

    public Role1(int employeeId, String name) {
        super(employeeId, name);
    }

    @Override
    public void defineWorkingHours(int workingHour) {
        if (workingHour < 0) {
            throw new IllegalArgumentException("Invalid");

        }
        this.workHour = workingHour;
        System.out.println("Role 1 working hours: " + workHour + " hrs per day");
    }

    @Override
    public void assignProject(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid");
        }
        this.project = projectName;
        System.out.println("Role 1 assigned to project: " + project);
    }
}

class Role2 extends EmployeeContract {
    private int workHour;
    private String project;

    public Role2(int employeeId, String name) {
        super(employeeId, name);
    }

    @Override
    public void defineWorkingHours(int workingHour) {
        if (workingHour < 0) {
            throw new IllegalArgumentException("Invalid");
        }
        this.workHour = workingHour;
        System.out.println("Role 2 working hours: " + workHour + " hrs per day");
    }

    @Override
    public void assignProject(String projectName) {
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid");
        }
        this.project = projectName;
        System.out.println("Role 2 assigned to project: " + project);
    }
}



