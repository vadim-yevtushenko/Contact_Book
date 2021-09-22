package com.example.contactbook;

import com.example.contactbook.contact.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("/users")
    Call<List<Contact>> getAllContacts();

    @GET("/users/{id}")
    Call<Contact> getContactById(@Path("id") long id);
}
