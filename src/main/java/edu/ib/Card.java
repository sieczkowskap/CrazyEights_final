package edu.ib;

class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Card() {
        suit = "notyet";
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }
}
