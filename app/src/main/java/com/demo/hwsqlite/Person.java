package com.demo.hwsqlite;

import androidx.annotation.NonNull;

public class Person {

    private int id;
    private String firstName;
    private String secondName;

    public Person(int id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @NonNull
    @Override
    public String toString() {
        return secondName;
    }
}
