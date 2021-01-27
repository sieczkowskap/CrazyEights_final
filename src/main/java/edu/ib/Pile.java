package edu.ib;

import java.util.ArrayList;

/**
 * Interface for card piles
 */
public interface Pile {

    /**
     * Abstract method used to return cards in other classes
     */
    ArrayList<Card> getCards();

    /**
     * Moves card from one pile to another
     * @param i  position in pile of card which should be moved
     * @param o2 the pile to which the cards will be moved
     */
    void move(int i, Pile o2);
}
