package it.polimi.ingsw.client.view.handler;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.util.HashMap;
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
    private Label winner_cgp;
    @FXML
    private Label winner_pgp;
    @FXML
    private Label winner_agp;

    @FXML
    private Label second;
    @FXML
    private Label second_name;
    @FXML
    private Label second_points;
    @FXML
    private Label second_cgp;
    @FXML
    private Label second_pgp;
    @FXML
    private Label second_agp;

    @FXML
    private Label third;
    @FXML
    private Label third_name;
    @FXML
    private Label third_points;
    @FXML
    private Label third_cgp;
    @FXML
    private Label third_pgp;
    @FXML
    private Label third_agp;

    @FXML
    private Label fourth;
    @FXML
    private Label fourth_name;
    @FXML
    private Label fourth_points;
    @FXML
    private Label fourth_cgp;
    @FXML
    private Label fourth_pgp;
    @FXML
    private Label fourth_agp;

    @FXML
    private Label cgp_lbl;
    @FXML
    private Label pgp_lbl;
    @FXML
    private Label agp_lbl;
    @FXML
    private Label total_lbl;
    @FXML
    private Button back_Btn;
    private Map<String, Integer> total_points = new HashMap<>();
    private Map<String, Integer> CG_Points = new HashMap<>();
    private Map<String, Integer> PG_Points = new HashMap<>();
    private Map<String, Integer> AG_Points = new HashMap<>();

    private void hideAll(){
        third.setVisible(false);
        third_name.setVisible(false);
        third_points.setVisible(false);
        third_cgp.setVisible(false);
        third_pgp.setVisible(false);
        third_agp.setVisible(false);

        fourth.setVisible(false);
        fourth_name.setVisible(false);
        fourth_points.setVisible(false);
        fourth_cgp.setVisible(false);
        fourth_pgp.setVisible(false);
        fourth_agp.setVisible(false);
    }

    public void back(){SceneHandler.switchScene("new_or_join");}

    public void updateinfo(String name, Integer points){this.total_points.put(name, points);}

    public void updateCGPoints(String name, int token){
        CG_Points.put(name, token);
    }

    public void updatePGPoints(String name, Integer points){
        PG_Points.put(name, points);
    }

    public void updateAGPoints(String name, Integer points){
        AG_Points.put(name, points);
    }

    public void show(){

        if(total_points.size() == 4)  setFourthPlace(getLastPlace());

        if(total_points.size() == 3) setThirdPlace(getLastPlace());

        setSecondPlace(getLastPlace());

        setFirstPlace(getLastPlace());

    }

    private String getLastPlace(){

        Integer min = 1000;
        String name = null;

        for (Map.Entry<String, Integer> entry : total_points.entrySet())
            if(entry.getValue() <= min) {
                min = entry.getValue();
                name = entry.getKey();
            }

        return name;
    }

    private void setFourthPlace(String player){

        fourth.setVisible(true);
        fourth_name.setVisible(true);
        fourth_points.setVisible(true);
        fourth_cgp.setVisible(true);
        fourth_pgp.setVisible(true);
        fourth_agp.setVisible(true);

        fourth_name.setText(player);
        if(CG_Points.containsKey(player)) fourth_cgp.setText(CG_Points.get(player).toString());
        else fourth_cgp.setText("0");
        fourth_pgp.setText(PG_Points.get(player).toString());
        fourth_agp.setText(AG_Points.get(player).toString());
        fourth_points.setText(total_points.get(player).toString());

        total_points.remove(player);
    }

    private void setThirdPlace(String player){

        third.setVisible(true);
        third_name.setVisible(true);
        third_points.setVisible(true);
        third_cgp.setVisible(true);
        third_pgp.setVisible(true);
        third_agp.setVisible(true);

        third_name.setText(player);
        if(CG_Points.containsKey(player)) third_cgp.setText(CG_Points.get(player).toString());
        else third_cgp.setText("0");
        third_pgp.setText(PG_Points.get(player).toString());
        third_agp.setText(AG_Points.get(player).toString());
        third_points.setText(total_points.get(player).toString());

        total_points.remove(player);
    }

    private void setSecondPlace(String player){

        second.setVisible(true);
        second_name.setVisible(true);
        second_points.setVisible(true);

        second_name.setText(player);
        if(CG_Points.containsKey(player)) second_cgp.setText(CG_Points.get(player).toString());
        else second_cgp.setText("0");
        second_pgp.setText(PG_Points.get(player).toString());
        second_agp.setText(AG_Points.get(player).toString());
        second_points.setText(total_points.get(player).toString());

        total_points.remove(player);
    }

    private void setFirstPlace(String player){

        winner.setVisible(true);
        winner_name.setVisible(true);
        winner_points.setVisible(true);

        winner_name.setText(player);
        if(CG_Points.containsKey(player)) winner_cgp.setText(CG_Points.get(player).toString());
        else winner_cgp.setText("0");
        winner_pgp.setText(PG_Points.get(player).toString());
        winner_agp.setText(AG_Points.get(player).toString());
        winner_points.setText(total_points.get(player).toString());

        total_points.remove(player);
    }

    @Override
    public void runScene(){

        hideAll();

        getStage().setScene(new Scene(getRoot()));

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
        winner_cgp.setFont(Font.font(winner_cgp.getFont().getFamily(), winner_cgp.getFont().getSize()*ratio_y));
        winner_pgp.setFont(Font.font(winner_pgp.getFont().getFamily(), winner_pgp.getFont().getSize()*ratio_y));
        winner_agp.setFont(Font.font(winner_agp.getFont().getFamily(), winner_agp.getFont().getSize()*ratio_y));

        second.setFont(Font.font(second.getFont().getFamily(), second.getFont().getSize()*ratio_y));
        second_name.setFont(Font.font(second_name.getFont().getFamily(), second_name.getFont().getSize()*ratio_y));
        second_points.setFont(Font.font(second_points.getFont().getFamily(), second_points.getFont().getSize()*ratio_y));
        second_cgp.setFont(Font.font(second_cgp.getFont().getFamily(), second_cgp.getFont().getSize()*ratio_y));
        second_pgp.setFont(Font.font(second_pgp.getFont().getFamily(), second_pgp.getFont().getSize()*ratio_y));
        second_agp.setFont(Font.font(second_agp.getFont().getFamily(), second_agp.getFont().getSize()*ratio_y));

        third.setFont(Font.font(third.getFont().getFamily(), third.getFont().getSize()*ratio_y));
        third_name.setFont(Font.font(third_name.getFont().getFamily(), third_name.getFont().getSize()*ratio_y));
        third_points.setFont(Font.font(third_points.getFont().getFamily(), third_points.getFont().getSize()*ratio_y));
        third_cgp.setFont(Font.font(third_cgp.getFont().getFamily(), third_cgp.getFont().getSize()*ratio_y));
        third_pgp.setFont(Font.font(third_pgp.getFont().getFamily(), third_pgp.getFont().getSize()*ratio_y));
        third_agp.setFont(Font.font(third_agp.getFont().getFamily(), third_agp.getFont().getSize()*ratio_y));

        fourth.setFont(Font.font(fourth.getFont().getFamily(), fourth.getFont().getSize()*ratio_y));
        fourth_name.setFont(Font.font(fourth_name.getFont().getFamily(), fourth_name.getFont().getSize()*ratio_y));
        fourth_points.setFont(Font.font(fourth_points.getFont().getFamily(), fourth_points.getFont().getSize()*ratio_y));
        fourth_cgp.setFont(Font.font(fourth_cgp.getFont().getFamily(), fourth_cgp.getFont().getSize()*ratio_y));
        fourth_pgp.setFont(Font.font(fourth_pgp.getFont().getFamily(), fourth_pgp.getFont().getSize()*ratio_y));
        fourth_agp.setFont(Font.font(fourth_agp.getFont().getFamily(), fourth_agp.getFont().getSize()*ratio_y));

        cgp_lbl.setFont(Font.font(cgp_lbl.getFont().getFamily(), cgp_lbl.getFont().getSize()*ratio_x));
        pgp_lbl.setFont(Font.font(pgp_lbl.getFont().getFamily(), pgp_lbl.getFont().getSize()*ratio_x));
        agp_lbl.setFont(Font.font(agp_lbl.getFont().getFamily(), agp_lbl.getFont().getSize()*ratio_x));
        total_lbl.setFont(Font.font(total_lbl.getFont().getFamily(), total_lbl.getFont().getSize()*ratio_x));

    }
}
