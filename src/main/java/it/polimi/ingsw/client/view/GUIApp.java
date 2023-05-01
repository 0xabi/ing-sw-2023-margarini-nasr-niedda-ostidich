package it.polimi.ingsw.client.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApp extends Application {


    private static Stage stage;

    private static GUI gui;
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start()
    {
        gui.loaded();
    }
    public void exit()
    {
        Platform.exit();
    }

    public static void setStage(GUI gui)
    {
        gui.setStage(stage);
    }

    public static void setGui(GUI gui)
    {
        GUIApp.gui = gui;
    }
    public void setScene(Stage stage,String nameRes)
    {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/"+nameRes+".fxml")));
            stage.setScene(scene);
            //nextBtn = (Button)scene.lookup("#nextBtn");
            //nickTextBox = (TextField) scene.lookup("#nickTextBox");
            //nextBtn.setOnAction(e->getUsername());
            System.out.println(this+"Set Scene");
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
        setStage(gui);
        setScene(stage,"home");
        stage.show();
    }
}
