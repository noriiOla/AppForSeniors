package com.example.olastandard.appforseniors.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonSmsData implements Serializable {

    private ContactData contactData;
    private List<Sms> listOfSms;

    public PersonSmsData(String numebrOfPerson) {
        this.contactData = new ContactData(numebrOfPerson);
        this.listOfSms = new ArrayList<Sms>();
    }

    public String getNumebrOfPerson(){
        return this.contactData.getNumebrOfPerson();
    }

    public String getNameOfPersion() {
        return this.contactData.getNameOfPersion();
    }

    public List<Sms> getListOfSms() {
        return listOfSms;
    }

    public void setNameOfPersion(String nameOfPersion) {
        this.contactData.setNameOfPerson(nameOfPersion);
    }

    public void addNewSmsToList(Sms singleSms) {
        this.listOfSms.add(singleSms);
    }

    public void setNumebrOfPerson(String numebrOfPerson){
        this.contactData.setNumebrOfPerson(numebrOfPerson);
    }

    public void reverseListOfSms() {
        Collections.reverse(this.listOfSms);
    }
}
