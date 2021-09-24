package com.example.contactbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements FragmentContactsList.DataPassListener {

    private FragmentContactsList fragmentContactsList;
    private FragmentContact fragmentContact;
    private Display display;
    public static final String KEY_ID = "id";
    public static final String KEY_ITEM_POSITION = "spinner";
    private long id;
    private int spinnerItemPosition;
    private boolean onExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        fragmentContact = new FragmentContact();
        fragmentContactsList = new FragmentContactsList();
        if (savedInstanceState != null) {
            spinnerItemPosition = savedInstanceState.getInt(KEY_ITEM_POSITION, 0);
            if (isLandscapeOrientation()) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listFragment, fragmentContactsList)
                        .commit();
            }
            id = savedInstanceState.getLong(KEY_ID, 0);
            if (id != 0) {
                passData(id);
            }
        }
        setListFragment();
    }

    private boolean isLandscapeOrientation() {
        int rotation = display.getRotation();
        return rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270;
    }

    public void setListFragment() {
        if (!isLandscapeOrientation() && id == 0) {
            onExit = true;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragmentContactsList)
                    .commit();
        }
    }

    @Override
    public void passData(Long data) {
        id = data;
        if (isLandscapeOrientation()) {
            onExit = true;
            if (data == 0) {
                fragmentContact = new FragmentContact();
            } else {
                fragmentContact = (FragmentContact) getSupportFragmentManager()
                        .findFragmentById(R.id.infoFragment);
            }
            fragmentContact.downloadContact(data);
        } else {
            onExit = false;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragmentContact)
                    .commit();
            Bundle bundle = new Bundle();
            bundle.putLong(KEY_ID, data);
            fragmentContact.setArguments(bundle);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(KEY_ID, id);
        outState.putInt(KEY_ITEM_POSITION, spinnerItemPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (onExit) {
            finish();
        } else {
            id = 0;
            setListFragment();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSpinnerItemPosition() {
        return spinnerItemPosition;
    }

    public void setSpinnerItemPosition(int spinnerItemPosition) {
        this.spinnerItemPosition = spinnerItemPosition;
    }
}