package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.GUIApp;
import it.polimi.ingsw.general.interfaces.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Abdullah Nasr
 */
public abstract class SceneHandler {
    private static Stage stage;

    private static Scene scene;

    private static Pane root;

    private static ClientController cc;

    private static SceneHandler currentHandler;

    private static GUI gui;


    /**
     * Set the Pane
     * @param root The root pane
     * @author Abdullah Nasr
     */
    public static void setRoot(Pane root)
    {
        SceneHandler.root = root;
    }

    /**
     * Get the pane
     * @return The pane root
     * @author Abdullah Nasr
     */
    public static Pane getRoot()
    {
        return root;
    }

    /**
     * Set the GUI that communicates with the handler
     * @param gui the GUI that communicates with the handler
     * @author Abdullah Nasr
     */
    public static void setGUI(GUI gui)
    {
        SceneHandler.gui = gui;
    }

    /**
     * Get the GUI that communicates with the handler
     * @return GUI that communicates with the handler
     * @author Abdullah Nasr
     */
    public static GUI getGui()
    {
        return SceneHandler.gui;
    }

    /**
     * Set the stage
     * @param stage The stage
     * @author Abdullah Nasr
     */
    public static void setStage(Stage stage)
    {
        SceneHandler.stage = stage;
    }

    /**
     * Set the scene
     * @param scene The scene
     * @author Abdullah Nasr
     */
    public static void setScene(Scene scene)
    {
        SceneHandler.scene = scene;
        //System.out.println("Scene"+scene);
    }

    /**
     * Get the scene
     * @return The scene
     * @author Abdullah Nasr
     */
    public static Scene getScene()
    {
        return scene;
    }

    /**
     * Set the client controller
     * @param cc The client controller
     * @author Abdullah Nasr
     */
    public static void setClientController(ClientController cc)
    {
        SceneHandler.cc = cc;
    }

    /**
     * Get the client controller
     * @return The client controller
     * @author Abdullah Nasr
     */
    public static ClientController getClientController()
    {
        return cc;
    }


    /**
     * Get the stage
     * @return The stage
     * @author Abdullah Nasr
     */
    public static Stage getStage()
    {
        return stage;
    }


    /**
     * Switch the scene given the scene's name
     * @param nameRes The name of the scene
     * @author Abdullah Nasr
     */
    public static void switchScene(String nameRes)
    {
        FXMLLoader loader = new FXMLLoader(GUIApp.class.getResource("/fxml/"+nameRes+".fxml"));


        try {
            //resizetest();
            //setScene(loader.load());
            setRoot(loader.load());
            currentHandler = loader.getController();
            currentHandler.runScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Set the current scene handler
     * @param sh The current scene handler
     * @author Abdullah Nasr
     */
    public static void setCurrentHandler(SceneHandler sh)
    {
        currentHandler = sh;
    }

    /**
     * Get the current scene handler
     * @return The current scene handler
     * @author Abdullah Nasr
     */
    public static SceneHandler getCurrentHandler()
    {
        return currentHandler;
    }


    /**
     *  @author Abdullah Nasr
     */
    public abstract void resize();


    /**
     *  @author Abdullah Nasr
     */
    public abstract void runScene();


}
