package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.Event;
import it.polimi.ingsw.resources.EventID;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GUI extends GameClientView {

    private Stage stage;
    public GUI(String network) {
        super(network);

    }
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setScene(Stage stage,String nameRes)
    {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/"+nameRes+".fxml")));
            stage.setScene(scene);
            //stage.show();
            //nextBtn = (Button)scene.lookup("#nextBtn");
            //nickTextBox = (TextField) scene.lookup("#nickTextBox");
            //nextBtn.setOnAction(e->getUsername());
            System.out.println(this+"Set Scene");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {


    }

    public void loaded()
    {
        getClientController().update(new Event(EventID.START,null));
    }

    @Override
    public void chooseIPAddress() {

    }

    @Override
    public void choosePlayerName() {

        setScene(stage,"player_name");
    }

    @Override
    public void chooseNewOrJoin() {

    }

    @Override
    public void chooseNewGameName() {

    }

    @Override
    public void chooseNewGamePlayerNumber() {

    }

    @Override
    public void notifyGameHasStared() {

    }

    @Override
    public void chooseGameRoom(List<GameRoom> rooms) {

    }

    @Override
    public void pickTiles(int availablePickNumber) {

    }

    @Override
    public void chooseOrder(List<Tile> selection) {

    }

    @Override
    public void chooseColumn() {

    }

    @Override
    public void playerIsPlaying(String playerName) {

    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {

    }

    @Override
    public void assignPersonalGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void assignAdjacentGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void announceWinner(String winnerName, Map<String, Integer> points) {

    }

    @Override
    public void justScanChat() {

    }

    @Override
    public void justPrintChat(String message) {

    }
}
