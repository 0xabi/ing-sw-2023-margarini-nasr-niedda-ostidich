package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.handler.SceneHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApp extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();

        //basic properties of the stage
        Image icon = new Image("/graphics/publisherMaterial/icon.png");
        stage.getIcons().add(icon);
        stage.setMaximized(true);
        stage.setTitle("My Shelfie");


        FXMLLoader loader = new FXMLLoader(GUIApp.class.getResource("/fxml/"+"match"+".fxml"));
        Scene scene;
        Parent root;


        try {
             root = loader.load();

             scene = new Scene(root);
             SceneHandler.setStage(stage);
             SceneHandler.setRoot(root);
             SceneHandler.setScene(scene);
             SceneHandler.setCurrentHandler(loader.getController());
             SceneHandler.getCurrentHandler().resize();
             stage.setScene(scene);
             stage.setWidth(screenWidth);
             stage.setHeight(screenHeight);
             stage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
