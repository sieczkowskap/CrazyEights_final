package edu.ib;

import java.util.ArrayList;

/**
 * Simulates pile of played cards on the table.
 * Implements interface Pile.
 */
public class Table implements Pile {
    private ArrayList<Card> cards;

    /**
     * Default constructor.
     * Creates list of cards in the pile.
     */
    public Table() {
        ArrayList cards = new ArrayList<>();
        this.cards = cards;
    }

    /**
     * @return list of cards on the table
     */
    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Removes card from one pile and adds this card to another pile.
     * @param i  position in pile of card which should be moved
     * @param o2 the pile to which the cards will be moved
     */
    @Override
    public void move(int i, Pile o2) {
        o2.getCards().add(getCards().get(i));
        getCards().remove(i);
    }
}
