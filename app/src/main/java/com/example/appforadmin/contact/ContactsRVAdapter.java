package com.example.appforadmin.contact;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appforadmin.CircleTextView;
import com.example.appforadmin.enums.Colors;
import com.example.appforadmin.FragmentContactsList;
import com.example.appforadmin.R;

import java.util.ArrayList;


public class ContactsRVAdapter extends RecyclerView.Adapter<ContactsRVAdapter.ContactRecyclerViewHolder> {

    private Context context;
    private ArrayList<Player> players;
    private Player player;
    private int count;
    private FragmentContactsList.DataPassListener dataPassListener;

    public ContactsRVAdapter(ArrayList<Player> players) {

        this.players = players;
    }

    @NonNull
    @Override
    public ContactRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        dataPassListener = (FragmentContactsList.DataPassListener) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_item, parent, false);
        ContactRecyclerViewHolder viewHolder = new ContactRecyclerViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecyclerViewHolder holder, int position) {
        holder.tvName.setText(players.get(position).getName());
        holder.tvUserName.setText(players.get(position).getTitle());
        holder.tvAvatar.setText(getFirstChar(players.get(position).getName()));
        holder.tvAvatar.setColorBackground(Color.parseColor(getColor()));
        holder.constraintLayout.setOnClickListener(v -> {
            dataPassListener.passData(players.get(position).getId());
        });
//        holder.constraintLayout.setOnLongClickListener(v -> {
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).
//                    setTitle("Do you want delete this contact?").
//                    setMessage("Contact will be deleted!").
//                    setPositiveButton("YES", (dialog, which) -> {
//                        DataBase.contacts.remove(position);
//                        sortListContacts();
//                    }).
//                    setNeutralButton("CANCEL", (dialog, which) -> {
//                    });
//            alertDialog.show();
//            return true;
//        });

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class ContactRecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvUserName;
        private CircleTextView tvAvatar;
        private ConstraintLayout constraintLayout;

        public ContactRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvUserName = itemView.findViewById(R.id.tvId);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
            constraintLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    private String getFirstChar(String name) {
        return String.valueOf(name.charAt(0));
    }

    private String getColor() {

        if (count == Colors.values().length){
            count = 0;
        }
        return Colors.values()[count++].getColor();
    }
}
