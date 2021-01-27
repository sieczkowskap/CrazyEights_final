package edu.ib;



import java.util.ArrayList;

/**
 * Abstract class to describe players.
 * Implements interface Pile
 */
public abstract class Player implements Pile {
    private ArrayList<Card> cards = new ArrayList<>();
    private int n;

    /**
     * @return the number of cards in player's hand
     */
    public int getN() {
        n = cards.size();
        return n;
    }
    /**
     * @return list of cards in player's hand
     */
    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Removes card from one pile and adds this card to another pile
     * @param i  position in pile of card which should be moved
     * @param o2 the pile to which the cards will be moved
     */
    @Override
    public void move(int i, Pile o2) {
        o2.getCards().add(getCards().get(i));
        getCards().remove(i);
    }

    /**
     * @param real information whether player is human or computer
     * @return information whether player is human or computer
     */
    public boolean isReal(boolean real) {
        return real;
    }


    /**
     * Player's turn.
     * @param card card to play
     * @return played card
     */
    public Card turn(Card card){
        return card;
    }

    /**
     * Abstract boolean to check whether player is human or computer.
     */
    public abstract boolean isReal();

    /**
     * Abstract method for players turn.
     * @param rank rank of the card which player tries to play
     * @param suit suit of the card which player tries to play
     * @param table top card on table pile
     * @param currentSuit current suit set in the game
     */
    public abstract Card turn(String rank, String suit, Table table, String currentSuit);
}
