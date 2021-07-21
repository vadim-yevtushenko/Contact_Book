package com.example.contactbook.comparators;

import com.example.contactbook.Contact;

import java.util.Comparator;

public class ComparatorByLastName implements Comparator<Contact> {
    @Override
    public int compare(Contact contact1, Contact contact2) {
        return contact1.getLastName().compareTo(contact2.getLastName());
    }
}
