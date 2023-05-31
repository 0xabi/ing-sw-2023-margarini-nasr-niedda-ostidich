package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.client.view.InputFormatChecker;
import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.EventID;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.rmi.RemoteException;

public class ConnectionSceneHandler extends SceneHandler {

    @FXML
    private ImageView logo;
    @FXML
    private Text ip_lbl;
    @FXML
    private ImageView ip_lbl_background;
    @FXML
    private TextField ip_txt;
    @FXML
    private ImageView ip_txt_background;
    @FXML
    private Button back_btn;
    @FXML
    private Button next_btn;
    @FXML
    private CheckBox socketCB;
    @FXML
    private CheckBox RMICB;
    @FXML
    private Label invalidMsgLbl;
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

        if(InputFormatChecker.isIPAddress(ip_txt.getText()))
        {
            try {
                String network = RMICB.isSelected() ? "RMI" : "Socket";
                String ip = ip_txt.getText();
                String networkAndIp = network+","+ip;

                getClientController().update(new Event(EventID.CHOOSE_CONNECTION_AND_IP, networkAndIp));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            invalidMsgLbl.setVisible(true);
        }

    }


    public void setRMI()
    {
        RMICB.setSelected(true);
        socketCB.setSelected(false);
    }

    public void setSocket()
    {
        socketCB.setSelected(true);
        RMICB.setSelected(false);

    }

    @Override
    public void runScene() {

        resize();

        getScene().setRoot(getRoot());

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
        logo.setFitWidth(logo.getFitWidth()*ratio_x);
        logo.setFitHeight(logo.getFitHeight()*ratio_y);

        ip_lbl_background.setFitWidth(ip_lbl_background.getFitWidth()*ratio_x);
        ip_lbl_background.setFitHeight(ip_lbl_background.getFitHeight()*ratio_y);

        ip_lbl.setFont(Font.font(ip_lbl.getFont().getFamily(), ip_lbl.getFont().getSize()*ratio_y));
        ip_lbl.setLayoutX(ip_lbl_background.getLayoutX()+ ((ip_lbl_background.getFitWidth()- ip_lbl.getBoundsInLocal().getWidth())/2));

        ip_txt.setFont(Font.font(ip_txt.getFont().getFamily(), ip_txt.getFont().getSize()*ratio_y));
        ip_txt_background.setFitWidth(ip_txt_background.getFitWidth()*ratio_x);
        ip_txt_background.setFitHeight(ip_txt_background.getFitHeight()*ratio_y);
        ip_txt.setPrefWidth(ip_txt.getPrefWidth()*ratio_x);
        ip_txt.setPrefHeight(ip_txt.getPrefHeight()*ratio_y);

        back_btn.setPrefWidth(back_btn.getPrefWidth()*ratio_x);
        back_btn.setPrefHeight(back_btn.getPrefHeight()*ratio_y);
        next_btn.setPrefWidth(next_btn.getPrefWidth()*ratio_x);
        next_btn.setPrefHeight(next_btn.getPrefHeight()*ratio_y);

        socketCB.setFont(Font.font(socketCB.getFont().getFamily(), socketCB.getFont().getSize()*ratio_y));
        socketCB.setPrefWidth(socketCB.getPrefWidth()*ratio_x);
        socketCB.setPrefHeight(socketCB.getPrefHeight()*ratio_y);


        RMICB.setFont(Font.font(RMICB.getFont().getFamily(), RMICB.getFont().getSize()*ratio_y));
        RMICB.setPrefWidth(RMICB.getPrefWidth()*ratio_x);
        RMICB.setPrefHeight(RMICB.getPrefHeight()*ratio_y);

        invalidMsgLbl.setFont(Font.font(invalidMsgLbl.getFont().getFamily(), invalidMsgLbl.getFont().getSize()*ratio_x));
        invalidMsgLbl.setPrefWidth(invalidMsgLbl.getPrefWidth()*ratio_x);
        invalidMsgLbl.setPrefHeight(invalidMsgLbl.getPrefHeight()*ratio_y);





    }
}
