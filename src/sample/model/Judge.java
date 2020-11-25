package sample.model;

import javafx.scene.control.Alert;

import java.util.ArrayList;

/**
 * This is to judge the end of game: WIN/BUST/PUSH
 */
public class Judge extends Player {

    public Judge(String name, ArrayList<Card> hand, int sum) {
        super(name, hand, sum);
    }

    /**
     * Check if the first 2 cards are BlackJack
     *
     * @param players
     * @param name
     * @return boolean
     */
    public static boolean isBlackJack(ArrayList<Player> players, String name) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(name)) {
                if (players.get(i).getSum() == 21) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if sum of player's hand exceed 21, and if so disable the buttons
     *
     * @param player
     * @return boolean
     */
    public static boolean isBusted(Player player) {
        if (player.getSum() > 21) {
            return true;
        }
        return false;
    }

    /**
     * Check the game result
     *
     * @param players
     */
    public static void gameResult(ArrayList<Player> players) {
        System.out.println("game Result method ");
        int player1Sum = 0;
        int player2Sum = 0;
        int dealerSum = 0;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals("Player1")) {
                player1Sum = players.get(i).getSum();
            } else if (players.get(i).getName().equals("Player2")) {
                player2Sum = players.get(i).getSum();
            } else if (players.get(i).getName().equals("Dealer")) {
                dealerSum = players.get(i).getSum();
            }
        }

        if (dealerSum > 21) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dealer Bust");
            alert.setHeaderText("Dealer Busted! Player win!");
            alert.showAndWait();
        } else if (dealerSum <= 21 && player1Sum <= 21 && player1Sum != 0 && player2Sum <= 21 && player2Sum != 0) {      ///added this else if
            if (dealerSum - player1Sum == 0 && dealerSum - player2Sum == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dealer vs Player1 vs Player2: PUSH");
                alert.setHeaderText("Dealer,Player1 and Player2 Push!");
                alert.showAndWait();
            }
        }
        if (dealerSum <= 21 && player1Sum <= 21 && player1Sum != 0) {
            int dealerDiff = 21 - dealerSum;
            int player1Diff = 21 - player1Sum;
            if (dealerDiff == player1Diff) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dealer vs Player1: PUSH");
                alert.setHeaderText("Dealer and Player1 Push!");
                alert.showAndWait();
            } else if (dealerDiff < player1Diff) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dealer vs Player1: Dealer won");
                alert.setHeaderText("Dealer won!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dealer vs Player1: Player1 won");
                alert.setHeaderText("Player1 won!");
                alert.showAndWait();
            }
        }
        if (dealerSum <= 21 && player2Sum <= 21 && player2Sum != 0) {
            int dealerDiff = 21 - dealerSum;
            int player2Diff = 21 - player2Sum;
            if (dealerDiff == player2Diff) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dealer vs Player2: PUSH");
                alert.setHeaderText("Dealer and Player2 Push!");
                alert.showAndWait();
            } else if (dealerDiff < player2Diff) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dealer vs Player2: Dealer won");
                alert.setHeaderText("Dealer won!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dealer vs Player2: Player2 won");
                alert.setHeaderText("Player2 won!");
                alert.showAndWait();
            }
        }
    }
}