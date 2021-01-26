package edu.ib;

import java.util.ArrayList;

public class Table implements Pile {
    private ArrayList<Card> cards;

    public Table() {
        ArrayList cards = new ArrayList<>();
        this.cards = cards;
    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public void move(int i, Pile o2) {
        o2.getCards().add(getCards().get(i));
        getCards().remove(i);
    }
}
