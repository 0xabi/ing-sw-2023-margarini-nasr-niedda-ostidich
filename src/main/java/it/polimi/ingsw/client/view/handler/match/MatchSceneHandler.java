package it.polimi.ingsw.client.view.handler.match;


import it.polimi.ingsw.client.view.handler.SceneHandler;
import it.polimi.ingsw.general.Tile;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Shelf;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    private Label advertising;

    @FXML
    private ImageView col1;

    @FXML
    private ImageView col2;
    @FXML
    private ImageView col3;
    @FXML
    private ImageView col4;
    @FXML
    private ImageView col5;

    @FXML
    private AnchorPane pane;
    @FXML
    private TextField txtField;
    @FXML
    private TextArea txtArea;
    @FXML
    private Button sendBtn;

    private Integer column = null;

    Tile[][] prevMainShelf;
    private boolean pickPhase = true;
    private boolean insertPhase = false;

    private final ArrayList<ImageView> tiles = new ArrayList<>();

    private final ArrayList<ImageView> ordered = new ArrayList<>();


    public void test()
    {
        //set up usernames
        getGui().turnCycleOrder(new ArrayList<>() {{
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
        shelfPlayerA.insertInColumn(new ArrayList<>() {{
            add(Tile.CATS);
            add(Tile.CATS);
            add(Tile.GAMES);
        }},1);

        shelfPlayerA.insertInColumn(new ArrayList<>() {{
            add(Tile.GAMES);
        }},3);

        shelfPlayerB.insertInColumn(new ArrayList<>() {{
            add(Tile.TROPHIES);
            add(Tile.CATS);
            add(Tile.FRAMES);
        }},1);


        Tile[][] board = {{null,null,null,null,null,null,null,null,null},
                {null,null,null,Tile.CATS,Tile.FRAMES,null,null,null,null,null},
                {null,null,null,Tile.FRAMES,Tile.BOOKS,Tile.PLANTS,null,null,null},
                {null,null,Tile.BOOKS,Tile.GAMES,Tile.TROPHIES,Tile.TROPHIES,Tile.BOOKS,Tile.CATS,null},
                {null,Tile.PLANTS,Tile.GAMES,Tile.TROPHIES,Tile.FRAMES,Tile.GAMES,Tile.TROPHIES,Tile.GAMES,null},
                {null,Tile.PLANTS,Tile.GAMES,Tile.TROPHIES,Tile.FRAMES,Tile.GAMES,Tile.TROPHIES,null,null},
                {null,null,null,Tile.FRAMES,Tile.CATS,Tile.TROPHIES,null,null,null},
                {null,null,null,null,Tile.BOOKS,Tile.TROPHIES,null,null,null},
                {null,null,null,null,null,null,null,null,null,}};


        getGui().updatePlayerShelves(plShelves);
        getGui().updateBoard(board);




    }

    /**
     * @author Pietro Andrea Niedda
     */
    public void resetColumnsCursor(){
        col1.setCursor(null);
        col2.setCursor(null);
        col3.setCursor(null);
        col4.setCursor(null);
        col5.setCursor(null);
    }


    /**
     *
     * @param tileType
     * @return
     * @author Abdullah Nasr
     */
    public Image getTileImage(Tile tileType)
    {
        //Random rand = new Random();

        if(tileType == Tile.FRAMES)
        {

            return new Image(getClass().getResource("/graphics/itemTiles/Cornici1.1.png").toString());

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

    /**
     * @author Pietro Andrea Niedda
     */
    public void callPick(){
        int n = 0;
        double tilesSize = tiles.size()*GuiObjectsHandler.getBoardTilesSizeWidth() + (tiles.size()-1) * 0.1018;
        double begin = mainShelf.getLayoutX() + (mainShelf.getFitWidth()- tilesSize)/2;
        TranslateTransition translate;

        if(!pickPhase){
            advertising.setText("It's not pick phase");
        }
        else if(tiles.size() == 0){
            advertising.setText("Must choose tiles first");
        }
        else
        {
            for(ImageView tile : tiles)
            {
                translate = new TranslateTransition();
                translate.setNode(tile);
                translate.setDuration(Duration.millis(1000));
                translate.setToX((begin+n*(GuiObjectsHandler.getBoardTilesSizeWidth()+0.1018))-tile.getLayoutX());
                translate.setToY(mainShelf.getLayoutY()-tile.getLayoutY()-GuiObjectsHandler.getBoardTilesSizeHeight()*2);
                tile.setEffect(null);
                translate.play();
                n++;
            }

            col1.setCursor(Cursor.HAND);
            col2.setCursor(Cursor.HAND);
            col3.setCursor(Cursor.HAND);
            col4.setCursor(Cursor.HAND);
            col5.setCursor(Cursor.HAND);

            prevMainShelf = getGui().getPlayerShelves().get(getGui().getNames().get(0));
            pickPhase = false;
            insertPhase = true;
        }


    }

    /**
     *
     * @param col
     * @author Pietro Andrea Niedda
     */
    public void callInsert(int col){
        TranslateTransition translate;
        int freeRowPosition = getFreeCellMainShelfPosition(col);
        double currFreeRowPositionY;
        double currFreeRowPositionX = mainShelf.getLayoutX()+mainShelf.getFitWidth()*GuiObjectsHandler.getMainShelfTilesPosX(col);



        for(ImageView tile : ordered) {
            translate = new TranslateTransition();
            tile.setFitWidth(GuiObjectsHandler.getBoardTilesSizeWidth());
            tile.setFitHeight(GuiObjectsHandler.getBoardTilesSizeHeight());
            translate.setNode(tile);
            translate.setDuration(Duration.millis(1000));
            currFreeRowPositionY =mainShelf.getLayoutY()+mainShelf.getFitHeight()*GuiObjectsHandler.getMainShelfTilesPosY(freeRowPosition);
            translate.setToY(currFreeRowPositionY -tile.getLayoutY());
            translate.setToX(currFreeRowPositionX-tile.getLayoutX());

            tile.setEffect(null);
            tile.setOnMouseClicked(null);
            translate.play();

            freeRowPosition++;

            //insert(tile, n++);

        }

        insertPhase = false;
        pickPhase = true;
        prevMainShelf = getGui().getPlayerShelves().get(getGui().getNames().get(0));
        tiles.clear();
        ordered.clear();
        resetColumnsCursor();
    }

    /**
     *
     * @param col
     * @return
     * @author Abdullah Nasr
     */
    private int getFreeCellMainShelfPosition(int col)
    {
        Tile[][] board = prevMainShelf;

        for(int i=0;i<Shelf.getColumnLength();i++)
        {
            if(board[col][i]==null || board[col][i]==Tile.EMPTY)
            {
                return i;
            }
        }

        return 0;
    }


    /**
     * @author Pietro Andrea Niedda
     */
    public void putCol1(){
        if(!insertPhase) {
            advertising.setText("It's not insert phase");
            return;
        }
        if(ordered.size() != tiles.size()){
            advertising.setText("Must choose order first");
            return;
        }

        column = 0;
        resetColumnsCursor();
        callInsert(0);
    }

    /**
     * @author Pietro Andrea Niedda
     */

    public void putCol2(){
        if(!insertPhase) {
            advertising.setText("It's not insert phase");
            return;
        }
        if(ordered.size() != tiles.size()){
            advertising.setText("Must choose order first");
            return;
        }

        column = 1;
        resetColumnsCursor();
        callInsert(1);
    }

    /**
     * @author Pietro Andrea Niedda
     */
    public void putCol3(){
        if(!insertPhase) {
            advertising.setText("It's not insert phase");
            return;
        }
        if(ordered.size() != tiles.size()){
            advertising.setText("Must choose order first");
            return;
        }

        column = 2;
        resetColumnsCursor();
        callInsert(2);
    }

    /**
     * @author Pietro Andrea Niedda
     */
    public void putCol4(){
        if(!insertPhase) {
            advertising.setText("It's not insert phase");
            return;
        }
        if(ordered.size() != tiles.size()){
            advertising.setText("Must choose order first");
            return;
        }

        column = 3;
        resetColumnsCursor();
        callInsert(3);
    }

    /**
     * @author Pietro Andrea Niedda
     */
    public void putCol5(){
        if(!insertPhase) {
            advertising.setText("It's not insert phase");
            return;
        }
        if(ordered.size() != tiles.size()){
            advertising.setText("Must choose order first");
            return;
        }

        column = 4;
        resetColumnsCursor();
        callInsert(4);
    }

    /**
     * @author Pietro Andrea Niedda
     */
    public void sendMsg(){
        if(!txtField.getText().equals("")){
            txtArea.appendText("placeholder" + ": " + txtField.getText() + "\n");
            txtField.setText("");
        }
    }

    /**
     * @author Abdullah Nasr
     */
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
                    iv.setPreserveRatio(false);


                    iv.setFitWidth(GuiObjectsHandler.getMainShelfTilesSizeWidth());
                    iv.setFitHeight(GuiObjectsHandler.getMainShelfTilesSizeHeight());

                    //iv.setLayoutX(GuiObjectsHandler.getMainShelfTilesPosX(i));
                    iv.setLayoutX(mainShelf.getLayoutX()+mainShelf.getFitWidth()*GuiObjectsHandler.getMainShelfTilesPosX(i));
                    iv.setLayoutY(mainShelf.getLayoutY()+mainShelf.getFitHeight()*GuiObjectsHandler.getMainShelfTilesPosY(j));

                    //iv.setLayoutY(GuiObjectsHandler.getMainShelfTilesPosY(j));
                    //iv.toFront();
                    getRoot().getChildren().add(indexPosShelf, iv);
                }
            }
        }
    }

    /**
     *
     * @param tile
     * @author Pietro Andrea Niedda
     */
    private void tileBehavior(ImageView tile){
        if(pickPhase) {
            if (tiles.contains(tile)) {
                tile.setEffect(null);
                tiles.remove(tile);
            } else if (tiles.size() < 3) {
                tile.setEffect(new Glow(1));
                tiles.add(tile);
            } else advertising.setText("can't choose more than 3 tiles");
        }
        else if(insertPhase){
            if(tiles.contains(tile)) {
                if (ordered.contains(tile)) {
                    tile.setEffect(null);
                    ordered.remove(tile);
                } else if (ordered.size() < 3) {
                    tile.setEffect(new Glow(1));
                    ordered.add(tile);
                }
                else advertising.setText("Can't choose this  tile");
            }
        }
    }

    /**
     * @author Pietro Andrea Niedda
     */
    public void fillBoard(){ 
        
        Tile[][] currboard = getGui().getBoard();
        int indexPosShelf = getRoot().getChildren().indexOf(mainShelf);
        
        for(int i=0;i< Board.getRowLength();i++)
        {
            for(int j=0;j<Board.getColumnLength();j++)
            {
                if(currboard[i][j]!=null && currboard[i][j]!=Tile.EMPTY)
                {
                    ImageView tile = new ImageView();
                    tile.setImage(getTileImage(currboard[i][j]));
                    tile.setCursor(Cursor.HAND);
                    tile.setFitWidth(GuiObjectsHandler.getBoardTilesSizeWidth());
                    tile.setFitHeight(GuiObjectsHandler.getBoardTilesSizeHeight());
                    tile.setLayoutX(board.getLayoutX()+board.getFitWidth()*GuiObjectsHandler.getBoardTilesPosX(j));
                    tile.setLayoutY(board.getLayoutY()+board.getFitHeight()*GuiObjectsHandler.getBoardTilesPosY(i));
                    tile.setOnMouseClicked(event ->tileBehavior(tile));
                    getRoot().getChildren().add(indexPosShelf,tile);

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
        double panePrefWidth = pane.getPrefWidth();
        double panePrefHeight = pane.getPrefHeight();
        double ratio_x = bounds.getWidth()/panePrefWidth;
        double ratio_y =  bounds.getHeight()/panePrefHeight;


        getRoot().setScaleX(ratio_x);
        getRoot().setScaleY(ratio_y);
        getRoot().setTranslateX( (bounds.getWidth()-panePrefWidth)/2);
        getRoot().setTranslateY((bounds.getHeight()-panePrefHeight)/2);

    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void runScene() {

        advertising.setText("");
        resize();
        //test();
        initMainShelf();

        fillBoard();


        getScene().setRoot(getRoot());

    }

}
