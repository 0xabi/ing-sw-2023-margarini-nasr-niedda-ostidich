package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.resources.Event;
import it.polimi.ingsw.resources.EventID;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

/**
 * @author Abdullah Nasr
 */
public class HomeSceneHandler extends SceneHandler{

    @FXML
    private Button start_btn;
    @FXML
    private Button quit_btn;
    @FXML
    private ImageView logo;
    @FXML
    private AnchorPane pane;

    /**
     * @author Abdullah Nasr
     */
    public void start() throws Exception {
        getClientController().update(new Event(EventID.START,null));
    }

    /**
     * @author Abdullah Nasr
     */
    public void exit()
    {
        Platform.exit();
    }

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
        start_btn.setPrefWidth(start_btn.getPrefWidth() * ratio_x);
        start_btn.setPrefHeight(start_btn.getPrefHeight() * ratio_y);
        quit_btn.setPrefWidth(quit_btn.getPrefWidth() * ratio_x);
        quit_btn.setPrefHeight(quit_btn.getPrefHeight() * ratio_y);
        logo.setFitWidth(logo.getFitWidth()*ratio_x);
        logo.setFitHeight(logo.getFitHeight()*ratio_y);

    }

    @Override
    public void runScene()
    {
        resize();
        getScene().setRoot(getRoot());


    }
}
