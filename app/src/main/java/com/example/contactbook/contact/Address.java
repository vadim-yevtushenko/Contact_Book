package com.example.contactbook.contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("suite")
    @Expose
    private String suite;


    public Address() {
    }

    public Address(String street, String suite) {
        this.street = street;
        this.suite = suite;

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }


}
