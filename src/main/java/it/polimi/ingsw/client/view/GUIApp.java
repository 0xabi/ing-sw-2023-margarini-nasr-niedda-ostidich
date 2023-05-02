package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.Event;
import it.polimi.ingsw.resources.EventID;
import it.polimi.ingsw.resources.interfaces.ClientController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApp extends Application {

    //functions Player Name Scene
    public void back()
    {
        setScene("home");
    }

    private static Stage stage;

    private static GUI gui;

    private static ClientController cc;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start()
    {
        cc.update(new Event(EventID.START,null));
    }
    public void exit()
    {
        Platform.exit();
    }

    public static void setClientController(ClientController cc)
    {
        GUIApp.cc = cc;
    }



    public static void setGui(GUI gui)
    {
        GUIApp.gui = gui;
    }

    public static void setScene(String nameRes)
    {
        setScene(stage, nameRes);
    }

    public static void setScene(Stage stage,String nameRes)
    {

        try {
            Scene scene = new Scene(FXMLLoader.load(GUIApp.class.getResource("/fxml/"+nameRes+".fxml")));
            stage.setScene(scene);
            //nextBtn = (Button)scene.lookup("#nextBtn");
            //nickTextBox = (TextField) scene.lookup("#nickTextBox");
            //nextBtn.setOnAction(e->getUsername());
            //System.out.println(this+"Set Scene");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void start(Stage stage) {

        Image icon = new Image(getClass().getResource("/graphics/publisherMaterial/icon.png").toString());
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setTitle("My Shelfie");
        GUIApp.stage = stage;
        setScene(stage,"home");
        stage.show();
    }
}
