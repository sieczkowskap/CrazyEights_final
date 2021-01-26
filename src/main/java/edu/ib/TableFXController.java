package edu.ib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.collections.*;

public class TableFXController {
    // Club - \u2663
    // Spades - \u2660
    // Diamond - \u2666
    // Heart - \u2665

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text TVCardsOnDeck;

    @FXML
    private Text TVOponentAction;

    @FXML
    private Text TVOponentCards;

    @FXML
    private ComboBox<String> CBSuitSelect;

    @FXML
    private ImageView imageView;

    @FXML
    private Button BtnDraw;

    @FXML
    private Text TVPrompts;

    @FXML
    private Text TVPlayerCards;

    @FXML
    private Button BtnPlay;


    @FXML
    private Button btnNewGame;

    @FXML
    private ComboBox<String> CBCardSelect;

    Deck deck = new Deck();
    Table table = new Table();
    RealPlayer p1 = new RealPlayer();
    ComputerPlayer p2 = new ComputerPlayer();
    Player[] p = {p1, p2}; // gracze
    String rank = "";
    String suit = "";
    Boolean winner = true;
    String winnerName = "";
    Card currentCard = new Card();
    String currentSuit;

    boolean playersError = false;

    ObservableList<String> clear = FXCollections.observableArrayList(" ");
    ObservableList<String> suits = FXCollections.observableArrayList("\u2663",
            "\u2660", "\u2666", "\u2665");


    // boolean isCbSuitsEmpty = CBSuitSelect.getSelectionModel().isEmpty(); // informacja czy jest wybrany kolor
    //boolean isCbCardsEmpty = cbCards.getSelectionModel().isEmpty(); // informacja czy jest wybrana karta

    // gracz dobiera karty
    @FXML
    void onBtnDrawClick(ActionEvent event) throws FileNotFoundException {
        shuffleDeckIfEmptyPlayer(); // tasowanie kart na stosie jeśli pusty
        CBCardSelect.setItems(clear);
        //CBCardSelect.setItems(stringsToUnicode(p[0].getCards())); // posiadane aktualnie karty

        try { // zapobieganie dobrania karty z pustego stosu
            p[0].getCards().add(deck.getCards().get(deck.getCards().size() - 1));
            deck.getCards().remove(deck.getCards().size() - 1);
            TVPrompts.setText("Card drawn successfully");
        } catch (IndexOutOfBoundsException e) {
            TVPrompts.setText("You can't draw another card. Choose card from your hand.");
        }

        shuffleDeckIfEmptyPlayer(); // tasowanie kart na stosie jeśli pusty
        TVCardsOnDeck.setText("Cards on deck: " + String.valueOf(deck.getCards().size()));
        // CBCardSelect.setItems(clear);
        CBCardSelect.setItems(stringsToUnicode(p[0].getCards())); // posiadane aktualnie karty
        TVPlayerCards.setText("Your cards: " + String.valueOf(p[0].getN()));
    }

    @FXML
    void onBtnNewGameClick(ActionEvent event) throws FileNotFoundException {
        deck = new Deck();
        table = new Table();

        for (int i = 0; i < p1.getCards().size() - 1; i++)
            p1.move(p1.getCards().size() - 1, deck);

        for (int i = 0; i < p2.getCards().size() - 1; i++)
            p2.move(p2.getCards().size() - 1, deck);

        for (int i = 0; i < table.getCards().size() - 1; i++)
            table.move(table.getCards().size() - 1, deck);

        //p = {p1, p2}; // gracze
        rank = "";
        suit = "";
        winner = true;
        winnerName = "";
        currentCard = new Card();
        String currentSuit;

        boolean playersError = false;
        initialize();
    }


    @FXML
    void onBtnPlayClick(ActionEvent event) throws FileNotFoundException {

        showCard(table.getCards().get(table.getCards().size() - 1).getRank(), table.getCards().get(table.getCards().size() - 1).getSuit());

        playersError = false;
        TVPrompts.setText("Play your card.");

        CBCardSelect.setItems(stringsToUnicode(p[0].getCards())); // posiadane aktualnie karty

        for (int i = 0; i < p.length; i++) { // tura gry

            if (playersError) break;

            Boolean flag1 = true;

            if (p[i].isReal()) {

                CBCardSelect.setItems(stringsToUnicode(p[0].getCards())); // update posiadanych kart

                // !!! JavaFX - załadowany obrazek

                while (flag1) { // ruch gracza
                    // getValue() JavaFX
                    // wywołanie metody podziału karty
                    // suit = ;
                    // rank = ;
                    if (CBCardSelect.getValue() == null) {

                        TVPrompts.setText("You didn't choose a card.");
                        playersError = true;
                        break;

                    }

                    String[] rankAndSuit = unicodeToStringsCards(CBCardSelect.getValue());
                    rank = rankAndSuit[0];
                    suit = rankAndSuit[1];

                    //rank = scan1.nextLine(); //String z wyboru karty na rank i suit
                    //suit = scan1.nextLine();
                    currentCard = p[i].turn(rank, suit, table, currentSuit);
                    TVPrompts.setText(p1.getPrompt());
                    if (!currentCard.getSuit().equals("notyet")) {
                        flag1 = false;
                        for (int j = 0; j < p[i].getCards().size(); j++) {
                            if (p[i].getCards().get(j).getRank().equals(currentCard.getRank()) && p[i].getCards().get(j).getSuit().equals(currentCard.getSuit())) {

                                boolean stop = true;
                                if (currentCard.getRank().equals("8")) {
                                    while (stop) {
                                        //System.out.println("Wybierz kolor:");

                                        if (CBSuitSelect.getValue() == null) { // gracz nie dokonał wyboru koloru
                                            TVPrompts.setText("You haven't chosen new suit.");
                                            playersError = true;
                                            break;
                                        } else { // gracz dokonał wyboru
                                            String choice = unicodeToStringsSuits(CBSuitSelect.getValue()); //getTValue z comboboxa
                                            //if (choice.equals("Hearts") || choice.equals("Spades") || choice.equals("Diamonds") || choice.equals("Clubs")) {
                                            currentSuit = choice;
                                            stop = false;
                                            p[i].move(j, table);
                                            playersError = false;
                                        }
                                    }
                                } else {
                                    currentSuit = currentCard.getSuit();
                                    p[i].move(j, table);
                                    playersError = false;
                                }
                            }
                        }

                    } else {
                        playersError = true;
                        break;
                    }

                }

            } else {  // ruch komputera
                while (flag1) {
                    shuffleDeckIfEmptyComputer(); // tasowanie kart, kiedy brak na stosie

                    while (flag1) { // ruch komputera
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }

                        boolean madeAMove = false;
                        for (int j = 0; j < p[i].getCards().size(); j++) {
                            if (madeAMove) {
                                break;
                            }

                            if (p2.throwEights()) break;

                            if (!p[i].getCards().get(j).getRank().equals("8")) {

                                rank = p[i].getCards().get(j).getRank();
                                suit = p[i].getCards().get(j).getSuit();
                                currentCard = p[i].turn(rank, suit, table, currentSuit);
                                if (!currentCard.getSuit().equals("notyet")) {
                                    flag1 = false;
                                    for (int h = 0; h < p[i].getCards().size(); h++) {
                                        if (p[i].getCards().get(h).getRank().equals(currentCard.getRank()) && p[i].getCards().get(h).getSuit().equals(currentCard.getSuit())) {
                                            p[i].move(h, table);
                                            madeAMove = true;
                                            currentSuit = currentCard.getSuit();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (!madeAMove) {
                            if (madeAMove) {
                                break;
                            }
                            for (int j = 0; j < p[i].getCards().size(); j++) {
                                if (p[i].getCards().get(j).getRank().equals("8")) {
                                    rank = p[i].getCards().get(j).getRank();
                                    suit = p[i].getCards().get(j).getSuit();
                                    currentCard = p[i].turn(rank, suit, table, currentSuit);
                                    if (!currentCard.getSuit().equals("notyet")) {
                                        flag1 = false;
                                        for (int h = 0; h < p[i].getCards().size(); h++) {
                                            if (p[i].getCards().get(h).getRank().equals(currentCard.getRank()) && p[i].getCards().get(h).getSuit().equals(currentCard.getSuit())) {
                                                p[i].move(h, table);
                                                madeAMove = true;
                                                currentSuit = p2.theMost();
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (!madeAMove) {
                            shuffleDeckIfEmptyComputer();
                            p[i].getCards().add(deck.getCards().get(deck.getCards().size() - 1));
                            deck.getCards().remove(deck.getCards().size() - 1);
                        }
                    }
                }
            }
            // JavaFX - komunikat o wygranej
            if (p[i].getN() == 0) { // wygrana
                winner = false;
                winnerName = "Player " + (i + 1);
                TVPrompts.setText("Winner: " + winnerName);
                break;
            }
        }
        showCard(table.getCards().get(table.getCards().size() - 1).getRank(), table.getCards().get(table.getCards().size() - 1).getSuit());


        TVOponentCards.setText("Opponent's cards: " + String.valueOf(p[1].getN()));
        TVPlayerCards.setText("Your cards: " + String.valueOf(p[0].getN()));
        TVCardsOnDeck.setText("Cards on deck: " + String.valueOf(deck.getCards().size()));
        CBCardSelect.setItems(clear);
        CBCardSelect.setItems(stringsToUnicode(p[0].getCards())); // posiadane aktualnie karty
        TVOponentAction.setText("Current suit: " + stringToUnicode(currentSuit));
    }

    void shuffleDeckIfEmptyPlayer() {
        if (deck.getCards().size() == 0) {  // sprawdzenie czy deck jest pusty, jeśli tak, tasowanie kart
            Boolean continueplay = false;
            for (int j = 0; j < p[0].getN(); j++) {
                if (p[0].getCards().get(j).getSuit().equals(currentSuit)
                        || p[0].getCards().get(j).getRank().equals(table.getCards().get(table.getCards().size() - 1).getRank())
                        || p[0].getCards().get(j).getRank().equals("8")) {
                    continueplay = true;
                }
            }

            // dawanie pierwszej karty na stos
            for (int j = 0; j < table.getCards().size() - 1; j++) {
                table.move(j, deck);
            }
            deck.shuffle();
            TVCardsOnDeck.setText("Cards on deck: " + String.valueOf(deck.getCards().size()));
            if (!continueplay) { //sprawdzenie czy turę można kontynuować
                TVCardsOnDeck.setText("No cards on deck.");
            }
        }
    }

    void shuffleDeckIfEmptyComputer() {
        if (deck.getCards().size() == 0) {  // sprawdzenie czy deck jest pusty
            Boolean continueplay = false;
            for (int j = 0; j < p[1].getN(); j++) {
                if (p[1].getCards().get(j).getSuit().equals(table.getCards().get(table.getCards().size() - 1).getSuit())
                        || p[1].getCards().get(j).getRank().equals(table.getCards().get(table.getCards().size() - 1).getRank())
                        || p[1].getCards().get(j).getRank().equals("8")) {
                    continueplay = true;
                }
            }

            for (int j = 0; j < table.getCards().size() - 1; j++) {
                table.move(j, deck);
            }
            deck.shuffle();
            TVCardsOnDeck.setText("Cards on deck: " + String.valueOf(deck.getCards().size()));
            TVOponentAction.setText("Cards shuffled.");
            if (!continueplay) { //sprawdzenie czy turę można kontynuować
                TVOponentAction.setText("No cards on deck.");
            }
        }
    }

    // Club - \u2663
    // Spades - \u2660
    // Diamond - \u2666
    // Heart - \u2665

    // comboBox Suits
    String unicodeToStringsSuits(String unicode) {

        if (unicode.equals("\u2663"))
            unicode = "Clubs";
        else if (unicode.equals("\u2660"))
            unicode = "Spades";
        else if (unicode.equals("\u2666"))
            unicode = "Diamonds";
        else if (unicode.equals("\u2665"))
            unicode = "Hearts";

        return unicode;
    }

    // konwertowanie do wyświetlenia aktualnego koloru
    String stringToUnicode(String unicode) {

        if (unicode.equals("Clubs"))
            unicode = "\u2663";
        else if (unicode.equals("Spades"))
            unicode = "\u2660";
        else if (unicode.equals("Diamonds"))
            unicode = "\u2666";
        else if (unicode.equals("Hearts"))
            unicode = "\u2665";

        return unicode;
    }


    // comboBox Cards
    String[] unicodeToStringsCards(String uni) {
        String[] split = uni.split(" ");

        for (int i = 0; i < split.length; i++) {
            if (split[1].equals("\u2663"))
                split[1] = "Clubs";
            else if (split[1].equals("\u2660"))
                split[1] = "Spades";
            else if (split[1].equals("\u2666"))
                split[1] = "Diamonds";
            else if (split[1].equals("\u2665"))
                split[1] = "Hearts";
        }

        return split;
    }

    // przygotowywanie do wyświetlenia kart u gracza
    ObservableList stringsToUnicode(ArrayList<Card> cards) {
        String[] unis = new String[cards.size()];

        for (int i = 0; i < cards.size(); i++) {
            String suit = cards.get(i).getSuit();
            if (suit.equals("Clubs"))
                suit = "\u2663 ";
            else if (suit.equals("Spades"))
                suit = "\u2660 ";
            else if (suit.equals("Diamonds"))
                suit = "\u2666 ";
            else if (suit.equals("Hearts"))
                suit = "\u2665 ";

            unis[i] = cards.get(i).getRank() + " " + suit;
        }

        return FXCollections.observableArrayList(unis);
    }

    void showCard(String rank, String suit) throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream("C:\\Users\\pauli\\IdeaProjects\\CrazyEights\\src\\main\\java\\edu\\ib\\images\\" + rank + suit.charAt(0) + ".png");

        Image img = new Image(inputStream);
        imageView.setImage(img);
    }

    @FXML
    void initialize() throws FileNotFoundException {
        assert TVOponentAction != null : "fx:id=\"TVOponentAction\" was not injected: check your FXML file 'table.fxml'.";
        assert TVOponentCards != null : "fx:id=\"TVOponentCards\" was not injected: check your FXML file 'table.fxml'.";
        assert CBSuitSelect != null : "fx:id=\"CBSuitSelect\" was not injected: check your FXML file 'table.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'table.fxml'.";
        assert BtnDraw != null : "fx:id=\"BtnDraw\" was not injected: check your FXML file 'table.fxml'.";
        assert TVPrompts != null : "fx:id=\"TVPrompts\" was not injected: check your FXML file 'table.fxml'.";
        assert TVPlayerCards != null : "fx:id=\"TVPlayerCards\" was not injected: check your FXML file 'table.fxml'.";
        assert BtnPlay != null : "fx:id=\"BtnPlay\" was not injected: check your FXML file 'table.fxml'.";
        assert CBCardSelect != null : "fx:id=\"CBCardSelect\" was not injected: check your FXML file 'table.fxml'.";
        assert btnNewGame != null : "fx:id=\"btnNewGame\" was not injected: check your FXML file 'table.fxml'.";
        TVPrompts.setText("Play your card."); // komunikat na początku gry
        deck.shuffle(); // przetasowanie
        for (int j = 0; j < p.length; j++) {
            for (int i = 0; i < 7; i++) { // dla dwóch graczy liczba kart = 7
                deck.move(deck.getCards().size() - 1, p[j]);
            }
        }

        deck.move(deck.getCards().size() - 1, table);
        currentSuit = table.getCards().get(table.getCards().size() - 1).getSuit();

        CBSuitSelect.setItems(suits); // dodawanie wartości do listy
        CBCardSelect.setItems(stringsToUnicode(p[0].getCards())); // dodanie pierwszych kart gracza

        TVOponentCards.setText("Opponent's cards: " + String.valueOf(p[1].getN()));
        TVPlayerCards.setText("Your cards: " + String.valueOf(p[0].getN()));

        TVOponentAction.setText("Current suit: " + stringToUnicode(currentSuit));


        showCard(table.getCards().get(table.getCards().size() - 1).getRank(), table.getCards().get(table.getCards().size() - 1).getSuit());
        TVCardsOnDeck.setText("Cards on deck: " + String.valueOf(deck.getCards().size()));
    }
}