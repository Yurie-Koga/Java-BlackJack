package sample.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.model.Card;
import sample.model.Deck;
import sample.model.Judge;
import sample.model.Player;
import sample.view.HelpWindow;
import java.net.URL;
import java.util.*;


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
    @FXML
    public Label labelSum1;
    @FXML
    public Label labelSum2;
    @FXML
    public Label labelSumDealer;
    @FXML
    public Label labelDeck;

    private Deck deck;
    private ArrayList<Player> players = new ArrayList<>();
    private int playerInt;
    private Player player;
    private ArrayList<Button> playerBtns = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Deck deck = new Deck();
        players.add(new Player("Player1", new ArrayList<Card>(), 0));
        players.add(new Player("Player2", new ArrayList<Card>(),0));
        players.add(new Player("Dealer", new ArrayList<Card>(), 0));

        playerBtns = new ArrayList<>(Arrays.asList(btnStand1, btnStand2, btnHit1, btnHit2));
        initWindow();
        initEnable();
    }

    /**
     * Initialize labels which are being updated during the game
     */
    private void initWindow(){
        labelSum1.setText("0");
        labelSum2.setText("0");
        labelSumDealer.setText("0");
        labelDeck.setText(String.valueOf(Deck.getDeckOfCards().size()));
    }

    /**
     * Set controllers as disable
     */
    private void initEnable(){
        for(Button b: playerBtns){
            b.setDisable(true);
        }
    }

    /**
     * Distribute 2 cards to players and a dealer hands
     * @param player
     * @return
     */
    public ArrayList<Card> InitialHand(Player player){
        if(player.getName().equals("Player1") || player.getName().equals("Player2")){
            Card pickedCard1 = pickCard(player,deck);
            player.setHand(addToHand(player,pickedCard1));
            player.setSum(totalSum(player));
            Card pickedCard2 = pickCard(player,deck);
            player.setHand(addToHand(player,pickedCard2));
            player.setSum(totalSum(player));
            return player.getHand();
        }else{
            Card pickedCard1 = pickCard(player,deck);
            player.setHand(addToHand(player,pickedCard1));
            player.setSum(totalSum(player));
            return player.getHand();
        }
    }

    /**
     * Game start, set initial hands and check BlackJack
     * @param actionEvent
     */
    public void gameStart(ActionEvent actionEvent) {
        System.out.println("deckOfCards start with " + Deck.getDeckOfCards().size() + " !");
        System.out.println("Game Start!");
        // Hide Start Button after game starting
        btnStart.setVisible(false);
        for (int i = 0; i < 3 ; i++) {
            InitialHand(players.get(i));
        }

        /// Player1 & Player2 get Black Jack
        if(Judge.isBlackJack(players, "Player1") && Judge.isBlackJack(players, "Player2")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player1 and Player2 BlackJack");
            alert.setHeaderText("Player1 and Player2 BlackJack!\nClick 'Start' to play again. ");
            alert.showAndWait();
            btnStart.setVisible(true);
            //--- Initialize ---s
            Deck deck = new Deck();
            players.add(new Player("Player1", new ArrayList<Card>(), 0));
            players.add(new Player("Player2", new ArrayList<Card>(),0));
            players.add(new Player("Dealer", new ArrayList<Card>(), 0));

            playerBtns = new ArrayList<>(Arrays.asList(btnStand1, btnStand2, btnHit1, btnHit2));
            initWindow();
            initEnable();
            //---------------
        }
        // Player1 BlackJack
        else  if (Judge.isBlackJack(players, "Player1")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player1 BlackJack");
            alert.setHeaderText("Player1 BlackJack!\nNow it's Dealer v.s. Player2.");
            alert.showAndWait();
            players.remove(0);
            System.out.println(players);
            playerInt = 0;
            enableDisableButton(getTurn(playerInt));
        }
        // Player2 BlackJack
        else if (Judge.isBlackJack(players, "Player2")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player2 BlackJack");
            alert.setHeaderText("Player2 BlackJack!\nNow it's Dealer v.s. Player1.");
            alert.showAndWait();
            players.remove(1);
            System.out.println(players);
            playerInt = 0;
            enableDisableButton(getTurn(playerInt));
        }
        // Dealer BlackJack
        else if (Judge.isBlackJack(players, "Dealer")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dealer BlackJack");
            alert.setHeaderText("Dealer BlackJack!\nClick 'Start' to play again.");
            alert.showAndWait();
            btnStart.setVisible(true);
            //--- Initialize ---s
            Deck deck = new Deck();
            players.add(new Player("Player1", new ArrayList<Card>(), 0));
            players.add(new Player("Player2", new ArrayList<Card>(), 0));
            players.add(new Player("Dealer", new ArrayList<Card>(), 0));

            playerBtns = new ArrayList<>(Arrays.asList(btnStand1, btnStand2, btnHit1, btnHit2));
            initWindow();
            initEnable();
            //---------------
        } else {
            playerInt = 0;
            enableDisableButton(getTurn(playerInt));
            System.out.println(players);
        }
    }

    public Player getTurn(int playerInt){
        return players.get(playerInt);
    }

    public void enableDisableButton(Player player){
        if(player.getName().equals("Player1")){
            btnHit1.setDisable(false);
            btnStand1.setDisable(false);
            btnHit2.setDisable(true);
            btnStand2.setDisable(true);
        }
        else if (player.getName().equals("Player2")){
            btnHit2.setDisable(false);
            btnStand2.setDisable(false);
            btnHit1.setDisable(true);
            btnStand1.setDisable(true);
        } else {
            btnHit1.setDisable(true);
            btnStand1.setDisable(true);
            btnHit2.setDisable(true);
            btnStand2.setDisable(true);

            if(totalSum(player) < 17) {

                Card pickedCard = pickCard(player, deck);
                player.setHand(addToHand(player, pickedCard));
                player.setSum(totalSum(player));
                playerInt = 0;
                enableDisableButton(players.get(playerInt));
            }
            Judge.gameResult(players);
        }
    }

    /**
     * Randomly pick up a card from deck of cards, set a new deck of cards
     * @param player
     * @param deck
     * @return pickedCard
     */
    public Card pickCard(Player player, Deck deck){
        Random rand = new Random();
        int int_random = rand.nextInt(Deck.getDeckOfCards().size());
        Card pickedCard = Deck.getDeckOfCards().get(int_random);
        Deck.getDeckOfCards().remove(int_random);
        Deck.setDeckOfCards(Deck.getDeckOfCards());
        // by Kazunobu
        labelDeck.setText(String.valueOf(Deck.getDeckOfCards().size()));
        return pickedCard;
    }

    /**
     * Add a Card to player's hand
     * @param player
     * @param card
     * @return player's hand
     */
    public ArrayList<Card> addToHand(Player player, Card card){
        ArrayList<Card> newHand = player.getHand();
        newHand.add(card);
        return newHand;
    }

    /**
     * calculate sum of players hands
     * @param player
     * @return player's sum
     */
    public int totalSum(Player player){
        int newSum = 0;
        for (int i = 0; i < player.getHand().size();i++){
            newSum = newSum + player.getHand().get(i).getRank();
        }
        // by Kazunobu
        displaySum(player, newSum);
        return newSum;
    }

    // display sum of Player1, 2 and dealer's card value
    public void displaySum(Player player, int sum){
        if(player.getName().equals("Player1")){
            labelSum1.setText(String.valueOf(sum));
        }else if(player.getName().equals("Player2")){
            labelSum2.setText(String.valueOf(sum));
        }else{
            labelSumDealer.setText(String.valueOf(sum));
        }
    }

    public void hitClicked(ActionEvent actionEvent) {
        player = getTurn(playerInt);
        player.setHand(addToHand(player, pickCard(player, deck)));
        player.setSum(totalSum(player));
        System.out.println(player);

        if (Judge.isBusted(player)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(player.getName() + " Bust");
            alert.setHeaderText(player.getName() + " Busted!");
            alert.showAndWait();
            players.remove(player);
            enableDisableButton(getTurn(playerInt));
        }
    }

    /**
     * Skip turn
     * @param actionEvent
     */
    public void standClicked(ActionEvent actionEvent) {
        Button hitClicked = (Button) actionEvent.getSource();
        String hitButtonId = hitClicked.getId();
        if(hitButtonId.equals("btnStand1")){
            playerInt++;
            System.out.println("btnStand1 clicked");
        } else if(hitButtonId.equals("btnStand2")){
            playerInt++;
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
}