package com.example.samplecrud;

public class User {
    public String name, age, email, address, father;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getFather() {
        return father;
    }

    public User(String name, String age, String email, String address, String father) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.father = father;
    }

    public User() {
    }
}
