package com.example.appforadmin.comparators;


import com.example.appforadmin.contact.Player;

import java.util.Comparator;

public class ComparatorById implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        return player1.getId().compareTo(player2.getId());
    }
}
