package it.polimi.ingsw.client.view.handler;


import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class MatchSceneHandler extends SceneHandler {

    @FXML
    private ImageView personalGoal;
    @FXML
    private ImageView mainShelf;
    @FXML
    private ImageView shelfPlayer1;
    @FXML
    private ImageView shelfPlayer2;
    @FXML
    private ImageView shelfPlayer3;
    @FXML
    private ImageView commonGoal1;
    @FXML
    private ImageView commonGoal2;
    @FXML
    private ImageView board;
    @FXML
    private Label mainPlayerLbl;
    @FXML
    private Label player1Lbl;
    @FXML
    private Label player2Lbl;
    @FXML
    private Label player3Lbl;

    @FXML
    private AnchorPane pane;


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void resize() {

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double ratio_x = bounds.getWidth()/pane.getPrefWidth();
        double ratio_y =  bounds.getHeight()/pane.getPrefHeight();


        for(Node n: pane.getChildren())
        {
            //re-positioning position according to the resolution
            n.setLayoutX(n.getLayoutX()*ratio_x);
            n.setLayoutY(n.getLayoutY()*ratio_y);

            //re-sizing according to the resolution
            if(n instanceof ImageView currentImage)
            {
                currentImage.setFitWidth(currentImage.getFitWidth()*ratio_x);
                currentImage.setFitHeight(currentImage.getFitHeight()*ratio_y);
            }
            else if(n instanceof Label currentLabel)
            {
                currentLabel.setPrefWidth(currentLabel.getPrefHeight()*ratio_x);
                currentLabel.setPrefHeight(currentLabel.getPrefHeight()*ratio_y);
                currentLabel.setFont(new Font(currentLabel.getFont().getFamily(),currentLabel.getFont().getSize()*ratio_y));
            }
        }

        mainPlayerLbl.setLayoutX(mainPlayerLbl.getLayoutX() - (mainPlayerLbl.getPrefWidth()/2));
        player1Lbl.setLayoutX(player1Lbl.getLayoutX() - (player1Lbl.getPrefWidth()/2));
        player2Lbl.setLayoutX(player2Lbl.getLayoutX() - (player2Lbl.getPrefWidth()/2));
        player3Lbl.setLayoutX(player3Lbl.getLayoutX() - (player3Lbl.getPrefWidth()/2));

        mainPlayerLbl.setLayoutY(mainPlayerLbl.getLayoutY() - (mainPlayerLbl.getPrefHeight()/2));
        player1Lbl.setLayoutY(player1Lbl.getLayoutY() - (player1Lbl.getPrefHeight()/2));
        player2Lbl.setLayoutY(player2Lbl.getLayoutY() - (player2Lbl.getPrefHeight()/2));
        player3Lbl.setLayoutY(player3Lbl.getLayoutY() - (player3Lbl.getPrefHeight()/2));


    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void runScene() {
        resize();
        getScene().setRoot(getRoot());

    }
}
