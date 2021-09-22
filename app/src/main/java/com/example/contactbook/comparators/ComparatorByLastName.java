package com.example.contactbook.comparators;

import com.example.contactbook.contact.Contact;

import java.util.Comparator;

public class ComparatorByLastName implements Comparator<Contact> {
    @Override
    public int compare(Contact contact1, Contact contact2) {
        return contact1.getUserName().compareTo(contact2.getUserName());
    }
}
