package edu.ib;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Deck deck = new Deck();
        deck.shuffle(); // przetasowanie
        Table table = new Table();
        RealPlayer p1 = new RealPlayer();
        ComputerPlayer p2 = new ComputerPlayer();
        Player[] p = {p1, p2}; // gracze
        for (int j = 0; j < p.length; j++) {
            for (int i = 0; i < 20; i++) { // dla dwóch graczy liczba kart = 7
                deck.move(deck.getCards().size() - 1, p[j]);
            }
        }

        deck.move(deck.getCards().size() - 1, table);
        Scanner scan1 = new Scanner(System.in);

        String rank = "";
        String suit = "";
        Boolean winner = true;
        String winnerName = "";
        Card currentCard = new Card();
        String currentSuit = table.getCards().get(table.getCards().size() - 1).getSuit();

        while (winner) { // rozgrywka

            for (int i = 0; i < p.length; i++) { // tura gry
                Boolean flag1 = true;
                System.out.println("GRACZ " + (i + 1));
                if (p[i].isReal()) {
                    while (flag1) {
                        if (deck.getCards().size() == 0) {  // sprawdzenie czy deck jest pusty, jeśli tak, tasowanie kart
                            Boolean continueplay = false;
                            for (int j = 0; j < p[i].getN(); j++) {
                                if (p[i].getCards().get(j).getSuit().equals(currentSuit)
                                        || p[i].getCards().get(j).getRank().equals(table.getCards().get(table.getCards().size() - 1).getRank())
                                        || p[i].getCards().get(j).getRank().equals("8")) {
                                    continueplay = true;
                               }
                           }

                          for (int j = 0; j < table.getCards().size() - 1; j++) {
                                table.move(j, deck);
                            }
                            deck.shuffle();
                            System.out.println("Potasowano");
                            if (!continueplay) { //sprawdzenie czy turę można kontynuować
                                System.out.println("Brak kart na stosie, koniec tury");
                                break;
                            }
                        }

                        System.out.println("Twoje karty:");
                        // JavaFX - scrollView
                        for (int j = 0; j < p[i].getN(); j++) {
                            System.out.println(p[i].getCards().get(j).getRank() + p[i].getCards().get(j).getSuit());
                        }

                        // JavaFX - załadowany obrazek
                        System.out.println("\nKarta na stole:");
                        System.out.println(table.getCards().get(table.getCards().size() - 1).getRank()
                                + table.getCards().get(table.getCards().size() - 1).getSuit()
                                + "(" + currentSuit + ")");


                        while (flag1) { // ruch gracza
                            rank = scan1.nextLine(); //String z wyboru karty na rank i suit

                            if (rank.equals("P")) { // gracz dobiera karty
                                try { // zapobieganie dobrania karty z pustego stosu
                                    p[i].getCards().add(deck.getCards().get(deck.getCards().size() - 1));
                                    deck.getCards().remove(deck.getCards().size() - 1);
                                    break;
                                    // JavaFX - komunikat
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Brak możliwości dobrania karty, reset gry");
                                }
                            }
                            suit = scan1.nextLine();
                            currentCard = p[i].turn(rank, suit, table, currentSuit);


                            if (!currentCard.getSuit().equals("notyet")) {
                                flag1 = false;
                                for (int j = 0; j < p[i].getCards().size(); j++) {
                                    if (p[i].getCards().get(j).getRank().equals(currentCard.getRank()) && p[i].getCards().get(j).getSuit().equals(currentCard.getSuit())) {
                                        p[i].move(j, table);
                                        currentSuit = currentCard.getSuit();
                                        boolean stop = true;
                                        if (currentCard.getRank().equals("8")) {
                                            while (stop) {
                                                System.out.println("Wybierz kolor:");
                                                String choice = scan1.nextLine(); //getText z comboboxa
                                                if (choice.equals("Hearts") || choice.equals("Spades") || choice.equals("Diamonds") || choice.equals("Clubs")) {
                                                    currentSuit = choice;
                                                    stop = false;
                                                } else {
                                                    System.out.println("Nie ma takiego koloru");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else {  // ruch komputera
                    while (flag1) {

                        if (deck.getCards().size() == 0) {  // sprawdzenie czy deck jest pusty
                            Boolean continueplay = false;
                            for (int j = 0; j < p[i].getN(); j++) {
                                if (p[i].getCards().get(j).getSuit().equals(table.getCards().get(table.getCards().size() - 1).getSuit())
                                        || p[i].getCards().get(j).getRank().equals(table.getCards().get(table.getCards().size() - 1).getRank())
                                        || p[i].getCards().get(j).getRank().equals("8")) {
                                    continueplay = true;
                                }
                            }

                            for (int j = 0; j < table.getCards().size() - 1; j++) {
                                table.move(j, deck);
                            }
                            deck.shuffle();
                            System.out.println("Potasowano");
                            if (!continueplay) { //sprawdzenie czy turę można kontynuować
                                System.out.println("Brak kart na stosie, koniec tury");
                                break;
                            }
                        }

                        System.out.println("Twoje karty:");
                        for (int j = 0; j < p[i].getN(); j++) {
                            System.out.println(p[i].getCards().get(j).getRank() + p[i].getCards().get(j).getSuit());
                        }

                        System.out.println("\nKarta na stole:");
                        System.out.println(table.getCards().get(table.getCards().size() - 1).getRank()
                                + table.getCards().get(table.getCards().size() - 1).getSuit());


                        while (flag1) { // ruch komputera
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
                                p[i].getCards().add(deck.getCards().get(deck.getCards().size() - 1));
                                deck.getCards().remove(deck.getCards().size() - 1);
                            }
                        }
                    }
                }
                // JavaFX - komunikat o wygranej
                if (p[i].getN() == 0) { // wygrana
                    winner = false;
                    winnerName = "Player" + (i + 1);
                    System.out.println("Winner: " + winnerName);
                    break;
                }
            }
        }

    }
}