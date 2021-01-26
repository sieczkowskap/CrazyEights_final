package edu.ib;

import javafx.scene.text.Text;

public class RealPlayer extends Player {
    boolean real = true;
    String prompt = ("You made your move successfully.");

    public String getPrompt() {
        return prompt;
    }

    @Override
    public boolean isReal() {
        return super.isReal(real);
    }

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
                    prompt = ("You cannot end game with am eight.");
                }
            }
        }

        // JavaFX - komunikat - tego nie będzie, bo gracz wybiera karty spośród widocznych na ekranie
        if (!hasCard) { // gracz nie ma karty, którą zadeklarował
            prompt = ("You do not have this card.");
        }

        // JavaFX - komunikat
        if (!match) { // karta, którą zadeklarował gracz, nie pasuje
            prompt = ("This card does not match card on the table.");
        }

        return super.turn(card);
    }
}
