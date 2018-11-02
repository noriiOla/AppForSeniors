package com.example.olastandard.appforseniors.Objects;

import java.util.ArrayList;
import java.util.List;

public class PersonSmsData {

    private String nameOfPersion;
    private String numebrOfPerson;
    private List<Sms> listOfSms;

    public PersonSmsData(String numebrOfPerson) {
        this.numebrOfPerson = numebrOfPerson;
        this.nameOfPersion = null;
        this.listOfSms = new ArrayList<Sms>();
    }

    public String getNumebrOfPerson(){
        return numebrOfPerson;
    }

    public String getNameOfPersion() {
        return nameOfPersion;
    }

    public List<Sms> getListOfSms() {
        return listOfSms;
    }

    public void setNameOfPersion(String nameOfPersion) {
        this.nameOfPersion = nameOfPersion;
    }

    public void addNewSmsToList(Sms singleSms) {
        this.listOfSms.add(singleSms);
    }

    public void setNumebrOfPerson(String numebrOfPerson){
        this.numebrOfPerson = numebrOfPerson;
    }
}
