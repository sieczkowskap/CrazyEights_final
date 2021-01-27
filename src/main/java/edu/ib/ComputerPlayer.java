package edu.ib;


import java.util.ArrayList;

/**
 * Simulates computer player.
 * Extends abstract class Player
 */
public class ComputerPlayer extends Player {
    boolean real = false;

    /**
     * @return information whether player is human or computer
     */
    @Override
    public boolean isReal() {
        return super.isReal(real);
    }


    /**
     * Computer player turn.
     * @param rank rank of the card which computer player tries to play
     * @param suit suit of the card which computer player tries to play
     * @param table top card on table pile
     * @param currentSuit current suit set in the game
     * @return played card
     */
    @Override
    public Card turn(String rank, String suit, Table table, String currentSuit) {
        Card card = new Card();

        for (int j = 0; j < getN(); j++) {

            if (getCards().get(j).getRank().equals(rank) && getCards().get(j).getSuit().equals(suit)) { //sprawdzenie czy karta jest na ręce

                // modyfikacja: if czy karty komputera = liczba 8 + 1, jeśli tak, to zawsze wyrzuca 8-kę, niezależnie,
                // czy karta (która nie jest 8) pasuje czy nie - obligatoryjne wyrzucenie 8-ki
                if (getCards().get(j).getSuit().equals(currentSuit)
                        || getCards().get(j).getRank().equals(table.getCards().get(table.getCards().size() - 1).getRank())
                        || (getCards().get(j).getRank().equals("8") && getN() != 1)) { // sprawdzenie czy karta pasuje do karty na stole
                    // liczba kart != 1, komputer może wyrzucić 8-kę
                    card = getCards().get(j);
                } else if (getCards().get(j).getRank().equals("8") && getN() == 1) {

                }
            }
        }


        return super.turn(card);
    }

    /**
     * Implements strategy for choosing color after playing card with rank 8 to computer player.
     * Computer player chooses the most common color from hand.
     * @return the most common color in computer player cards
     */
    // metoda sprawdzająca, jakich kolorów kart komputer ma najwięcej, żeby wybrać kolor
    public String theMost() {
        String r = "";
        int s = 0;
        int d = 0;
        int h = 0;
        int c = 0;
        int current = 0;
        for (int i = 0; i < getCards().size(); i++) {
            if (getCards().get(i).getSuit().equals("Clubs")) {
                c++;
            } else if (getCards().get(i).getSuit().equals("Hearts")) {
                h++;
            } else if (getCards().get(i).getSuit().equals("Diamonds")) {
                d++;
            } else if (getCards().get(i).getSuit().equals("Spades")) {
                s++;
            }
        }
        int[] tab = {h, d, s, c};
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] > current) {
                current = tab[i];
            }
        }
        if (s == current) {
            r = "Spades";
        } else if (c == current) {
            r = "Clubs";
        } else if (h == current) {
            r = "Hearts";
        } else if (d == current) {
            r = "Diamonds";
        }
        return r;
    }

    /**
     * Implements better strategy for computer player.
     * Computer player plays card with rank 8 when only 1 card with rank other than 8 remains in hand.
     * @return information if computer player should start to play cards with rank 8
     */
    public boolean throwEights() {
        int count = 0;
        ArrayList<Card> cards = getCards();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getRank().equals("8"))
                count++;
        }

        return getN() - count <= 1 && count != 0;
    }
}
