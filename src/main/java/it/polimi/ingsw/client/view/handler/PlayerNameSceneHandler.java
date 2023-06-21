package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.EventID;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.rmi.RemoteException;


/**
 * @author Abdullah Nasr
 */
public class PlayerNameSceneHandler extends SceneHandler{

    @FXML
    private ImageView logo;
    @FXML
    private Text username_lbl;
    @FXML
    private ImageView username_lbl_background;
    @FXML
    private TextField username_txt;
    @FXML
    private ImageView username_txt_background;
    @FXML
    private Button back_btn;
    @FXML
    private Button next_btn;
    @FXML
    private AnchorPane pane;

    /**
     * @author Abdullah Nasr
     */
    public void back()
    {
        SceneHandler.switchScene("home");
    }

    public void next(){

        try {
            getGui().setPlayerName(username_txt.getText());
            getClientController().update(new Event(EventID.CHOOSE_PLAYER_NAME, username_txt.getText()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void resize()
    {

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
        logo.setFitWidth(logo.getFitWidth()*ratio_x);
        logo.setFitHeight(logo.getFitHeight()*ratio_y);

        username_lbl_background.setFitWidth(username_lbl_background.getFitWidth()*ratio_x);
        username_lbl_background.setFitHeight(username_lbl_background.getFitHeight()*ratio_y);

        username_lbl.setFont(Font.font(username_lbl.getFont().getFamily(), username_lbl.getFont().getSize()*ratio_y));
        username_lbl.setLayoutX(username_lbl_background.getLayoutX()+ ((username_lbl_background.getFitWidth()- username_lbl.getBoundsInLocal().getWidth())/2));

        username_txt.setFont(Font.font(username_txt.getFont().getFamily(), username_txt.getFont().getSize()*ratio_y));
        username_txt_background.setFitWidth(username_txt_background.getFitWidth()*ratio_x);
        username_txt_background.setFitHeight(username_txt_background.getFitHeight()*ratio_y);
        username_txt.setPrefWidth(username_txt.getPrefWidth()*ratio_x);
        username_txt.setPrefHeight(username_txt.getPrefHeight()*ratio_y);

        back_btn.setPrefWidth(back_btn.getPrefWidth()*ratio_x);
        back_btn.setPrefHeight(back_btn.getPrefHeight()*ratio_y);
        next_btn.setPrefWidth(next_btn.getPrefWidth()*ratio_x);
        next_btn.setPrefHeight(next_btn.getPrefHeight()*ratio_y);

    }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void runScene() {

        username_txt.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                next();
            }
        });

        resize();

        //getScene().setRoot(getRoot());
        getStage().setScene(new Scene(getRoot()));

    }
}
