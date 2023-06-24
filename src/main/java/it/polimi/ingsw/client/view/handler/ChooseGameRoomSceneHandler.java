package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.EventID;
import it.polimi.ingsw.general.GameRoom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
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

    public static void setGameRooms(List<GameRoom> gameRooms){
        ChooseGameRoomSceneHandler.gameRooms = gameRooms;
    }
    private void getLobbys(){
        for(GameRoom gameRoom: gameRooms) lobbys.add(gameRoom.gameRoomName() + "\t" + gameRoom.creatorName() +
                "\t" + gameRoom.enteredPlayers().size() + "/" + gameRoom.totalPlayers());
    }
    public void updateList(){
        lobbys = new LinkedList<>();
        getLobbys();
        lobbyList.getItems().addAll(lobbys);
        lobbyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                lobbyHasBeenSelected = true;
                selectedLobby = lobbyList.getSelectionModel().getSelectedItem().split("\t", 2)[0];
            }
        });
    }
    public void join(){
        if(!lobbyHasBeenSelected) return;
        try {
            getClientController().update(new Event(EventID.CHOOSE_GAME_ROOM, selectedLobby));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh(){
        try {
            getClientController().update(new Event(EventID.CHOOSE_GAME_ROOM, "refresh"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(){
        SceneHandler.switchScene("new_or_join");
    }

    @Override
    public void runScene() {
        updateList();
        resize();
        //getScene().setRoot(getRoot());
        getStage().setScene(new Scene(getRoot()));

    }

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
