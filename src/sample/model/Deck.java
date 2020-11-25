package sample.model;

import java.util.ArrayList;

public class Deck {

    // Suit Constants
    public static final String HEARTS = "♥";
    public static final String DIAMONDS = "♦";
    public static final String SPADES = "♠";
    public static final String CLOVERS = "♣";

    // Rank Constants
    private static final int ACE = 11;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int EIGHT = 8;
    private static final int NINE = 9;
    private static final int TEN = 10;
    private static final int JACK = 10;
    private static final int QUEEN = 10;
    private static final int KING = 10;

    private static final String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static ArrayList<Card> deckOfCards;

    /**
     * Constructor
     * Arrange all cards here
     */
    public Deck(){
        this.deckOfCards = refillDeck();
    }


    public Deck(ArrayList<Card> deckOfCards){
        this.deckOfCards = deckOfCards;
    }

    public static ArrayList<Card> refillDeck(){
        ArrayList<Card> deckOfCards = new ArrayList<>();

        String[] suit = {HEARTS, DIAMONDS, SPADES, CLOVERS};
        int[] rank = {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};

        for (int i = 0; i < 4; i++){
         for (int j = 0; j < 13; j++){
            deckOfCards.add(new Card(suit[i], rank[j], values[j]));
         }
        }
        Deck.deckOfCards = deckOfCards;
        return deckOfCards;
    }


    public static ArrayList<Card> getDeckOfCards(){
        return deckOfCards;
    }


    public static void setDeckOfCards(ArrayList<Card> deckOfCards){
        Deck.deckOfCards = deckOfCards;
    }


    @Override
    public String toString() {
        return "Deck{" +
                "deckOfCards=" + deckOfCards +
                '}';
    }
}