package it.polimi.ingsw.client.view;

import it.polimi.ingsw.Main;
import it.polimi.ingsw.client.view.handler.AssignPointsSceneHandler;
import it.polimi.ingsw.client.view.handler.ChooseGameRoomSceneHandler;
import it.polimi.ingsw.client.view.handler.SceneHandler;
import it.polimi.ingsw.client.view.handler.WaitPlayersSceneHandler;
import it.polimi.ingsw.client.view.handler.match.MatchSceneHandler;
import it.polimi.ingsw.general.GameRoom;
import it.polimi.ingsw.general.Tile;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class GUI extends GameClientView {

    private final Map<String, Integer> commonGoalAssignationReminder = new HashMap<>();

    public GUI() {
        super();
    }

    @Override
    public void start() {
        SceneHandler.setClientController(getClientController());

    }

    @Override
    public void chooseIPAddress() {


    }

    @Override
    public void choosePlayerName() {
        //SceneHandler.switchScene("match");

        Platform.runLater(() ->
                SceneHandler.switchScene("player_name"));

    }

    @Override
    public void chooseNewOrJoin() {


        Platform.runLater(() -> SceneHandler.switchScene("new_or_join"));


    }

    @Override
    public void chooseNewGameName() {

        Platform.runLater(() ->
                System.out.println("choosenewgame"));



    }

    @Override
    public void chooseNewGamePlayerNumber() {

        Platform.runLater(() ->
                SceneHandler.switchScene("game_ip_connection"));

    }

    @Override
    public void showUpdatedGameRoom(String roomName) {


        Platform.runLater(() ->
        {
            GameRoom room = null;
            for (GameRoom gameRoom : getGameRooms()) {
                if (gameRoom.gameRoomName().equals(roomName)) {
                    room = gameRoom;
                    break;
                }
            }
            if (room == null) throw new RuntimeException("no such room name present while printing personal room!");
            WaitPlayersSceneHandler.setRoom(room);

            SceneHandler.switchScene("wait_players");
        });



    }

    @Override
    public void notifyGameHasStared() { 



        Platform.runLater(() ->{

            SceneHandler.switchScene("match");
            MatchSceneHandler.setNumOfPlayers(getNames().size());
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();

            msh.initPersonalGoal(getPlayerPersonalGoals().get(0));
            msh.initOpponentPlayers();
            msh.setImageCommonGoals(getCommonGoal1(),getCommonGoal2());
            msh.initChair();
            msh.initScoringTokens();

        });
    }

    /**
     * When invoked, this method switches the scene to the one in which the player has to select the gameroom he wants
     * to join.
     * @param rooms is the game rooms availability list
     * @author Pietro Andrea Niedda
     */
    @Override
    public void chooseGameRoom(List<GameRoom> rooms) {

        Platform.runLater(() ->
        {
            ChooseGameRoomSceneHandler.setGameRooms(rooms);
            SceneHandler.switchScene("choose_game_room");
        });

    }

    @Override
    public void pickTiles(int availablePickNumber) {

        Platform.runLater(() ->{

            MatchSceneHandler.setPickPhase(true);
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            MatchSceneHandler.setAvailableTiles(availablePickNumber);

            msh.checkSelection();
            msh.updateBoard();
            msh.updateOpponentBoards();
            msh.updateCommonGoals(getCommonGoal1GivenPlayers(),getCommonGoal2GivenPlayers());
            msh.updateEndGameToken();
        });


    }

    @Override
    public void chooseOrder(List<Tile> selection) {

        Platform.runLater(() ->{
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.chooseOrder();
        });


    }

    @Override
    public void chooseColumn() {


        Platform.runLater(() ->{
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.changeAdviseMsg("Choose column");
            msh.enableColumnsCursor();
            msh.checkColumnSelection(true);
        });


    }

    @Override
    public void playerIsPlaying(String playerName) {


        Platform.runLater(() -> {


            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            MatchSceneHandler.setPickPhase(false);
            Main.Watch.temp.stop();

            msh.checkColumnSelection(false);
            msh.changeAdviseMsg(playerName + " is currently playing his turn");
            msh.updateBoard();
            msh.updateOpponentBoards();
            msh.updateCommonGoals(getCommonGoal1GivenPlayers(),getCommonGoal2GivenPlayers());
            msh.updateEndGameToken();
        });

    }

    @Override
    public void updateEndGameToken(boolean present) {

        Platform.runLater(() ->
        {
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.setEndGameTokenIsTaken(!present);
        });
    }

    @Override
    public void updateEndGameTokenPlayer(String player) {

        Platform.runLater(() ->
        {
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.setEndGameTokeHolderName(player);
        });

    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {

    }

    /**
     *When invoked, store the amount of points scored from common goals from each player in the
     * commonGoalAssignationReminder variable.
     * @param playerName the name of the player
     * @param token the amount of points received from a specific common goal
     * @author Pietro Andrea Nieddakm
     */
    private void calculateCGPoints(String playerName, Integer token){

        if(commonGoalAssignationReminder.containsKey(playerName)) {
            commonGoalAssignationReminder.put(playerName, commonGoalAssignationReminder.get(playerName) + token);
        }

        else { commonGoalAssignationReminder.put(playerName, token); }

    }

    /**
     * This method is responsible for sending to the AssignPointsSceneHandler the information about the points each
     * player has scored from personal goal and common goals.
     * @param points is the personal goal assigned points map
     * @author Pietro Andrea Niedda
     */
    @Override
    public void assignPersonalGoalPoints(Map<String, Integer> points) {
        Platform.runLater(() -> {

            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.moveTiles();
            msh.clear();

            SceneHandler.switchScene("assign_points");

            AssignPointsSceneHandler apsh = (AssignPointsSceneHandler) SceneHandler.getCurrentHandler();

            for (Map.Entry<String, Integer> entry : points.entrySet())
                apsh.updatePGPoints(entry.getKey(), entry.getValue());

            for(Map.Entry<Integer, String> cgpoints: getCommonGoal1GivenPlayers().entrySet())
                calculateCGPoints(cgpoints.getValue(), cgpoints.getKey());

            for(Map.Entry<Integer, String> cgpoints: getCommonGoal2GivenPlayers().entrySet())
                calculateCGPoints(cgpoints.getValue(), cgpoints.getKey());

            for (Map.Entry<String, Integer> commonGoalPoints : commonGoalAssignationReminder.entrySet())
                apsh.updateCGPoints(commonGoalPoints.getKey(), commonGoalPoints.getValue());

        });
    }

    /**
     * This method is responsible for sending to the AssignPointsSceneHandler the information about the points each
     * player has scored from the tiles that are adjacent.
     * @param points is the adjacent goal assigned points map
     * @author Pietro Andrea Niedda
     */
    @Override
    public void assignAdjacentGoalPoints(Map<String, Integer> points) {

        Platform.runLater(() -> {

            AssignPointsSceneHandler apsh = (AssignPointsSceneHandler) SceneHandler.getCurrentHandler();

            for (Map.Entry<String, Integer> entry : points.entrySet())
                apsh.updateAGPoints(entry.getKey(), entry.getValue());

        });

    }

    /**
     * This method is responsible for sending to the AssignPointsSceneHandler the information about the total amount of
     * points each player has scored. Also informs the AssignPointsSceneHandler that it's time to update the scene,
     * showing the points each player has scored for each category.
     *player has scored from personal goal and common goals.
     * @param winnerName is the winner player's name string
     * @param points     is the players' end game points map
     * @author Pietro Andrea Niedda
     */
    @Override
    public void announceWinner(String winnerName, Map<String, Integer> points) {

        Platform.runLater(() -> {

            AssignPointsSceneHandler apsh = (AssignPointsSceneHandler) SceneHandler.getCurrentHandler();

            for (Map.Entry<String, Integer> entry : points.entrySet())
                apsh.updateinfo(entry.getKey(), entry.getValue());

            apsh.show();

        });

    }

    @Override
    public void chooseRMIorSocket() {
        SceneHandler.switchScene("connection");
    }


    @Override
    public void disconnected() {

    }

    @Override
    public void justScanChat() {}

    @Override
    public void justPrintChat() {
        Platform.runLater(() -> {
            MatchSceneHandler msh = (MatchSceneHandler) SceneHandler.getCurrentHandler();
            msh.updateChat(getChatMessages());
        });
    }
}