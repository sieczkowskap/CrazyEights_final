package edu.ib;

import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Pile {
    private ArrayList<Card> cards;

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

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    @Override
    public void move(int i, Pile o2) {
        o2.getCards().add(getCards().get(i));
        getCards().remove(i);
    }
}
