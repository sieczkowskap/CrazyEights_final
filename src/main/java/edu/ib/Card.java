package edu.ib;

/**
 * Simulates card object.
 */
class Card {
    private String suit;
    private String rank;

    /**
     * Constructor with parameters.
     * @param suit suit of the card
     * @param rank rank of the card
     */
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Default constructor.
     * set suit to "notyet" which does not match any suit used in the game
     */
    public Card() {
        suit = "notyet";
    }

    /**
     * @return suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * @return rank of the card
     */
    public String getRank() {
        return rank;
    }
}
