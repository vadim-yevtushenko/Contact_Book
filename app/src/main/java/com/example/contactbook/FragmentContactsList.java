package com.example.contactbook;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.contactbook.comparators.ComparatorByName;
import com.example.contactbook.comparators.ComparatorByUserName;
import com.example.contactbook.contact.Contact;
import com.example.contactbook.contact.ContactsRVAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentContactsList extends Fragment {

    private Spinner spSort;
    private ArrayAdapter<String> spinnerAdapter;

    private RecyclerView rvContacts;
    private ContactsRVAdapter contactsRVAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Contact> contacts;
    private Display display;
    private Api api;
    private MainActivity activity;


    private final String SORT_BY_NAME = "Name";
    private final String SORT_BY_USERNAME = "Username";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        display = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        initViews(view);
        api = NetworkService.getInstance().getJsonApi();

        String[] comparators = {SORT_BY_NAME, SORT_BY_USERNAME};
        spinnerAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, comparators);
        spSort.setAdapter(spinnerAdapter);
        spSort.setSelection(activity.getSpinnerItemPosition());
        initListeners();
        downloadAndCreateRecyclerList();
        return view;
    }

    private void downloadAndCreateRecyclerList() {

        api.getAllContacts().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                contacts = (ArrayList<Contact>) response.body();
                sortListContacts();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Toast.makeText(activity, "Error connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createRecyclerList(ArrayList<Contact> contacts) {
        contactsRVAdapter = new ContactsRVAdapter(contacts);
        linearLayoutManager = new LinearLayoutManager(activity);
        rvContacts.setLayoutManager(linearLayoutManager);

        rvContacts.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

        rvContacts.setAdapter(contactsRVAdapter);
    }

    private void initViews(View view) {
        spSort = view.findViewById(R.id.spSort);
        rvContacts = view.findViewById(R.id.rvContacts);
    }


    private void initListeners() {
        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activity.setSpinnerItemPosition(position);
                if (contacts != null) {
                    sortListContacts();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sortListContacts() {
        switch (spSort.getSelectedItem().toString()) {
            case SORT_BY_NAME: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    contacts.sort(new ComparatorByName());
                    Collections.sort(contacts, new ComparatorByName());
                }
                createRecyclerList(contacts);
                break;
            }
            case SORT_BY_USERNAME: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    contacts.sort(new ComparatorByUserName());
                    Collections.sort(contacts, new ComparatorByUserName());
                }
                createRecyclerList(contacts);
                break;
            }
        }
    }

    public interface DataPassListener {
        void passData(Long data);
    }
}