package com.example.appforadmin;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appforadmin.comparators.ComparatorByName;
import com.example.appforadmin.comparators.ComparatorById;
import com.example.appforadmin.contact.Player;
import com.example.appforadmin.contact.ContactsRVAdapter;

import java.net.HttpURLConnection;
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
    private ArrayList<Player> players;
    private Display display;
    private Api api;
    private MainActivity activity;


    private final String SORT_BY_NAME = "Name";
    private final String SORT_BY_ID = "Id";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        display = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        initViews(view);
        api = NetworkService.getInstance().getJsonApi();

        String[] comparators = {SORT_BY_ID, SORT_BY_NAME};
        spinnerAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, comparators);
        spSort.setAdapter(spinnerAdapter);
        spSort.setSelection(activity.getSpinnerItemPosition());
        initListeners();
        Log.d("log", "start");
        downloadAndCreateRecyclerList();
        return view;
    }

    private void downloadAndCreateRecyclerList() {

        api.getAllPlayers().enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {

                players = (ArrayList<Player>) response.body();
                sortListContacts();
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Log.d("log", "onFailure " + t.toString());
                Toast.makeText(activity, "Error connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createRecyclerList(ArrayList<Player> players) {
        contactsRVAdapter = new ContactsRVAdapter(players);
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
                if (players != null) {
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
                    players.sort(new ComparatorByName());
                    Collections.sort(players, new ComparatorByName());
                }
                createRecyclerList(players);
                break;
            }
            case SORT_BY_ID: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    players.sort(new ComparatorById());
                    Collections.sort(players, new ComparatorById());
                }
                createRecyclerList(players);
                break;
            }
        }
    }

    public interface DataPassListener {
        void passData(Long data);
    }
}