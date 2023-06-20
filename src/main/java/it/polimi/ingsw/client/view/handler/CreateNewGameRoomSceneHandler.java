package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.EventID;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.rmi.RemoteException;

public class CreateNewGameRoomSceneHandler extends SceneHandler {

    @FXML
    private ImageView logo;

    @FXML
    private Text game_name_lbl;

    @FXML
    private ImageView game_name_lbl_background;

    @FXML
    private TextField game_name_txt;

    @FXML
    private ImageView game_name_txt_background;


    @FXML
    private ImageView number_players_lbl_background;

    @FXML
    private CheckBox twoPlayersCB;

    @FXML
    private CheckBox threePlayersCB;

    @FXML
    private CheckBox fourPlayersCB;

    @FXML
    private Button next_btn;

    @FXML
    private Button back_btn;

    @FXML
    private AnchorPane pane;



    public void selectTwoPlayers()
    {
        twoPlayersCB.setSelected(true);
        threePlayersCB.setSelected(false);
        fourPlayersCB.setSelected(false);
    }

    public void selectThreePlayers()
    {
        twoPlayersCB.setSelected(false);
        threePlayersCB.setSelected(true);
        fourPlayersCB.setSelected(false);
    }

    public void selectFourPlayers()
    {
        twoPlayersCB.setSelected(false);
        threePlayersCB.setSelected(false);
        fourPlayersCB.setSelected(true);
    }
    public void next()
    {

        try {
            Object[] data = new Object[2];
            data[0] = game_name_txt.getText();
            if(twoPlayersCB.isSelected())
                data[1] = 2;
            else if (threePlayersCB.isSelected())
                data[1] = 3;
            else if (fourPlayersCB.isSelected())
                data[1] = 4;

            getClientController().update(new Event(EventID.CREATE_ROOM_NAME_NUMBER, data));


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void back()
    {
        SceneHandler.switchScene("home");
    }

    @Override
    public void runScene() {

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



        //re-sizing according to the resolution
        logo.setFitWidth(logo.getFitWidth()*ratio_x);
        logo.setFitHeight(logo.getFitHeight()*ratio_y);

        game_name_lbl_background.setFitWidth(game_name_lbl_background.getFitWidth()*ratio_x);
        game_name_lbl_background.setFitHeight(game_name_lbl_background.getFitHeight()*ratio_y);

        game_name_lbl.setFont(Font.font(game_name_lbl.getFont().getFamily(), game_name_lbl.getFont().getSize()*ratio_y));
        game_name_lbl.setLayoutX(game_name_lbl_background.getLayoutX()+ ((game_name_lbl_background.getFitWidth()- game_name_lbl.getBoundsInLocal().getWidth())/2));

        game_name_txt.setFont(Font.font(game_name_txt.getFont().getFamily(), game_name_txt.getFont().getSize()*ratio_y));
        game_name_txt_background.setFitWidth(game_name_txt_background.getFitWidth()*ratio_x);
        game_name_txt_background.setFitHeight(game_name_txt_background.getFitHeight()*ratio_y);
        game_name_txt.setPrefWidth(game_name_txt.getPrefWidth()*ratio_x);
        game_name_txt.setPrefHeight(game_name_txt.getPrefHeight()*ratio_y);

        number_players_lbl_background.setFitWidth(number_players_lbl_background.getFitWidth()*ratio_x);
        number_players_lbl_background.setFitHeight(number_players_lbl_background.getFitHeight()*ratio_y);
        //number_players_lbl.setFont(Font.font(number_players_lbl.getFont().getFamily(), number_players_lbl.getFont().getSize()*ratio_y/2));

        twoPlayersCB.setFont(Font.font(twoPlayersCB.getFont().getFamily(), twoPlayersCB.getFont().getSize()*ratio_y));
        twoPlayersCB.setPrefWidth(twoPlayersCB.getPrefWidth()*ratio_x);
        twoPlayersCB.setPrefHeight(twoPlayersCB.getPrefHeight()*ratio_y);


        threePlayersCB.setFont(Font.font(threePlayersCB.getFont().getFamily(), threePlayersCB.getFont().getSize()*ratio_y));
        threePlayersCB.setMinWidth(threePlayersCB.getPrefWidth()*ratio_x);
        threePlayersCB.setPrefHeight(threePlayersCB.getPrefHeight()*ratio_y);

        fourPlayersCB.setFont(Font.font(fourPlayersCB.getFont().getFamily(), fourPlayersCB.getFont().getSize()*ratio_y));
        fourPlayersCB.setPrefWidth(fourPlayersCB.getPrefWidth()*ratio_x);
        fourPlayersCB.setPrefHeight(fourPlayersCB.getPrefHeight()*ratio_y);

        back_btn.setPrefWidth(back_btn.getPrefWidth()*ratio_x);
        back_btn.setPrefHeight(back_btn.getPrefHeight()*ratio_y);
        next_btn.setPrefWidth(next_btn.getPrefWidth()*ratio_x);
        next_btn.setPrefHeight(next_btn.getPrefHeight()*ratio_y);


        //re-positioning position according to the resolution
        for(Node n: pane.getChildren())
        {
            n.setLayoutX(n.getLayoutX()*ratio_x);
            n.setLayoutY(n.getLayoutY()*ratio_y);
        }

    }
}
