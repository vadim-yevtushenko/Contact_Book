package com.example.appforadmin;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.example.appforadmin.contact.Player;
import com.example.appforadmin.enums.Colors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentContact extends Fragment {

    private long id;
    private EditText etName;
    private EditText etUntilNextLevel;
    private EditText etTitle;
    private EditText etRace;
    private EditText etProfession;
    private EditText etBirthday;
    private EditText etExperience;
    private EditText etLevel;
    private Button bSave;
    private Button bDelete;
    private Button bCreate;
    private Button bBack;
    private Api api;
    private Player player;
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
        bCreate.setOnClickListener(this::create);
        bBack.setOnClickListener(this::back);
        return view;
    }

    private void back(View view) {
        activity.setId(0);
        activity.setListFragment();
    }

    private void create(View view) {
        player.setId(null);
        player.setName(etName.getText().toString());
        player.setTitle(etTitle.getText().toString());
        player.setExperience(Integer.parseInt(etExperience.getText().toString()));
        player.setLevel(Integer.parseInt(etLevel.getText().toString()));
        player.setUntilNextLevel(Integer.parseInt(etUntilNextLevel.getText().toString()));
        api.createPlayer(player).enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                back(view);
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Toast.makeText(activity, "Error connection", Toast.LENGTH_SHORT).show();
                back(view);
            }
        });

    }

    public void downloadContact(long id) {

        api.getPlayerById(id).enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {

                player = response.body();

                if (player != null) {
                    etName.setText(player.getName());
                    etTitle.setText(player.getTitle());
                    etRace.setText(player.getRace().toString());
                    etProfession.setText(player.getProfession().toString());
                    etBirthday.setText(String.valueOf(player.getBirthday()));
                    etExperience.setText(String.valueOf(player.getExperience()));
                    etLevel.setText(String.valueOf(player.getLevel()));
                    etUntilNextLevel.setText(String.valueOf(player.getUntilNextLevel()));

                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.d("log", t.toString());
                Toast.makeText(activity, "Error connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void delete(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity).
                setTitle("Do you want delete this contact?").
                setMessage("Contact will be deleted!").
                setPositiveButton("YES", (dialog, which) -> {
                    api.deletePlayer(id).enqueue(new Callback<Player>() {
                        @Override
                        public void onResponse(Call<Player> call, Response<Player> response) {
                            Toast.makeText(activity, "player was deleted", Toast.LENGTH_SHORT).show();
                            activity.setListFragment();
                        }

                        @Override
                        public void onFailure(Call<Player> call, Throwable t) {
                            Toast.makeText(activity, "Error connection", Toast.LENGTH_SHORT).show();
                            activity.setListFragment();
                        }
                    });
                }).
                setNeutralButton("CANCEL", (dialog, which) -> {
                });
        alertDialog.show();
    }

    private void save(View view) {
        player.setName(etName.getText().toString());
        api.updatePlayer(id, player).enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                player = response.body();
                if (player != null) {
                    etName.setText(player.getName());
                    etTitle.setText(player.getTitle());
//                    etRace.setText(player.getRace().toString());
//                    etProfession.setText(player.getProfession().toString());
//                    etBirthday.setText(String.valueOf(player.getBirthday().getTime()));
                    etExperience.setText(String.valueOf(player.getExperience()));
                    etLevel.setText(String.valueOf(player.getLevel()));
                    etUntilNextLevel.setText(String.valueOf(player.getUntilNextLevel()));
                }

            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Toast.makeText(activity, "Error connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViews(View view) {
        etName = view.findViewById(R.id.etName);
        etTitle = view.findViewById(R.id.etTitle);
        etRace = view.findViewById(R.id.etRace);
        etProfession = view.findViewById(R.id.etProfession);
        etBirthday = view.findViewById(R.id.etBirthday);
        etExperience = view.findViewById(R.id.etExperience);
        etLevel = view.findViewById(R.id.etLevel);
        etUntilNextLevel = view.findViewById(R.id.etUntilNextLevel);
        bSave = view.findViewById(R.id.bSave);
        bDelete = view.findViewById(R.id.bDelete);
        bCreate = view.findViewById(R.id.bCreate);
        bBack = view.findViewById(R.id.bBack);
    }
}