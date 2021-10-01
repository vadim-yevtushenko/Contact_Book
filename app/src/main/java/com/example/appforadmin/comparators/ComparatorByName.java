package com.example.appforadmin.comparators;



import com.example.appforadmin.contact.Player;

import java.util.Comparator;

public class ComparatorByName implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        return player1.getName().compareTo(player2.getName());
    }
}
