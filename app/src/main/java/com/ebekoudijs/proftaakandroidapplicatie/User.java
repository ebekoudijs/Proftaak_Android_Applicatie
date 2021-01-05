package com.ebekoudijs.proftaakandroidapplicatie;

public class User {

    public String Username;
    public String Password;
    public String Email;
    public int Age;
    public String Gender;

    public User(String username, String password, String email, int age, String gender){
        Username = username;
        Password = password;
        Email = email;
        Age = age;
        Gender = gender;
    }

    public User(String username, String password){
        Username = username;
        Password = password;
    }
}
