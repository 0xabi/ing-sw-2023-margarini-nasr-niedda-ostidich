package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.EventID;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

import java.rmi.RemoteException;

public class NewOrJoinHandler extends SceneHandler{

    @FXML
    private Button joinGameBtn;

    @FXML
    private Button newGameBtn;

    @FXML
    private Button home_btn;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane pane;


    /**
     * Switch to scene which you can create a new game
     * @author Abdullah Nasr
     */
    public void newGame()
    {
        try {
            getClientController().update(new Event(EventID.CHOOSE_NEW_OR_JOIN, "new"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Switch to scene which you can join a game
     * @author Abdullah Nasr
     */
    public void joinGame()
    {
        try {
            getClientController().update(new Event(EventID.CHOOSE_NEW_OR_JOIN, "join"));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Go back to home scene
     * @author Abdullah Nasr
     */
    public void exit()
    {
        SceneHandler.switchScene("home");
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


            //re-sizing according to the resolution
            home_btn.setPrefWidth(home_btn.getPrefWidth() * ratio_x);
            home_btn.setPrefHeight(home_btn.getPrefHeight() * ratio_y);
            newGameBtn.setPrefWidth(newGameBtn.getPrefWidth() * ratio_x);
            newGameBtn.setPrefHeight(newGameBtn.getPrefHeight() * ratio_y);
            joinGameBtn.setPrefWidth(joinGameBtn.getPrefWidth() * ratio_x);
            joinGameBtn.setPrefHeight(joinGameBtn.getPrefHeight() * ratio_y);

            logo.setFitWidth(logo.getFitWidth()*ratio_x);
            logo.setFitHeight(logo.getFitHeight()*ratio_y);


        }

    /**
     * @author Abdullah Nasr
     */
    @Override
    public void runScene() {
        resize();
        getStage().setScene(new Scene(getRoot()));
    }


}

