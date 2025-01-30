package com.campus_training.genrics;

import java.util.*;

public class NewGenericMain {
    public static void main(String[] args) {
        List<String> employees = Arrays.asList("Kiran", "Veneesha", "Ankshika", "Diya");
        List<Double> salary = Arrays.asList(110000.00, 95000.00, 25000.00, 30000.00);
        Set<Integer> employeeId = new HashSet<>(Arrays.asList(101, 102, 103, 104));

        Pair<Integer, String> pair = new Pair<>(101, "Employee");
        DataProcessor.processData(pair.getKey().toString(), pair.getValue(), salary.get(0));
        Pair<String, String> pair1 = new Pair<>("Veneesha", "Employee");
        DataProcessor.processData(pair1.getKey(), pair1.getValue(), salary.get(1));
        pair = new Pair<>(103, "Intern");
        DataProcessor.processData(pair.getKey().toString(), pair.getValue(), salary.get(2));
        pair1 = new Pair<>("Diya", "Intern");
        DataProcessor.processData(pair1.getKey(), pair1.getValue(), salary.get(3));
        CollectionProcessor.processCollection(employeeId);
        CollectionProcessor.processCollection(employees);
        CollectionProcessor.processCollection(salary);
    }
}

class CollectionProcessor {
    public static <W> void processCollection(Collection<W> collection) {
        if (collection == null || collection.isEmpty()) {
            System.out.println("Whe collection is empty or null.");
            return;
        }
        for (W item : collection) {
            System.out.println("Processing item: " + item);
        }
    }
}

class DataProcessor {
    public static <W> void processData(W key, String status, Double baseSalary) {
        if (key == null || status == null) {
            System.out.println("Invalid input data: Key or Status is null.");
            return;
        }
        double salary = baseSalary;
        switch (status) {
            case "Employee":
                salary = calculateSalaryWithBonusesAndDeductions(salary, 0.10, 0.05);
                System.out.println(key + " (Employee) Pay: " + salary);
                break;
            case "Intern":
                salary = calculateSalaryWithBonusesAndDeductions(salary, 0.05, 0.02);
                System.out.println(key + " (Intern) Pay: " + salary);
                break;
            default:
                System.out.println(key + " has an unknown position.");
        }
    }

    private static double calculateSalaryWithBonusesAndDeductions(double baseSalary, double bonusPercentage, double deductionPercentage) {
        double bonus = baseSalary * bonusPercentage;
        double deduction = baseSalary * deductionPercentage;
        return baseSalary + bonus - deduction;
    }
}

class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

