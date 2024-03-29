package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.general.GameRoom;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class WaitPlayersSceneHandler extends SceneHandler{

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView info_background;

    @FXML
    private Label wait_players_lbl;

    @FXML
    private Label entered_players_lbl;

    @FXML
    private Label max_players_lbl;

    @FXML
    private Label creator_lbl;

    @FXML
    private Label room_name_lbl;

    @FXML
    private Label room_name;

    @FXML
    private Label creator;

    @FXML
    private Label max_players;

    @FXML
    private Label entered_players;

    @FXML
    private ImageView logo;


    private static GameRoom room;

    /**
     * Set the game room
     * @param room The game room
     * @author Abdullah Nasr
     */
    public static void setRoom(GameRoom room) {
       WaitPlayersSceneHandler.room = room;
    }

    /**
     * @author Abdullah Nasr
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

        logo.setFitWidth(logo.getFitWidth()*ratio_x);
        logo.setFitHeight(logo.getFitHeight()*ratio_y);

        info_background.setFitWidth(info_background.getFitWidth()*ratio_x);
        info_background.setFitHeight(info_background.getFitHeight()*ratio_y);
        room_name_lbl.setFont(Font.font(room_name_lbl.getFont().getFamily(), room_name_lbl.getFont().getSize()*ratio_y));
        room_name.setFont(Font.font(room_name.getFont().getFamily(), room_name.getFont().getSize()*ratio_y));
        creator_lbl.setFont(Font.font(creator_lbl.getFont().getFamily(), creator_lbl.getFont().getSize()*ratio_y));
        creator.setFont(Font.font(creator.getFont().getFamily(), creator.getFont().getSize()*ratio_y));
        max_players_lbl.setFont(Font.font(max_players_lbl.getFont().getFamily(), max_players_lbl.getFont().getSize()*ratio_y));
        max_players.setFont(Font.font(max_players.getFont().getFamily(), max_players.getFont().getSize()*ratio_y));
        entered_players_lbl.setFont(Font.font(entered_players_lbl.getFont().getFamily(), entered_players_lbl.getFont().getSize()*ratio_y));
        entered_players.setFont(Font.font(entered_players.getFont().getFamily(), entered_players.getFont().getSize()*ratio_y));
        wait_players_lbl.setFont(Font.font(wait_players_lbl.getFont().getFamily(), wait_players_lbl.getFont().getSize()*ratio_y));


    }

    /**
     * It updates the information about the room as the name, creator's name, number of players and
     * the current players that are in.
     * @author Abdullah Nasr
     */
    public void updateInfo()
    {
        StringBuilder players = new StringBuilder();

        for(String currentPlayer : room.enteredPlayers())
        {
            players.append(currentPlayer).append(",");
        }

        //remove last comma
        String playersNames = players.substring(0,players.length()-1);

        room_name.setText(room.gameRoomName());
        creator.setText(room.creatorName());
        max_players.setText(String.valueOf(room.totalPlayers()));
        entered_players.setText(playersNames);

    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void runScene() {


        resize();
        updateInfo();

        getScene().setRoot(getRoot());
        //getStage().setScene(new Scene(getRoot()));

    }
}
