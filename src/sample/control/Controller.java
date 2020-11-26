package sample.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import sample.model.Card;
import sample.model.Deck;
import sample.model.Judge;
import sample.model.Player;
import sample.view.HelpWindow;
import java.net.URL;
import java.util.*;


public class Controller implements Initializable {
    @FXML
    public AnchorPane anchorPaneMain;
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
    @FXML
    public HBox hBoxHand1;
    @FXML
    public HBox hBoxHand2;
    @FXML
    public HBox hBoxHandDealer;
    @FXML
    public ImageView imgViewPlayer1;
    @FXML
    public ImageView imgViewPlayer2;
    @FXML
    public ImageView imgViewDealer;

    private Deck deck;
    private ArrayList<Player> players = new ArrayList<>();
    private int playerInt;
    private Player player;
    private ArrayList<Button> playerBtns = new ArrayList<>();
    private ArrayList<HBox> playerHands = new ArrayList<>();
    private int blackJackCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deck = new Deck();
        players.add(new Player("Player1", new ArrayList<Card>(), 0));
        players.add(new Player("Player2", new ArrayList<Card>(),0));
        players.add(new Player("Dealer", new ArrayList<Card>(), 0));

        playerBtns = new ArrayList<>(Arrays.asList(btnStand1, btnStand2, btnHit1, btnHit2));
        playerHands = new ArrayList<>(Arrays.asList(hBoxHand1, hBoxHand2, hBoxHandDealer));
        initializeDisplay();
        playerInt = 0;
        blackJackCount = 0;
    }

    /**
     * Set controllers as initial display
     */
    private void initializeDisplay() {
        for (Button b : playerBtns) {
            b.setDisable(true);
        }
        imgViewPlayer1.setVisible(true);
        imgViewPlayer2.setVisible(true);
        imgViewDealer.setVisible(true);
        labelDeck.setVisible(false);
        labelSum1.setVisible(false);
        labelSum2.setVisible(false);
        labelSumDealer.setVisible(false);
        hBoxHand1.setVisible(false);
        hBoxHand2.setVisible(false);
        hBoxHandDealer.setVisible(false);

        labelDeck.setText(String.valueOf(Deck.getDeckOfCards().size()));
        labelSum1.setText("0");
        labelSum2.setText("0");
        labelSumDealer.setText("0");
        hBoxHand1.getChildren().clear();
        hBoxHand2.getChildren().clear();
        hBoxHandDealer.getChildren().clear();
    }

    /**
     * Reset the display before staring game
     */
    private void resetDisplay() {
        imgViewPlayer1.setVisible(false);
        imgViewPlayer2.setVisible(false);
        imgViewDealer.setVisible(false);
        labelDeck.setVisible(true);
        labelSum1.setVisible(true);
        labelSum2.setVisible(true);
        labelSumDealer.setVisible(true);
        hBoxHand1.setVisible(true);
        hBoxHand2.setVisible(true);
        hBoxHandDealer.setVisible(true);
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
        // Hide Start Button after game starting
        btnStart.setVisible(false);
        resetDisplay();
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
            // restart
            restartGame();
        }
        // Player1 BlackJack
        else  if (Judge.isBlackJack(players, "Player1")){
            blackJackCount++;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player1 BlackJack");
            alert.setHeaderText("Player1 BlackJack!\nNow it's Dealer v.s. Player2.");
            alert.showAndWait();
            removePlayer(0);
            playerInt = 0;
            enableDisableButton(getTurn(playerInt));
            if (Judge.isBusted(players.get(0))) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Player2 Busted");
                alert.setHeaderText("Oh no! Player2 is Busted!");
                alert.showAndWait();
                removePlayer(players.get(0));
                if (players.size() == 1) {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Player1 Won");
                    alert.setHeaderText("Player1 Won with BlackJack! Let's play again!");
                    alert.showAndWait();
                    restartGame();
                }
            }
        }
        // Player2 BlackJack
        else if (Judge.isBlackJack(players, "Player2")){
            blackJackCount++;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Player2 BlackJack");
            alert.setHeaderText("Player2 BlackJack!\nNow it's Dealer v.s. Player1.");
            alert.showAndWait();
            removePlayer(1);
            playerInt = 0;
            enableDisableButton(getTurn(playerInt));
            if (Judge.isBusted(players.get(0))) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Player1 Busted");
                alert.setHeaderText("Oh no! Player1 is Busted!");
                alert.showAndWait();
                removePlayer(players.get(0));
                if (players.size() == 1) {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Player2 Won");
                    alert.setHeaderText("Player2 Won with BlackJack! Let's play again!");
                    alert.showAndWait();
                    restartGame();
                }
            }
        }
        else {
            playerInt = 0;
            enableDisableButton(getTurn(playerInt));
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

            while (totalSum(player) < 17) {
                Card pickedCard = pickCard(player, deck);
                player.setHand(addToHand(player, pickedCard));
                player.setSum(totalSum(player));
            }
            // check game result
            Judge.gameResult(players);
            // restart
            restartGame();
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
        // Display hand
        updateHandDisplay(player, card);
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


        if (Judge.isBusted(player)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(player.getName() + " Bust");
            alert.setHeaderText(player.getName() + " Busted!");
            alert.showAndWait();
            removePlayer(player);

            if(players.size() == 1 && blackJackCount == 1){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dealer vs " + player.getName() + ": " +  player.getName() +  " bust");
                alert.setHeaderText(player.getName() + " busted, Dealer wins!!");
                alert.showAndWait();
                restartGame();
            }
            if(players.size() == 1){
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Players bust");
                alert.setHeaderText("Both players busted, Dealer wins!!");
                alert.showAndWait();
                restartGame();
            }
        } else {
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
        if(hitButtonId.equals("btnStand1") || hitButtonId.equals("btnStand2") ){
            playerInt++;
        }
        enableDisableButton(getTurn(playerInt));
    }


    public void escClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to quit this game?");
        ButtonType continueBtn = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
        ButtonType quitBtn = new ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(continueBtn,quitBtn);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == quitBtn){
            System.exit(0);
        }
    }


    /**
     * Display help window
     * @param actionEvent
     */
    public void helpClicked(ActionEvent actionEvent) {
        HelpWindow.displayHelp(actionEvent, getClass());
    }

    /**
     * Update the display of hand
     *
     * @param newCard
     */
    private void updateHandDisplay(Player player, Card newCard) {
        int i = players.indexOf(player);
        HBox hand = playerHands.get(i);
//        double[] position = {hand.getLayoutX(), hand.getLayoutY()};
        List<Node> cards = hand.getChildren();
        if (cards.size() > 0) {
            // Multiple cards: update the current top card to move to under
            Pane lastCard = (Pane) cards.get(cards.size() - 1);
            lastCard.getStyleClass().add("under");
        }
        // add a new card to top
        Pane card = generateCard(newCard);
        hand.getChildren().add(card);

    }
    /**
     * Generate Pane of a new card
     *
     * @param newCard
     * @return
     */
    private Pane generateCard(Card newCard) {
        Label txtValue = new Label();
        txtValue.getStyleClass().add("hand-text-label");
        txtValue.setText(newCard.getValue());

        Label txtSuit = new Label();
        txtSuit.getStyleClass().add("hand-text-label");
        txtSuit.setText(newCard.getSuit());

        if (newCard.getSuit().equals(Deck.HEARTS) || newCard.getSuit().equals(Deck.DIAMONDS)) {
            txtValue.getStyleClass().add("red");
            txtSuit.getStyleClass().add("red");
        }

        TilePane card = new TilePane();
        card.getStyleClass().add("hand-tilepane");
        card.getChildren().addAll(txtValue, txtSuit);
        return card;
    }

    /**
     * Restart Game: show Start button
     */
    private void restartGame() {
        deck = new Deck();
        players = new ArrayList<>();
        players.add(new Player("Player1", new ArrayList<Card>(), 0));
        players.add(new Player("Player2", new ArrayList<Card>(), 0));
        players.add(new Player("Dealer", new ArrayList<Card>(), 0));

        playerBtns = new ArrayList<>(Arrays.asList(btnStand1, btnStand2, btnHit1, btnHit2));
        playerHands = new ArrayList<>(Arrays.asList(hBoxHand1, hBoxHand2, hBoxHandDealer));

        initializeDisplay();
        btnStart.setVisible(true);
        playerInt = 0;
        blackJackCount = 0;
    }

    /**
     * Remove player by player
     * @param player
     */
    private void removePlayer(Player player) {
        removePlayer(players.indexOf(player));
    }

    /**
     * Remove player by index
     * Remove Hand controller to keep the corresponding index with Players list
     * @param index
     */
    private void removePlayer(int index) {
        players.remove(index);
//        playerBtns.remove(index); // should be removed as well but need to switch to 2D array instead of ArrayList
        playerHands.remove(index);
    }
}