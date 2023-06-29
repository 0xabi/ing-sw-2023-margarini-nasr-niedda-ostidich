package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.EventID;
import it.polimi.ingsw.general.GameRoom;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class ChooseGameRoomSceneHandler extends SceneHandler{

    private String selectedLobby;
    private LinkedList<String> lobbys;
    private static List<GameRoom> gameRooms;
    @FXML
    private Button backBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private ListView<String> lobbyList;
    @FXML
    private AnchorPane pane;
    @FXML
    private Label availableLobbyLabel;
    @FXML
    private Button joinBtn;
    private boolean lobbyHasBeenSelected = false;

    /**
     * Prepares a list of all the available gamerooms.
     * @param gameRooms is a list of the available rooms received from the server
     * @author Pietro Andrea Niedda
     */
    public static void setGameRooms(List<GameRoom> gameRooms){
        ChooseGameRoomSceneHandler.gameRooms = gameRooms;
    }

    /**
     * Prepares a string that contains: the gameroom name, the name of its creator and the number of players
     * already entered.
     * @author Pietro Andrea Niedda
     */
    private void getLobbies(){
        for(GameRoom gameRoom: gameRooms) lobbys.add(gameRoom.gameRoomName() + "\t" + gameRoom.creatorName() +
                "\t" + gameRoom.enteredPlayers().size() + "/" + gameRoom.totalPlayers());
    }

    /**
     * Allows to visualize and select the gameroom via the ListView.
     * @author Pietro Andrea Niedda
     */
    public void updateList(){
        lobbys = new LinkedList<>();
        getLobbies();
        lobbyList.getItems().addAll(lobbys);
        lobbyList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            lobbyHasBeenSelected = true;
            selectedLobby = lobbyList.getSelectionModel().getSelectedItem().split("\t", 2)[0];
        });
    }

    /**
     * Allows to join the previously selected gameroom.
     * @author Pietro Andrea Niedda
     */
    public void join(){
        if(!lobbyHasBeenSelected) return;
        try {
            getClientController().update(new Event(EventID.CHOOSE_GAME_ROOM, selectedLobby));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Allows to refresh the list of available lobbies.
     * @author Pietro Andrea Niedda
     */
    public void refresh(){
        try {
            getClientController().update(new Event(EventID.CHOOSE_GAME_ROOM, "refresh"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used to return to the previous scene.
     * @author Pietro Andrea Niedda
     */
    public void back(){
        SceneHandler.switchScene("new_or_join");
    }

    /**
     * @author Pietro Andrea Niedda
     */
    @Override
    public void runScene() {
        updateList();
        resize();
        getScene().setRoot(getRoot());
        //getStage().setScene(new Scene(getRoot()));

    }

    /**
     * @author Pietro Andrea Niedda
     */
    @Override
    public void resize() {
        //noinspection DuplicatedCode
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double ratio_x = bounds.getWidth()/pane.getPrefWidth();
        double ratio_y =  bounds.getHeight()/pane.getPrefHeight();

        //re-positioning position according to the resolution
        for(Node n: pane.getChildren())
        {
            n.setLayoutX(n.getLayoutX()*ratio_x);
            n.setLayoutY(n.getLayoutY()*ratio_y);
        }

        //re-sizing according to the resolution
        joinBtn.setFont(Font.font(joinBtn.getFont().getFamily(), joinBtn.getFont().getSize()*ratio_x));
        backBtn.setFont(Font.font(backBtn.getFont().getFamily(), backBtn.getFont().getSize()*ratio_x));
        refreshBtn.setFont(Font.font(refreshBtn.getFont().getFamily(), refreshBtn.getFont().getSize()*ratio_x));

        lobbyList.setPrefWidth(lobbyList.getPrefWidth()*ratio_x);
        lobbyList.setPrefHeight(lobbyList.getPrefHeight()*ratio_y);

        availableLobbyLabel.setFont(Font.font(availableLobbyLabel.getFont().getFamily(), availableLobbyLabel.getFont().getSize()*ratio_y));
    }
}
