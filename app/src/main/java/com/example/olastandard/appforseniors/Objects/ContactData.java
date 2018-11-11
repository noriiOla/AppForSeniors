package com.example.olastandard.appforseniors.Objects;

import java.io.Serializable;

public class ContactData implements Serializable, Comparable{
    private String nameOfPersion;
    private String numebrOfPerson;

    public ContactData(String numebrOfPerson) {
        this.numebrOfPerson = numebrOfPerson;
        this.nameOfPersion = numebrOfPerson;
    }

    public ContactData(String nameOfPersion, String numebrOfPerson) {
        this.nameOfPersion = nameOfPersion;
        this.numebrOfPerson = numebrOfPerson;
    }

    public String getNumebrOfPerson(){
        return numebrOfPerson;
    }

    public String getNameOfPersion() {
        return nameOfPersion;
    }

    public void setNameOfPerson(String nameOfPersion) {
        this.nameOfPersion = nameOfPersion;
    }

    public void setNumebrOfPerson(String numebrOfPerson){
        this.numebrOfPerson = numebrOfPerson;
    }

    @Override
    public int compareTo(Object o) {
        return this.getNameOfPersion().compareTo(((ContactData) o).getNameOfPersion());
    }

}
