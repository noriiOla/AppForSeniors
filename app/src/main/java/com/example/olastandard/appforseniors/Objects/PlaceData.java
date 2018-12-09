package com.example.olastandard.appforseniors.Objects;

import java.io.Serializable;

public class PlaceData implements Serializable, Comparable{
    String title;
    String address;

    public PlaceData(String title, String address) {
        this.title = title;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(Object o) {
        return this.getTitle().compareTo(((PlaceData) o).getTitle());
    }
}
