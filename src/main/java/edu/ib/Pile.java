package edu.ib;

import java.util.ArrayList;

public interface Pile {
    ArrayList<Card> getCards();

    void move(int i, Pile o2);
}
