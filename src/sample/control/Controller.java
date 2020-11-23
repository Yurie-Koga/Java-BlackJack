package sample.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import sample.model.Card;
import sample.model.Deck;
import sample.model.Player;
import sample.view.HelpWindow;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    public Button btnStart;
    @FXML
    public Button btnStand1;
    @FXML
    public Button btnStand2;
    @FXML
    public Button btnHit1;
    @FXML
    public Button btnHit2;
    @FXML
    public Button btnHelp;
    @FXML
    public Button btnEsc;

    private Deck deck;
    private ArrayList<Player> players = new ArrayList<>();
    private int playerInt;
    private Player player;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Deck deck = new Deck();
        players.add(0, new Player("Player1", new ArrayList<Card>(), 0));
        players.add(1, new Player("Player2", new ArrayList<Card>(),0));
        players.add(2, new Player("Dealer", new ArrayList<Card>(), 0));
    }


    public void gameStart(ActionEvent actionEvent) {
        System.out.println("deckOfCards start with " + Deck.getDeckOfCards().size() + " !");
        for (int i = 0; i < 3; i++){
            System.out.println(players.get(i));
        }
        System.out.println("Game Start!");
        // Hide Start Button after game starting
        btnStart.setVisible(false);
        playerInt = 0;
        enableDisableButton(getTurn(playerInt));
    }

    public Player getTurn(int playerInt){
        return players.get(playerInt);
    }

    public void enableDisableButton(Player player){
        if(player == players.get(0)){
            btnHit1.setDisable(false);
            btnStand1.setDisable(false);
            btnHit2.setDisable(true);
            btnStand2.setDisable(true);
        }
        else if (player == players.get(1)){
            btnHit2.setDisable(false);
            btnStand2.setDisable(false);
            btnHit1.setDisable(true);
            btnStand1.setDisable(true);
        } else {
            btnHit1.setDisable(true);
            btnStand1.setDisable(true);
            btnHit2.setDisable(true);
            btnStand2.setDisable(true);
            //------------- Dealer method HERE----------------------
            Card pickedCard = pickCard(player, deck);
            player.setHand(addToHand(player,pickedCard));
            player.setSum(totalSum(player));
            System.out.println(players.get(2));
            playerInt = 0;
            enableDisableButton(players.get(playerInt));
            //------------------------------------------------------
        }
    }

    /// Update to return Card
    public Card pickCard(Player player, Deck deck){
        Random rand = new Random();
        int int_random = rand.nextInt(Deck.getDeckOfCards().size());
        System.out.println("Randomly chosen card for " + player.getName() + " is" + Deck.getDeckOfCards().get(int_random));
        Card pickedCard = Deck.getDeckOfCards().get(int_random);
        Deck.getDeckOfCards().remove(int_random);
        Deck.setDeckOfCards(Deck.getDeckOfCards());
        return pickedCard;
    }

    // Update to add Card obj into arrayList
    public ArrayList<Card> addToHand(Player player, Card card){
        ArrayList<Card> newHand = player.getHand();
        newHand.add(card);
        return newHand;
    }

    // return sum as int
    public int totalSum(Player player){
        int newSum = 0;
        for (int i = 0; i < player.getHand().size();i++){
            newSum = newSum + player.getHand().get(i).getRank();
        }
        return newSum;
    }

    public void hitClicked(ActionEvent actionEvent) {
        player = getTurn(playerInt);
        player.setHand(addToHand(player, pickCard(player, deck)));
        player.setSum(totalSum(player));
        System.out.println(player);

        Button hitClicked = (Button) actionEvent.getSource();
        String hitButtonId = hitClicked.getId();
        if (hitButtonId.equals("btnHit1")){
            playerInt++;
        } else if (hitButtonId.equals("btnHit2")){
            playerInt++;
        }
        enableDisableButton(getTurn(playerInt));
    }

    public void standClicked(ActionEvent actionEvent) {
        Button hitClicked = (Button) actionEvent.getSource();
        String hitButtonId = hitClicked.getId();
        if(hitButtonId.equals("btnStand1")){
            playerInt++;
            System.out.println("btnStand1 clicked");
        } else if(hitButtonId.equals("btnStand2")){
            playerInt = 0;
            System.out.println("btnStand2 clicked");
        }
        enableDisableButton(getTurn(playerInt));
    }


    public void escClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Do you quit this game?");
        ButtonType continueBtn = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
        ButtonType quitBtn = new ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(continueBtn,quitBtn);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == quitBtn){
            System.exit(0);
        }
    }


    public void helpClicked(ActionEvent actionEvent) {
        System.out.println("help clicked");
        HelpWindow.displayHelp(actionEvent, getClass());
    }

    /// Display result (Judge -> use judge class to show the result (Win, bust, push)

}