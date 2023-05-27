package it.polimi.ingsw.client.view.handler;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import javax.swing.text.html.ImageView;
import java.awt.*;

public class SettingsSceneHandler extends SceneHandler{


/*
logo
welcomeText
username_lbl_background
playerNumberLbl
connectionTypeLbl
connectionTypeBackground
playerNumberBackground
joinBtn
newBtn
twoPlayersCB
threePlayersCB
fourPlayersCB
RMICB
socketCB

 */
    @FXML
    private ImageView logo;
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView username_lbl_background;
    @FXML
    private Label playerNumberLbl;

    @FXML
    private Label connectionTypeLbl;

    @FXML
    private ImageView connectionTypeBackground;

    @FXML
    private ImageView playerNumberBackground;

    @FXML
    private Button joinBtn;

    @FXML
    private Button newBtn;

    @FXML
    private CheckBox twoPlayersCB;

    @FXML
    private CheckBox threePlayersCB;

    @FXML
    private CheckBox fourPlayersCB;

    @FXML
    private CheckBox RMICB;

    @FXML
    private CheckBox socketCB;

    public void setThreePlayers(){
        twoPlayersCB.setSelected(false);
        threePlayersCB.setSelected(true);
        fourPlayersCB.setSelected(false);
    }
    public void setFourPlayers(){
        twoPlayersCB.setSelected(false);
        threePlayersCB.setSelected(false);
        fourPlayersCB.setSelected(true);
    }
    public void setSocket(){
        socketCB.setSelected(true);
        RMICB.setSelected(false);
    }
    public void setRMI(){
        socketCB.setSelected(false);
        RMICB.setSelected(true);
    }


    @Override
    public void runScene() {

    }

    @Override
    public void resize() {

    }
}
