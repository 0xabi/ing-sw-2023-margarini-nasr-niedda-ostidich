package it.polimi.ingsw.client.view.handler;


import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.server.model.Shelf;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchSceneHandler extends SceneHandler {

    @FXML
    private ImageView personalGoal;
    @FXML
    private ImageView mainShelf;
    @FXML
    private ImageView shelfPlayer1;
    @FXML
    private ImageView shelfPlayer2;
    @FXML
    private ImageView shelfPlayer3;
    @FXML
    private ImageView commonGoal1;
    @FXML
    private ImageView commonGoal2;
    @FXML
    private ImageView board;
    @FXML
    private Label mainPlayerLbl;
    @FXML
    private Label player1Lbl;
    @FXML
    private Label player2Lbl;
    @FXML
    private Label player3Lbl;

    @FXML
    private AnchorPane pane;

    ImageView[][] mainShelfTiles = new ImageView[6][5];


    public void test()
    {
        //set up usernames
        getGui().turnCycleOrder(new ArrayList<String>(){{
            add("PlayerAkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
            add("PlayerB");
            add("PlayerC");
        }});

        //link username with shelves
        HashMap<String, Tile[][]> plShelves = new HashMap<>();

        //set player shelves
        Shelf shelfPlayerA = new Shelf();
        Shelf shelfPlayerB = new Shelf();
        Shelf shelfPlayerC = new Shelf();

        plShelves.put("PlayerAkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", shelfPlayerA.getPositions());
        plShelves.put("PlayerB", shelfPlayerB.getPositions());
        plShelves.put("PlayerC", shelfPlayerC.getPositions());

        //insert tiles into the shelves
        shelfPlayerA.insertInColumn(new ArrayList<Tile>(){{
                                 add(Tile.CATS);
                                 add(Tile.CATS);
                                 add(Tile.GAMES);
        }},1);

        shelfPlayerA.insertInColumn(new ArrayList<Tile>(){{
            add(Tile.GAMES);
        }},3);

        shelfPlayerB.insertInColumn(new ArrayList<Tile>(){{
            add(Tile.TROPHIES);
            add(Tile.CATS);
            add(Tile.FRAMES);
        }},1);


        getGui().updatePlayerShelves(plShelves);

    }

    public Image getTileImage(Tile tileType)
    {
        //Random rand = new Random();

        if(tileType == Tile.FRAMES)
        {
            return new Image("/graphics/itemTiles/Cornici1.1.png");
        }
        else if(tileType == Tile.CATS)
        {
            return new Image("/graphics/itemTiles/Gatti1.1.png");
        }
        else if(tileType == Tile.BOOKS)
        {
            return new Image("/graphics/itemTiles/Libri1.1.png");
        }
        else if(tileType == Tile.PLANTS)
        {
            return new Image("/graphics/itemTiles/Piante1.1.png");
        }
        else if(tileType == Tile.TROPHIES)
        {
            return new Image("/graphics/itemTiles/Trofei1.1.png");
        }
        else if(tileType == Tile.GAMES)
        {
            return new Image("/graphics/itemTiles/Giochi1.1.png");
        }

        return null;
    }

    public void initMainShelf()
    {
        //set name player
        String mainPlayer = getGui().getNames().get(0);
        mainPlayerLbl.setText(mainPlayer);

        int indexPosShelf = getRoot().getChildren().indexOf(mainShelf);

        for(int i=0;i<Shelf.getRowLength();i++)
        {
            for(int j=0;j<Shelf.getColumnLength();j++)
            {
                ImageView iv = new ImageView();

                Tile typeTile = getGui().getPlayerShelves().get(mainPlayer)[i][j];

                if(typeTile != null)
                {
                    iv.setImage(getTileImage(typeTile));
                    iv.setFitWidth(GuiObjectsHandler.getMainShelfTilesSizeWidth());
                    iv.setFitHeight(GuiObjectsHandler.getMainShelfTilesSizeHeight());
                    iv.setLayoutX(GuiObjectsHandler.getMainShelfTilesPosX(i));
                    iv.setLayoutY(GuiObjectsHandler.getMainShelfTilesPosY(j));
                    //iv.toFront();
                    getRoot().getChildren().add(indexPosShelf, iv);
                }
            }
        }

    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void resize() {

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double ratio_x = bounds.getWidth()/pane.getPrefWidth();
        double ratio_y =  bounds.getHeight()/pane.getPrefHeight();


        for(Node n: pane.getChildren())
        {
            //re-positioning position according to the resolution
            n.setLayoutX(n.getLayoutX()*ratio_x);
            n.setLayoutY(n.getLayoutY()*ratio_y);

            //re-sizing according to the resolution
            if(n instanceof ImageView currentImage)
            {
                currentImage.setFitWidth(currentImage.getFitWidth()*ratio_x);
                currentImage.setFitHeight(currentImage.getFitHeight()*ratio_y);
            }
            else if(n instanceof Label currentLabel)
            {

                //currentLabel.setLayoutX(mainShelf.getLayoutX()+((mainShelf.getFitWidth()-currentLabel.getBoundsInParent().getWidth())/2));
                currentLabel.setFont(new Font(currentLabel.getFont().getFamily(),currentLabel.getFont().getSize()*ratio_y));
                currentLabel.setPrefWidth(currentLabel.getPrefWidth()*ratio_x);
                currentLabel.setPrefHeight(currentLabel.getPrefHeight()*ratio_y);
            }
        }

        //mainPlayerLbl.setLayoutX(mainPlayerLbl.getLayoutX() - (mainPlayerLbl.getPrefWidth()/2));
        player1Lbl.setLayoutX(player1Lbl.getLayoutX() - (player1Lbl.getPrefWidth()/2));
        player2Lbl.setLayoutX(player2Lbl.getLayoutX() - (player2Lbl.getPrefWidth()/2));
        player3Lbl.setLayoutX(player3Lbl.getLayoutX() - (player3Lbl.getPrefWidth()/2));

        mainPlayerLbl.setLayoutY(mainPlayerLbl.getLayoutY() - (mainPlayerLbl.getPrefHeight()/2));
        player1Lbl.setLayoutY(player1Lbl.getLayoutY() - (player1Lbl.getPrefHeight()/2));
        player2Lbl.setLayoutY(player2Lbl.getLayoutY() - (player2Lbl.getPrefHeight()/2));
        player3Lbl.setLayoutY(player3Lbl.getLayoutY() - (player3Lbl.getPrefHeight()/2));

        GuiObjectsHandler.resizeObjects(ratio_x,ratio_y);

    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void runScene() {



        resize();
        test();
        initMainShelf();


        getScene().setRoot(getRoot());

    }
}
