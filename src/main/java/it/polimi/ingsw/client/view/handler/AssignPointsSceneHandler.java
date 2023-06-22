package it.polimi.ingsw.client.view.handler;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.util.Map;

public class AssignPointsSceneHandler extends SceneHandler {

    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView info_background;
    @FXML
    private Label winner;
    @FXML
    private Label winner_name;
    @FXML
    private Label winner_points;
    @FXML
    private HBox winner_Box;
    @FXML
    private Label second;
    @FXML
    private Label second_name;
    @FXML
    private Label second_points;
    @FXML
    private HBox second_Box;
    @FXML
    private Label third;
    @FXML
    private Label third_name;
    @FXML
    private Label third_points;
    @FXML
    private HBox third_Box;
    @FXML
    private Label fourth;
    @FXML
    private Label fourth_name;
    @FXML
    private Label fourth_points;
    @FXML
    private HBox fourth_Box;
    @FXML
    private Button back_Btn;
    private Map<String, Integer> points;

    private void hideAll(){
        third.setVisible(false);
        third_name.setVisible(false);
        third_points.setVisible(false);
        fourth.setVisible(false);
        fourth_name.setVisible(false);
        fourth_points.setVisible(false);
    }

    public void back(){SceneHandler.switchScene("new_or_join");}

    public void updateinfo(String name, Integer points){this.points.put(name, points);}

    public void show(){

        String player = null;

        if(points.size() == 4){
            fourth.setVisible(true);
            fourth_name.setVisible(true);
            fourth_points.setVisible(true);
            player = getLastPlace();
            fourth_name.setText(player);
            fourth_points.setText(points.get(player).toString() + " points");

            points.remove(player);
        }

        if(points.size() == 3){
            third.setVisible(true);
            third_name.setVisible(true);
            third_points.setVisible(true);
            player = getLastPlace();
            third_name.setText(player);
            third_points.setText(points.get(player).toString() + " points");

            points.remove(player);
        }

        second.setVisible(true);
        second_name.setVisible(true);
        second_points.setVisible(true);
        player = getLastPlace();
        second_name.setText(player);
        second_points.setText(points.get(player).toString() + " points");

        points.remove(player);

        winner.setVisible(true);
        winner_name.setVisible(true);
        winner_points.setVisible(true);
        player = getLastPlace();
        winner_name.setText(player);
        winner_points.setText(points.get(player).toString() + " points");

        points.remove(player);
    }

    private String getLastPlace(){

        Integer min = 1000;
        String name = null;

        for (Map.Entry<String, Integer> entry : points.entrySet())
            if(entry.getValue() <= min) {
                min = entry.getValue();
                name = entry.getKey();
            }

        return name;
    }

    @Override
    public void runScene(){

        hideAll();

        resize();

    }
    @Override
    public void resize(){

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

        back_Btn.setFont(Font.font(back_Btn.getFont().getFamily(), back_Btn.getFont().getSize()*ratio_x));

        winner.setFont(Font.font(winner.getFont().getFamily(), winner.getFont().getSize()*ratio_y));
        winner_name.setFont(Font.font(winner_name.getFont().getFamily(), winner_name.getFont().getSize()*ratio_y));
        winner_points.setFont(Font.font(winner_points.getFont().getFamily(), winner_points.getFont().getSize()*ratio_y));
        second.setFont(Font.font(second.getFont().getFamily(), second.getFont().getSize()*ratio_y));
        second_name.setFont(Font.font(second_name.getFont().getFamily(), second_name.getFont().getSize()*ratio_y));
        second_points.setFont(Font.font(second_points.getFont().getFamily(), second_points.getFont().getSize()*ratio_y));
        third.setFont(Font.font(third.getFont().getFamily(), third.getFont().getSize()*ratio_y));
        third_name.setFont(Font.font(third_name.getFont().getFamily(), third_name.getFont().getSize()*ratio_y));
        third_points.setFont(Font.font(third_points.getFont().getFamily(), third_points.getFont().getSize()*ratio_y));
        fourth.setFont(Font.font(fourth.getFont().getFamily(), fourth.getFont().getSize()*ratio_y));
        fourth_name.setFont(Font.font(fourth_name.getFont().getFamily(), fourth_name.getFont().getSize()*ratio_y));
        fourth_points.setFont(Font.font(fourth_points.getFont().getFamily(), fourth_points.getFont().getSize()*ratio_y));

        /*winner_Box.setPrefWidth(winner_Box.getWidth()*ratio_x);
        winner_Box.setPrefHeight(winner_Box.getHeight()*ratio_y);
        winner_Box.setLayoutX(winner_Box.getLayoutX()*ratio_x);
        winner_Box.setLayoutY(winner_Box.getLayoutY()*ratio_y);*/
        winner_Box.setSpacing(winner_Box.getSpacing()*ratio_x);
        second_Box.setSpacing(second_Box.getSpacing()*ratio_x);
        third_Box.setSpacing(third_Box.getSpacing()*ratio_x);
        fourth_Box.setSpacing(fourth_Box.getSpacing()*ratio_x);
    }
}
