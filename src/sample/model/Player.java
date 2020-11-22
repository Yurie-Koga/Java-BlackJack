package sample.model;
import java.util.ArrayList;

/**
 * This class is to represent players (Dealer, player1, player2)
 */
public class Player {

    private  String name;    // Dealer, player1, player2
    private  ArrayList<Card> hand;
    private  int sum;        // sum of cards picked

    public Player(String name, ArrayList<Card> hand, int sum){
        this.name = name;
        this.hand = hand;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    public int getSum() {
        return sum;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + hand +
                ", sum=" + sum +
                '}';
    }

}