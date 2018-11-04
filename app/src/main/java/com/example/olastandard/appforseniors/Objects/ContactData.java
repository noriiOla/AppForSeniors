package com.example.olastandard.appforseniors.Objects;

public class ContactData {
    private String nameOfPersion;
    private String numebrOfPerson;

    public ContactData(String numebrOfPerson) {
        this.numebrOfPerson = numebrOfPerson;
        this.nameOfPersion = null;
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

}
