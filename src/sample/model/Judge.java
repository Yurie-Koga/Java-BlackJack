//package sample.model;
//
//
//import java.util.ArrayList;
//
///**
// * This is to judge the end of game: WIN/BUST/PUSH
// */
//public class Judge extends Player {
//    public boolean onPlay;
//
//    public Judge(String name, ArrayList<Integer> hand,  int sum, boolean playing, boolean onPlay){
//        super(name, hand, sum, false);
//        this.onPlay = true;
//    }
//
//    private String name;    // Dealer, player1, player2
//    private ArrayList<Integer> hand; //  to track which card(value/suit) the player holds
//    private int sum;        // sum of cards picked
//    private boolean playing;
//
//
//
//    // Ger result method Here (depends on the sum of the deck)
//        // - One bust: One player sum <= 21 & the other sum > 21
//        // - both not bust: one player sum <=21 & the other sum <= 21 -> Which ever closer to 21, wins
//        // - both bust (PUSH: one player sum > 21 & the other sum > 21 & one player sum <= 21 & the other sum <= 21
//        // when sum == 21 -> BlackJack
//        // // When onPlay is false (-> Play again)
//
//}

