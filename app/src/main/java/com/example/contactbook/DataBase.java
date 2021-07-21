package com.example.contactbook;

import android.os.Build;

import com.example.contactbook.comparators.ComparatorByLastName;
import com.example.contactbook.comparators.ComparatorByName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;

public class DataBase {
    public static ArrayList<Contact> contacts;

    static {
        contacts = new ArrayList<>();

        contacts.add(new Contact("Ivan", "Ivanov", "ivan@gmail.com", "+38(067)565-98-45", 0));
        contacts.add(new Contact("Semen", "Sidorov", "sid@m.ua", "+38(095)735-45-25", 0));
        contacts.add(new Contact("Ivan", "Petrov", "pet@m.com", "+38(066)958-16-75", 0));
        contacts.add(new Contact("Petr", "Petrov", "ptrov@m.com", "+38(050)335-43-54", 0));
        contacts.add(new Contact("Petr", "Sidorov", "pes@m.ua", "+38(093)153-28-41", 0));
        contacts.add(new Contact("Vadim", "Yevtushenko", "vadik@gmail.com", "+38(066)957-50-05", R.drawable.ava));

        contacts.add(new Contact("Aleksei", "Pertsukh", "", "+38(093)153-28-41", 0));
        contacts.add(new Contact("Daniil", "Laureate", "", "+38(093)153-28-41", 0));
        contacts.add(new Contact("Anton", "Antoniuk", "", "+38(093)153-28-41", 0));
        contacts.add(new Contact("Evgenii", "Narozhniy", "", "+38(093)153-28-41", 0));
    }

    public static void compareByName(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contacts.sort(new ComparatorByName().thenComparing(new ComparatorByLastName()));
            Collections.sort(contacts, new ComparatorByName().thenComparing(new ComparatorByLastName()));
        }
    }

    public static void compareByLastName(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contacts.sort(new ComparatorByLastName().thenComparing(new ComparatorByName()));
            Collections.sort(contacts, new ComparatorByLastName().thenComparing(new ComparatorByName()));
        }
    }
}
