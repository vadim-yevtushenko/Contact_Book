package com.example.contactbook.comparators;

import com.example.contactbook.Contact;

import java.util.Comparator;

public class ComparatorByName implements Comparator<Contact> {
    @Override
    public int compare(Contact contact1, Contact contact2) {
        return contact1.getFirstName().compareTo(contact2.getFirstName());
    }
}
