package edu.ib;

/**
 * Simulates human player.
 * Extends abstract class Player
 */
public class RealPlayer extends Player {
    boolean real = true;
    String prompt = ("You made your move successfully.");

    /**
     * @return prompt with information about player's actions
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * @return information whether player is human or computer
     */
    @Override
    public boolean isReal() {
        return super.isReal(real);
    }

    /**
     * Human player turn.
     * @param rank rank of the card which player tries to play
     * @param suit suit of the card which player tries to play
     * @param table top card on table pile
     * @param currentSuit current suit set in the game
     * @return played card
     */
    @Override
    public Card turn(String rank, String suit, Table table, String currentSuit) {
        Card card = new Card();
        Boolean match = true;
        Boolean hasCard = false;

        for (int j = 0; j < getN(); j++) {

            if (getCards().get(j).getRank().equals(rank) && getCards().get(j).getSuit().equals(suit)) { //sprawdzenie czy karta jest na ręce
                hasCard = true;
                match = false;

                if (getCards().get(j).getSuit().equals(currentSuit)
                        || getCards().get(j).getRank().equals(table.getCards().get(table.getCards().size() - 1).getRank())
                        || (getCards().get(j).getRank().equals("8") && getN() != 1)) { // sprawdzenie czy karta pasuje do karty na stole
                    match = true; // karta pasuje
                    card = getCards().get(j);
                    prompt = ("You made your move successfully.");
                    // JavaFX - komunikat
                } else if (getCards().get(j).getRank().equals("8") && getN() == 1) {
                    prompt = ("You can't end game with an eight.");
                }
            }
        }

        // JavaFX - komunikat - tego nie będzie, bo gracz wybiera karty spośród widocznych na ekranie
        if (!hasCard) { // gracz nie ma karty, którą zadeklarował
            prompt = ("You don't have this card.");
        }

        // JavaFX - komunikat
        if (!match) { // karta, którą zadeklarował gracz, nie pasuje
            prompt = ("This card doesn't match card on the table.");
        }

        return super.turn(card);
    }
}
