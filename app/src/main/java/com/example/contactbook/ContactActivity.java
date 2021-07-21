package com.example.contactbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {
    private int index;
    private EditText etName;
    private CircleTextView tvAvatar;
    private EditText etLastName;
    private EditText etPhoneNumber;
    private EditText eteMail;
    private ImageView ivAvatar;
    private Button bSave;
    private Button bDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        if (intent != null){
            index = (int) intent.getIntExtra(String.valueOf(MainActivity.contactKey), 0);
        }

        initViews();
        etName.setText(DataBase.contacts.get(index).getFirstName());
        etLastName.setText(DataBase.contacts.get(index).getLastName());
        etPhoneNumber.setText(DataBase.contacts.get(index).getPhoneNumber());
        eteMail.setText(DataBase.contacts.get(index).geteMail());
        if (DataBase.contacts.get(index).getAvatar() == 0) {
            tvAvatar.setText(getFirstChar());
            tvAvatar.setColorBackground(Color.parseColor(getRandomColor()));
        } else {
            tvAvatar.setVisibility(TextView.INVISIBLE);
            ivAvatar.setImageResource(DataBase.contacts.get(index).getAvatar());

        }
        bSave.setOnClickListener(this::save);
        bDelete.setOnClickListener(this::delete);
    }

    private String getRandomColor() {
        int random = (int) (Math.random()*Colors.values().length);
        return Colors.values()[random].getColor();
    }

    private void delete(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).
                setTitle("Do you want delete this contact?").
                setMessage("Contact will be deleted!").
                setPositiveButton("YES", (dialog, which) -> {
                    DataBase.contacts.remove(index);
                    finish();
                }).
                setNeutralButton("CANCEL", (dialog, which) -> {
                });
        alertDialog.show();
    }

    private void save(View view) {
        DataBase.contacts.get(index).setFirstName(etName.getText().toString());
        DataBase.contacts.get(index).setLastName(etLastName.getText().toString());
        DataBase.contacts.get(index).setPhoneNumber(etPhoneNumber.getText().toString());
        DataBase.contacts.get(index).seteMail(eteMail.getText().toString());
        finish();
    }

    private String getFirstChar() {
        return String.valueOf(DataBase.contacts.get(index).getFirstName().charAt(0));
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        eteMail = findViewById(R.id.eteMail);
        tvAvatar = findViewById(R.id.tvAvatar);
        bSave = findViewById(R.id.bSave);
        bDelete = findViewById(R.id.bDelete);
        ivAvatar = findViewById(R.id.ivAvatar);
    }
}