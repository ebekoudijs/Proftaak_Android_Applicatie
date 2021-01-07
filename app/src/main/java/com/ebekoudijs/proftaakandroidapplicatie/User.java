package com.ebekoudijs.proftaakandroidapplicatie;

public class User {

    public String password;
    public String email;
    public int age;
    public String gender;
    public String name;

    public User(String name, String password, String email, int age, String gender){
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(){

    }

}
