package it.polimi.ingsw.client.view.handler;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.GUIApp;
import it.polimi.ingsw.resources.interfaces.ClientController;
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


    public static void setRoot(Pane root)
    {
        SceneHandler.root = root;
    }

    /**
     *
     * @return
     * @author Abdullah Nasr
     */
    public Pane getRoot()
    {
        return root;
    }

    /**
     * set the GUI that communicates with the handler
     * @param gui the GUI that communicates with the handler
     * @author Abdullah Nasr
     */
    public static void setGUI(GUI gui)
    {
        SceneHandler.gui = gui;
    }

    /**
     *
     * @return GUI  that communicates with the handler
     * @author Abdullah Nasr
     */
    public static GUI getGui()
    {
        return SceneHandler.gui;
    }

    /**
     *
     * @param stage
     * @author Abdullah Nasr
     */
    public static void setStage(Stage stage)
    {
        SceneHandler.stage = stage;
    }

    /**
     *
     * @param scene
     * @author Abdullah Nasr
     */
    public static void setScene(Scene scene)
    {
        SceneHandler.scene = scene;
    }

    /**
     *
     * @return
     * @author Abdullah Nasr
     */
    public static Scene getScene()
    {
        return scene;
    }

    /**
     *
     * @param cc
     * @author Abdullah Nasr
     */
    public static void setClientController(ClientController cc)
    {
        SceneHandler.cc = cc;
    }

    /**
     *
     * @return
     * @author Abdullah Nasr
     */
    public static Stage getStage()
    {
        return stage;
    }

    /**
     *
     * @return
     * @author Abdullah Nasr
     */
    public static ClientController getClientController()
    {
        return cc;
    }

    /**
     *
     * @param nameRes
     * @author Abdullah Nasr
     */
    public static void switchScene(String nameRes)
    {
        FXMLLoader loader = new FXMLLoader(GUIApp.class.getResource("/fxml/"+nameRes+".fxml"));

        try {
            setRoot(loader.load());
            currentHandler = loader.getController();
            currentHandler.runScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param sh
     * @author Abdullah Nasr
     */
    public static void setCurrentHandler(SceneHandler sh)
    {
        currentHandler = sh;
    }

    /**
     *
     * @return
     * @author Abdullah Nasr
     */
    public static SceneHandler getCurrentHandler()
    {
        return currentHandler;
    }

    /**
     *  @author Abdullah Nasr
     */
    public abstract void runScene();

    /**
     *  @author Abdullah Nasr
     */
    public abstract void resize();

}
