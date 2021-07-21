package com.example.contactbook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.contactbook.comparators.ComparatorByLastName;
import com.example.contactbook.comparators.ComparatorByName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lvContacts;
    private Spinner spSort;
    private ArrayAdapter<String> spinnerAdapter;
    private ContactAdapter contactAdapter;
    private ArrayList<Map<String, String>> contacts;

    public static final String KEY_FIRST_NAME = "first name";
    public static final String KEY_SECOND_NAME = "second name";
    public static final String KEY_AVATAR = "avatar";
    private final String SORT_BY_NAME = "Name and Lastname";
    private final String SORT_BY_LASTNAME = "Lastname and name";

    public static int contactKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContacts = findViewById(R.id.lvContacts);
        spSort = findViewById(R.id.spSort);
        contacts = new ArrayList<>();

        String[] comparators = {SORT_BY_NAME, SORT_BY_LASTNAME};
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, comparators);
        spSort.setAdapter(spinnerAdapter);
        loadViewListAdapter();
        initListeners();
    }

    private void fillContactsList() {
        contacts = new ArrayList<>();
        Map<String, String> data;
        for (Contact contact : DataBase.contacts) {
            data = new HashMap<>();
            data.put(KEY_FIRST_NAME, contact.getFirstName());
            data.put(KEY_SECOND_NAME, contact.getLastName());
            data.put(KEY_AVATAR, String.valueOf(contact.getAvatar()));
            contacts.add(data);
        }
    }

    private void initListeners() {
        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            contactKey = position;
            Intent intent = new Intent(this, ContactActivity.class);
            intent.putExtra(String.valueOf(position), position);
            startActivity(intent);
        });

        lvContacts.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).
                    setTitle("Do you want delete this contact?").
                    setMessage("Contact will be deleted!").
                    setPositiveButton("YES", (dialog, which) -> {
                        DataBase.contacts.remove(position);
                        loadViewListAdapter();
                    }).
                    setNeutralButton("CANCEL", (dialog, which) -> {
                    });
            alertDialog.show();
            return true;
        });
        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadViewListAdapter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void loadViewListAdapter() {
        switch (spSort.getSelectedItem().toString()){
            case SORT_BY_NAME:{
                DataBase.compareByName();
                fillContactsList();
                contactAdapter = new ContactAdapter(this,
                        contacts,
                        R.layout.my_item,
                        new String[]{KEY_FIRST_NAME, KEY_SECOND_NAME},
                        new int[]{android.R.id.text1, android.R.id.text2});
                contactAdapter.setFirstCharKey(KEY_FIRST_NAME);

                lvContacts.setAdapter(contactAdapter);
                break;
            }
            case SORT_BY_LASTNAME:{
                DataBase.compareByLastName();
                fillContactsList();
                contactAdapter = new ContactAdapter(this,
                        contacts,
                        R.layout.my_item,
                        new String[]{KEY_SECOND_NAME,KEY_FIRST_NAME},
                        new int[]{android.R.id.text1, android.R.id.text2});
                contactAdapter.setFirstCharKey(KEY_SECOND_NAME);

                lvContacts.setAdapter(contactAdapter);
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        loadViewListAdapter();
        super.onResume();
    }
}