package sample.model;

/**
 * This is to represent trump cards
 */
public class Card {

    private String suit;        // represent ♡, ☘,♠︎, ♦︎　
    private int rank;           // represent card number
    private String value;       // represent the value of cards: A, 2, 3, ..., J, Q, K

    // Constructor for card class
    public Card(String suit, int rank, String value){
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    // getter methods
    public String getSuit(){
        return suit;
    }
    public int getRank(){
        return rank;
    }

    public String getValue() {
        return value;
    }

    // setter methods
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", rank=" + rank +
                ", value='" + value + '\'' +
                '}';
    }
}
