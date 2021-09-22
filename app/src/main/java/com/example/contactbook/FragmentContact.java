package com.example.contactbook;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.contactbook.contact.Contact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentContact extends Fragment {

    private long id;
    private EditText etName;
    private CircleTextView tvAvatar;
    private EditText etUserName;
    private EditText etPhoneNumber;
    private EditText eteMail;
    private EditText etStreet;
    private EditText etSuite;
    private EditText etCompany;
    private ImageView ivAvatar;
    private Button bSave;
    private Button bDelete;
    private Api api;
    private Contact contact;
    private MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        api = NetworkService.getInstance().getJsonApi();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (MainActivity)getContext();
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        if (getArguments() != null) {
            id = getArguments().getLong(MainActivity.KEY_ID);
        }

        initViews(view);
        downloadContact(id);
        bSave.setOnClickListener(this::save);
        bDelete.setOnClickListener(this::delete);
        return view;
    }

    public void downloadContact(long id) {
        final Contact[] contactConst = {null};
        if (api == null) {
            Log.d("log", "downloadContactnull " + id);
        }
        api.getContactById(id).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                contactConst[0] = response.body();
                contact = contactConst[0];

                if (contact != null) {
                    etName.setText(contact.getName());
                    etUserName.setText(contact.getUserName());
                    etPhoneNumber.setText(contact.getPhoneNumber());
                    eteMail.setText(contact.getEmail());
                    etStreet.setText(contact.getAddress().getStreet());
                    etSuite.setText(contact.getAddress().getSuite());
                    etCompany.setText(contact.getCompany().getName());

                    tvAvatar.setText(getFirstChar());
                    tvAvatar.setColorBackground(Color.parseColor(getRandomColor()));
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Toast.makeText(activity, "Error connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getRandomColor() {
        int random = (int) (Math.random() * Colors.values().length);
        return Colors.values()[random].getColor();
    }

    private void delete(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity).
                setTitle("Do you want delete this contact?").
                setMessage("Contact will be deleted!").
                setPositiveButton("YES", (dialog, which) -> {
//                    DataBase.contacts.remove(id);

                }).
                setNeutralButton("CANCEL", (dialog, which) -> {
                });
        alertDialog.show();
    }

    private void save(View view) {
        activity.setId(0);
        activity.setListFragment();
    }

    private String getFirstChar() {
        return String.valueOf(contact.getName().charAt(0));
    }

    private void initViews(View view) {
        etName = view.findViewById(R.id.etName);
        etUserName = view.findViewById(R.id.etUserName);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        eteMail = view.findViewById(R.id.eteMail);
        etStreet = view.findViewById(R.id.etStreet);
        etSuite = view.findViewById(R.id.etSuite);
        etCompany = view.findViewById(R.id.etCompany);
        tvAvatar = view.findViewById(R.id.tvAvatar);
        bSave = view.findViewById(R.id.bSave);
        bDelete = view.findViewById(R.id.bDelete);
        ivAvatar = view.findViewById(R.id.ivAvatar);
    }
}