package edu.ib;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Simulates full card deck.
 * Implements interface Pile
 */
public class Deck implements Pile {

    private ArrayList<Card> cards;


    /**
     * Default constructor.
     * Creates every card from deck and puts them into cards list
     */
    public Deck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 2; i < 11; i++) {
            deck.add(new Card("Hearts", Integer.toString(i)));
            deck.add(new Card("Clubs", Integer.toString(i)));
            deck.add(new Card("Spades", Integer.toString(i)));
            deck.add(new Card("Diamonds", Integer.toString(i)));
        }
        deck.add(new Card("Hearts", "J"));
        deck.add(new Card("Hearts", "Q"));
        deck.add(new Card("Hearts", "K"));
        deck.add(new Card("Hearts", "A"));
        deck.add(new Card("Clubs", "J"));
        deck.add(new Card("Clubs", "Q"));
        deck.add(new Card("Clubs", "K"));
        deck.add(new Card("Clubs", "A"));
        deck.add(new Card("Spades", "J"));
        deck.add(new Card("Spades", "Q"));
        deck.add(new Card("Spades", "K"));
        deck.add(new Card("Spades", "A"));
        deck.add(new Card("Diamonds", "J"));
        deck.add(new Card("Diamonds", "Q"));
        deck.add(new Card("Diamonds", "K"));
        deck.add(new Card("Diamonds", "A"));
        this.cards = deck;
    }

    /**
     * @return list of cards in the deck
     */
    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Shuffles cards in deck.
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
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
