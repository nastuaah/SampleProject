package com.example.sampleproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static com.example.sampleproject.CardInfo.mask;
import static com.example.sampleproject.CardInfo.nominal;

public class GameWindowController {

    private static int attacker;

    @FXML
    private Button add1;

    @FXML
    private Button add2;

    @FXML
    private GridPane deskAttackCardPane;

    private final ArrayList<String> already_use = new ArrayList<>();
    private final ArrayList<String> has_first = new ArrayList<>();
    private final ArrayList<String> has_second = new ArrayList<>();

    @FXML
    private GridPane deskAnswerCardPane;

    @FXML
    private ScrollPane firstPlayerScroll;

    @FXML
    private ScrollPane secondPlayerScroll;

    @FXML
    private FlowPane firstPlayerPane;

    @FXML
    private FlowPane secondPlayerPane;

    @FXML
    void addCard1() throws IOException {
        while (has_first.size() < 6) {
            String card = availableCard();
            if (!card.equals("")){
                has_first.add(card);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Card.fxml"));

                Pane firstPane = loader.load();
                CardController cardController = loader.getController();
                cardController.setCardParameters(card, this, firstPane);
                firstPlayerPane.getChildren().add(firstPane);
                firstPlayerScroll = new ScrollPane();
                firstPlayerScroll.setContent(firstPlayerPane);
            }else{
                add1.setVisible(false);
            }
        }
        add1.setDisable(true);
    }
    @FXML
    void addCard2() throws IOException {
        while (has_second.size() < 6) {
            String card = availableCard();
            if (!card.equals("")){
                has_second.add(card);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Card.fxml"));

                Pane secondPane = loader.load();
                CardController cardController = loader.getController();
                cardController.setCardParameters(availableCard(), this, secondPane);
                secondPlayerPane.getChildren().add(secondPane);
                secondPlayerScroll = new ScrollPane();
                secondPlayerScroll.setContent(secondPlayerPane);
            }else{
                add2.setVisible(false);
            }
        }
        add2.setDisable(true);
    }
    public void addCardOnTable(CardController card) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Card.fxml"));
        Pane newPane = loader.load();
        CardController cardController = loader.getController();
        String str_card = card.getNominal() + "_" + card.getMask();
        cardController.setCardParameters(str_card, this, newPane);
        if (has_first.contains(str_card)) {
            if (attacker == 1)
                deskAttackCardPane.add(newPane, deskAttackCardPane.getChildren().size(), 0);
            else
                deskAnswerCardPane.add(newPane, deskAnswerCardPane.getChildren().size(), 0);
            has_first.remove(str_card);
            if (has_first.size() < 6)
                add1.setDisable(false);
        }else{
            if (attacker == 2){
                deskAttackCardPane.add(newPane, deskAttackCardPane.getChildren().size(), 0);
            }else{
                deskAnswerCardPane.add(newPane, deskAnswerCardPane.getChildren().size(), 0);
            }
            has_second.remove(str_card);
            if (has_second.size() < 6)
                add2.setDisable(false);
        }
    }
    public static void attack(){
        Random random = new Random();
        attacker = random.nextInt(2) + 1;
    }
    private String availableCard(){

        Random random = new Random();
        String card = "";
        while (already_use.size() < 36) {
            int nominalIndex = random.nextInt(nominal.length);
            int maskIndex = random.nextInt(mask.length);
            card = nominal[nominalIndex] + "_" + mask[maskIndex];
            if (!already_use.contains(card)) {
                already_use.add(card);
                break;
            }
        }
        return card;
    }
}
