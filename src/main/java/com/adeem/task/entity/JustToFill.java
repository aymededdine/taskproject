package com.adeem.task.entity;

public class JustToFill {
    // Fields
    private String name;
    private int age;

    // Constructors
    public JustToFill(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            System.out.println("Age cannot be negative.");
        }
    }

    public void celebrateBirthday() {
        System.out.println(name + " is celebrating their birthday!");
        age++;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Creating an instance of the Person class
        JustToFill person1 = new JustToFill("John Doe", 30);

        // Accessing fields and methods
        System.out.println("Name: " + person1.getName());
        System.out.println("Age: " + person1.getAge());

        person1.celebrateBirthday();
        System.out.println("New age after birthday: " + person1.getAge());

        // Modifying the age directly (not recommended, use setter)
        person1.age = -5; // This will not be allowed due to private access modifier
    }
}

