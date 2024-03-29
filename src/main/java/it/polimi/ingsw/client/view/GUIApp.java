package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.handler.SceneHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApp extends Application {

    /**
     * The entry point that run JavaFX app given the arguments.
     * @param args The arguments
     * @author Abdullah Nasr
     */
    public static void main(String[] args)
    {
        launch(args);
    }


    @Override
    public void start(Stage stage) {

        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();

        //basic properties of the stage
        //Image icon = new Image("/graphics/publisherMaterial/icon.png");
        //stage.getIcons().add(icon);
        stage.setMaximized(true);
        stage.setTitle("My Shelfie");


        FXMLLoader loader = new FXMLLoader(GUIApp.class.getResource("/fxml/"+"home"+".fxml"));
        Scene scene;
        Parent root;


        try {
             root = loader.load();

             scene = new Scene(root);
             SceneHandler.setStage(stage);
             SceneHandler.setRoot((Pane) root);
             SceneHandler.setScene(scene);
             SceneHandler.setCurrentHandler(loader.getController());
             SceneHandler.getCurrentHandler().resize();
             stage.setScene(scene);
             stage.setWidth(screenWidth);
             stage.setHeight(screenHeight);
             stage.setFullScreen(true);
             //stage.setResizable(false);
             stage.show();

             stage.setOnCloseRequest(event -> {
                 event.consume();
                 Logout();
             });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *This method is invoked when the window is being closed, creating a pop-up window that asks to confirm the user is
     * sure about closing the window. Click on ok to confirm, or on cancel otherwise.
     */
    private void Logout(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logging out");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Are you sure?");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.exit(0);
        }

    }
}
