package it.polimi.ingsw.client.view.handler.match;


import it.polimi.ingsw.client.view.handler.SceneHandler;
import it.polimi.ingsw.general.Coordinates;
import it.polimi.ingsw.general.Event;
import it.polimi.ingsw.general.EventID;
import it.polimi.ingsw.general.Tile;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Shelf;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.util.Duration;
import it.polimi.ingsw.Debugging.Watch;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    @FXML
    private ImageView endGameToken;

    @FXML
    private ImageView chairMainPlayer;

    @FXML
    private ImageView chairPlayer1;

    @FXML
    private ImageView chairPlayer2;

    @FXML
    private ImageView chairPlayer3;
    private Integer column = null;

    Tile[][] prevMainShelf;
    private static boolean pickPhase = false;
    private static boolean insertPhase = false;

    private static boolean selectedColumn = false;
    private static Integer selectedColumnNumber = null;

    private final ArrayList<ImageView> tiles = new ArrayList<>();

    private final ArrayList<ImageView> ordered = new ArrayList<>();

    private final ImageView[][] mainShelfTileImages = new ImageView[Shelf.getRowLength()][Shelf.getColumnLength()];

    private final ImageView[][] boardTilesImages = new ImageView[Board.getRowLength()][Board.getColumnLength()];

    private static final HashMap<ImageView, Coordinates> imageToCoord = new HashMap<>();
    private static int availableTiles = 0;

    ArrayList<Coordinates> coordsList = new ArrayList<>();

    private final static ArrayList<String> opponentPlayersName = new ArrayList<>();
    private final static HashMap<String, ImageView[][]> opponentsPlayerTilesImages = new HashMap<>();

    private static int numTotPlayers;


    private static boolean tilesIsMoving = false;

    Watch pickTilesReqToServer = new Watch("pickTilesReqToServer");
    Watch chooseColumnReqToServer = new Watch("chooseColumnReqToServer");
    Watch chooseOrderReqToServer = new Watch("chooseOrderReqToServer");
    int indexPosShelf;




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
     * Initialize and create scoring tokens
     * @author Abdullah Nasr
     */
    public void initScoringTokens()
    {
        CommonGoalsHandler.setRoot(getRoot());
        CommonGoalsHandler.setOpponentNames(opponentPlayersName);
        CommonGoalsHandler.setNamePlayer(getGui().getPlayerName());
        CommonGoalsHandler.setShelves(mainShelf,shelfPlayer1,shelfPlayer2,shelfPlayer3);
        CommonGoalsHandler.initScoringTokens(numTotPlayers,commonGoal1,commonGoal2);
    }

    public void updateCommonGoals(Map<Integer, String> givenCommonGoal1, Map<Integer,String> givenCommonGoal2)
    {
        CommonGoalsHandler.updateCommonGoal1(givenCommonGoal1);
        CommonGoalsHandler.updateCommonGoal2(givenCommonGoal2);
    }

    /**
     * Set the chair to the player that have the first turn
     * @author Abdullah Nasr
     */
    public void initChair()
    {
        String firstTurnPlayerName = getGui().getNames().get(0);
        int counter = 0;

        //if the main player have the first turn
        if(getGui().getPlayerName().equals(firstTurnPlayerName))
        {
            chairMainPlayer.setVisible(true);
        }
        else
        {
            for(String currentName : opponentPlayersName)
            {
                if(currentName.equals(firstTurnPlayerName))
                {
                    if(counter==0)
                    {
                        chairPlayer1.setVisible(true);
                    }
                    else if(counter==1)
                    {
                        chairPlayer2.setVisible(true);
                    }
                    else if(counter==3)
                    {
                        chairPlayer3.setVisible(true);
                    }
                }

                counter++;
            }
        }
    }



    /**
     * It reads the personal goal image based on the number that is associated in the json
     * @param personalGoalNumber The number that is associated with the configuration present in the json file
     * @author Abdullah Nasr
     */
    public void initPersonalGoal(Integer personalGoalNumber)
    {
        String postfix = personalGoalNumber == 1 ? ".png" : personalGoalNumber+".png";

        personalGoal.setImage(new Image(Objects.requireNonNull(getClass().getResource("/graphics/personalGoalCards/Personal_Goals" + postfix)).toString()));

    }


    /**
     * Set the number of players in the match
     * @param numPlayers the number of players in the match
     * @author Abdullah Nasr
     */
    public static void setNumOfPlayers(int numPlayers)
    {
        numTotPlayers = numPlayers;
    }

    /**
     * It creates the images view for the tiles of the opponent
     * @param namePlayer The opponent's name
     * @param shelf The image view of the opponent's shelf
     * @author Abdullah Nasr
     */
    public void initOpponentShelf(String namePlayer,ImageView shelf)
    {
        int posShelf = getRoot().getChildren().indexOf(shelf);
        ImageView[][] shelfTileImages=  new ImageView[Shelf.getRowLength()][Shelf.getColumnLength()];

        for(int i=0;i<Shelf.getRowLength();i++)
        {
            for(int j=0;j<Shelf.getColumnLength();j++)
            {
                ImageView iv = new ImageView();


                //Tile typeTile = getGui().getPlayerShelves().get(mainPlayer)[i][j];

                iv.setImage(null);
                iv.setPreserveRatio(false);

                iv.setFitWidth(GuiObjectsHandler.getPlayerShelfTilesSizeWidth());
                iv.setFitHeight(GuiObjectsHandler.getPlayerShelfTilesSizeHeight());

                iv.setLayoutX(shelf.getLayoutX()+shelf.getFitWidth()*GuiObjectsHandler.getMainShelfTilesPosX(i));
                iv.setLayoutY(shelf.getLayoutY()+shelf.getFitHeight()*GuiObjectsHandler.getMainShelfTilesPosY(j));
                shelfTileImages[i][j] = iv;
                getRoot().getChildren().add(posShelf, iv);

            }
        }

        opponentsPlayerTilesImages.put(namePlayer, shelfTileImages);

    }

    /**
     * It updates the image view of the opponent boards
     * @author Abdullah Nasr
     */
    public void updateOpponentBoards()
    {

        Watch updateOpponentBoards = new Watch("updateOpponentBoards");
        updateOpponentBoards.start();
        for(String currentName: opponentPlayersName)
        {
            Tile[][] currentShelf = getGui().getPlayerShelves().get(currentName);
            ImageView[][] currentTilesImages = opponentsPlayerTilesImages.get(currentName);

            for(int i=0;i< Shelf.getRowLength();i++) {
                for (int j = 0; j < Shelf.getColumnLength(); j++) {

                    if(currentTilesImages[i][j]!=null)
                    {
                        if(currentShelf[i][j]== null || currentShelf[i][j]==Tile.EMPTY)
                        {
                            currentTilesImages[i][j].setVisible(false);
                        }
                        else
                        {
                            currentTilesImages[i][j].setImage(getTileImage(currentShelf[i][j]));
                            currentTilesImages[i][j].setVisible(true);
                        }
                    }
                }
            }
        }
        updateOpponentBoards.stop();
    }

    /**
     *  Initialize GUI's components for opponent players
     *  as Label, Image View etc.
     *  @author Abdullah Nasr
     */
    public void initOpponentPlayers()
    {
        //get opponent names
        for(String currentName : getGui().getNames())
        {
            if(!getGui().getPlayerName().equals(currentName))
            {
                opponentPlayersName.add(currentName);
                //initOpponentShelf(currentName, );
            }
        }

        //show only #numTotPlayers shelves
        if(numTotPlayers==2)
        {
            player2Lbl.setVisible(false);
            player3Lbl.setVisible(false);
            shelfPlayer2.setVisible(false);
            shelfPlayer3.setVisible(false);
            player1Lbl.setText(opponentPlayersName.get(0));
            initOpponentShelf(opponentPlayersName.get(0),shelfPlayer1);
        }
        else if(numTotPlayers==3)
        {
            player3Lbl.setVisible(false);
            shelfPlayer3.setVisible(false);

            player1Lbl.setText(opponentPlayersName.get(0));
            initOpponentShelf(opponentPlayersName.get(0),shelfPlayer1);

            player2Lbl.setText(opponentPlayersName.get(1));
            initOpponentShelf(opponentPlayersName.get(1),shelfPlayer2);
        }
        else if(numTotPlayers==4)
        {
            player1Lbl.setText(opponentPlayersName.get(0));
            initOpponentShelf(opponentPlayersName.get(0),shelfPlayer1);

            player2Lbl.setText(opponentPlayersName.get(1));
            initOpponentShelf(opponentPlayersName.get(1),shelfPlayer2);

            player3Lbl.setText(opponentPlayersName.get(2));
            initOpponentShelf(opponentPlayersName.get(2),shelfPlayer3);
        }

    }

    /**
     * @author Pietro Andrea Niedda
     */
    public void resetColumnsCursor(){
        col1.setVisible(false);
        col2.setVisible(false);
        col3.setVisible(false);
        col4.setVisible(false);
        col5.setVisible(false);
        col1.setCursor(null);
        col2.setCursor(null);
        col3.setCursor(null);
        col4.setCursor(null);
        col5.setCursor(null);
    }

    /**
     *
     */
    public void enableColumnsCursor()
    {
        col1.setVisible(true);
        col2.setVisible(true);
        col3.setVisible(true);
        col4.setVisible(true);
        col5.setVisible(true);
        col1.setCursor(Cursor.HAND);
        col2.setCursor(Cursor.HAND);
        col3.setCursor(Cursor.HAND);
        col4.setCursor(Cursor.HAND);
        col5.setCursor(Cursor.HAND);
    }

    /**
     * Set the number of pickable tiles
     * @param num the number of pickable tiles
     * @author Abdullah nasr
     */
    public static void setAvailableTiles(int num)
    {
        availableTiles = num;
    }


    /**
     * Set the images of the common goals given the names of the first and second common goals
     * @param commonGoal1Name The name of the first common goal
     * @param commonGoal2Name The name of the second common goal
     * @author Abdullah Nasr
     */
    public void setImageCommonGoals(String commonGoal1Name, String commonGoal2Name)
    {
        CommonGoalsHandler.setImageCommonGoals(commonGoal1,commonGoal1Name,commonGoal2,commonGoal2Name);
    }

    /**
     * It gives the tile image based on the tile type
     * @param tileType the tile type to be converted into image
     * @return The image of the tile
     * @author Abdullah Nasr
     */
    public Image getTileImage(Tile tileType)
    {
        //Random rand = new Random();

        if(tileType == Tile.FRAMES)
        {

            return new Image(Objects.requireNonNull(getClass().getResource("/graphics/itemTiles/Cornici1.1.png")).toString());

        }
        else if(tileType == Tile.CATS)
        {
            return new Image(Objects.requireNonNull(getClass().getResource("/graphics/itemTiles/Gatti1.1.png")).toString());
        }
        else if(tileType == Tile.BOOKS)
        {
            return new Image(Objects.requireNonNull(getClass().getResource("/graphics/itemTiles/Libri1.1.png")).toString());

        }
        else if(tileType == Tile.PLANTS)
        {
            return new Image(Objects.requireNonNull(getClass().getResource("/graphics/itemTiles/Piante1.1.png")).toString());
        }
        else if(tileType == Tile.TROPHIES)
        {
            return new Image(Objects.requireNonNull(getClass().getResource("/graphics/itemTiles/Trofei1.1.png")).toString());
        }
        else if(tileType == Tile.GAMES)
        {
            return new Image(Objects.requireNonNull(getClass().getResource("/graphics/itemTiles/Giochi1.1.png")).toString());
        }

        return null;
    }

    /**
     *
     */
    public void moveTiles()
    {

        double tilesSize = tiles.size()*GuiObjectsHandler.getBoardTilesSizeWidth() + (tiles.size()-1) * 0.1018;
        TranslateTransition translate;
        double begin = mainShelf.getLayoutX() + (mainShelf.getFitWidth()- tilesSize)/2;
        int n = 0;

            for(ImageView tile : tiles)
            {
                translate = new TranslateTransition();

                translate.setNode(tile);

                translate.setDuration(Duration.millis(1000));

                translate.setToX((begin+n*(GuiObjectsHandler.getBoardTilesSizeWidth()+0.1018))-tile.getLayoutX());

                translate.setToY(mainShelf.getLayoutY()-tile.getLayoutY()-GuiObjectsHandler.getBoardTilesSizeHeight()*2);

                translate.play();
                tile.setEffect(null);
                n++;
            }

        pickPhase = false;
        insertPhase = true;


    }


    /**
     * @author Pietro Andrea Niedda
     */
    public void callPick(){

        Watch callPickWatch = new Watch("callPickWatch");

        callPickWatch.start();

        if(!pickPhase){
            advertising.setText("You can't pick");
        }
        else if(tiles.size() == 0){
            advertising.setText("Must choose tiles first");
        }
        else
        {

            try {
                //begin = System.currentTimeMillis();
                pickTilesReqToServer.start();
                getClientController().update(new Event(EventID.PICK_TILES, coordsList));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            prevMainShelf = getGui().getPlayerShelves().get(getGui().getPlayerName());

        }

        callPickWatch.stop();
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

            //beginT = System.currentTimeMillis();
            int finalFreeRowPosition;
            translate = new TranslateTransition();
            tile.setFitWidth(GuiObjectsHandler.getBoardTilesSizeWidth());
            tile.setFitHeight(GuiObjectsHandler.getBoardTilesSizeHeight());
            translate.setNode(tile);
            translate.setDuration(Duration.millis(1000));
            currFreeRowPositionY =mainShelf.getLayoutY()+mainShelf.getFitHeight()*GuiObjectsHandler.getMainShelfTilesPosY(freeRowPosition);

            translate.setToY(currFreeRowPositionY -tile.getLayoutY());
            translate.setToX(currFreeRowPositionX-tile.getLayoutX());
            finalFreeRowPosition = freeRowPosition;

            translate.setOnFinished(e -> {


                tilesIsMoving=false;

                //update shelf image view
               mainShelfTileImages[col][finalFreeRowPosition].setImage(tile.getImage());

               
               Coordinates coordTile = imageToCoord.get(tile);

               //replace tile and put the new on into the board
                ImageView newTile = new ImageView();
                newTile.setImage(null);
                newTile.setCursor(Cursor.HAND);
                newTile.setVisible(false);
                newTile.setFitWidth(GuiObjectsHandler.getBoardTilesSizeWidth());
                newTile.setFitHeight(GuiObjectsHandler.getBoardTilesSizeHeight());
                newTile.setLayoutX(board.getLayoutX()+board.getFitWidth()*GuiObjectsHandler.getBoardTilesPosX(coordTile.x()));
                newTile.setLayoutY(board.getLayoutY()+board.getFitHeight()*GuiObjectsHandler.getBoardTilesPosY(coordTile.y()));
                newTile.setOnMouseClicked(event ->tileBehavior(newTile));

                imageToCoord.remove(tile);
                imageToCoord.put(newTile, coordTile);
                boardTilesImages[coordTile.x()][coordTile.y()]=newTile;
                getRoot().getChildren().add(indexPosShelf,newTile);
                updateBoard();



            });

            tile.setEffect(null);
            tile.setOnMouseClicked(null);

            tilesIsMoving = true;
            translate.play();

            freeRowPosition++;


            //insert(tile, n++);

        }

        insertPhase = false;
        pickPhase = false;
        prevMainShelf = getGui().getPlayerShelves().get(getGui().getPlayerName());
        tiles.clear();
        ordered.clear();
        coordsList.clear();
        selectedColumn = false;
        selectedColumnNumber = null;
        resetColumnsCursor();

    }

    /**
     * The function gives the last free row from a specified column i.e.
     * the index of the first row starting from 0(bottom) that doesn't contain
     * a tile.
     * @param col The column in which search the first free slot
     * @return The index of the first row starting from 0(bottom) that doesn't contain a tile
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

        return -1;
    }

    /**
     * Put the tiles into the specified column
     * The index column starts from 0
     * @param column a number(from 0) that indicates the column in which insert the tiles
     * @author Abdullah Nasr
     */
    public void putCol(int column)
    {
        if(!insertPhase) {
            advertising.setText("It's not insert phase");
        }
        else if(ordered.size() != tiles.size()){
            advertising.setText("Must choose order first");
        }
        else
        {
            //resetColumnsCursor();
            try {
                selectedColumn=true;
                selectedColumnNumber = column;
                chooseColumnReqToServer.start();
                Watch.temp.start();
                getClientController().update(new Event(EventID.CHOOSE_COLUMN, column));
                //callInsert(column);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
    }


    /**
     * Change the player's message label
     * @param msg the message to be shown to the player
     * @author Abdullah Nasr
     */
    public void changeAdviseMsg(String msg)
    {
        advertising.setText(msg);
    }

    /**
     *
     */
    public void putCol1()
    {
        putCol(0);
    }

    public void putCol2()
    {
        putCol(1);
    }

    public void putCol3()
    {
        putCol(2);
    }

    public void putCol4()
    {
        putCol(3);
    }

    public void putCol5()
    {
        putCol(4);
    }


    /**
     * @author Abdullah Nasr
     */
    public void sendMsg(){

    }

    /**
     * set if the player is in the pick phase
     * @param state can be true if the player is in the pick phase false otherwise
     * @author Abdullah Nasr
     */
    public static void setPickPhase(boolean state)
    {
        pickPhase=state;
    }


    /**
     * It creates the images view(5x6) for the tiles contained in the player's shelf
     * at the beginning these images will have empty image, it will change during the game.
     * @author Abdullah Nasr
     */
    public void initMainShelf()
    {

        //set name player
        String mainPlayer = getGui().getPlayerName();
        mainPlayerLbl.setText(mainPlayer);

        indexPosShelf = getRoot().getChildren().indexOf(mainShelf);

        for(int i=0;i<Shelf.getRowLength();i++)
        {
            for(int j=0;j<Shelf.getColumnLength();j++)
            {
                ImageView iv = new ImageView();


                //Tile typeTile = getGui().getPlayerShelves().get(mainPlayer)[i][j];

                iv.setImage(null);
                iv.setPreserveRatio(false);

                iv.setFitWidth(GuiObjectsHandler.getMainShelfTilesSizeWidth());
                iv.setFitHeight(GuiObjectsHandler.getMainShelfTilesSizeHeight());

                iv.setLayoutX(mainShelf.getLayoutX()+mainShelf.getFitWidth()*GuiObjectsHandler.getMainShelfTilesPosX(i));
                iv.setLayoutY(mainShelf.getLayoutY()+mainShelf.getFitHeight()*GuiObjectsHandler.getMainShelfTilesPosY(j));
                mainShelfTileImages[i][j] = iv;
                getRoot().getChildren().add(indexPosShelf, iv);

            }
        }

        System.out.println("Time to initMainShelf(): ");
        System.out.println(System.currentTimeMillis());
    }

    /**
     * Tell to the user to choose the order, if just choose the order it means that
     * I chose an invalid column, so i will resend to the controller the coordinates
     *
     * @author Abdullah Nasr
     */
    public void chooseOrder()
    {
        //i just chose the order but the column was invalid
        if(ordered.size()!=0)
        {
            //changeAdviseMsg("Invalid column, please retry again");
            try {
                getClientController().update(new Event(EventID.CHOOSE_ORDER, coordsList));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            pickTilesReqToServer.stop();
            changeAdviseMsg("Choose the order of your picked tiles");

            Watch moveTilesWatch = new Watch("moveTiles");
            moveTilesWatch.start();
            moveTiles();
            moveTilesWatch.stop();
        }
    }

    /**
     * If the function is called from column selection phase it means
     * that the previous selection failed, so it prompt to retry otherwise
     * it will animate the tiles
     * @param chooseColumnPhase true if you call the function during the column selection
     *                          phase, false otherwise
     * @author Abdullah Nasr
     */
    public void checkColumnSelection(boolean chooseColumnPhase)
    {

        if(selectedColumn&&chooseColumnPhase)
        {
            changeAdviseMsg("Invalid column, please retry again");
        }
        else if(selectedColumn&&ordered.size()!=0)
        {
            callInsert(selectedColumnNumber);
        }

        if(chooseColumnReqToServer.isRunning())
            chooseColumnReqToServer.stop();

        if(chooseOrderReqToServer.isRunning())
            chooseOrderReqToServer.stop();

    }

    /**
     * If the selection was illegal, prompt to retry the selection otherwise
     * prompt to continue with selection
     * @author Abdullah Nasr
     */
    public void checkSelection()
    {
        if(tiles.size()!=0)
        {
            changeAdviseMsg("Invalid selection, retry.");

            for(ImageView iv : tiles)
            {
                iv.setEffect(null);
            }

            tiles.clear();
            coordsList.clear();
        }
        else
        {
            changeAdviseMsg("You can pick more " + availableTiles + " tiles");
        }
    }

    /**
     *
     * @param tile
     * @author Pietro Andrea Niedda
     */
    private void tileBehavior(ImageView tile){
        Watch tileBehaviorWatch = new Watch("tileBehavior");
        tileBehaviorWatch.start();
        if(pickPhase) {
            if (tiles.contains(tile)) {
                tile.setEffect(null);
                tiles.remove(tile);
                coordsList.remove(imageToCoord.get(tile));
                System.out.println("Removed tile: "+ imageToCoord.get(tile));

                availableTiles++;
                changeAdviseMsg("You can pick more " + availableTiles + " tiles");
            } else if (availableTiles>0) {
                tile.setEffect(new Glow(1));
                tiles.add(tile);
                coordsList.add(imageToCoord.get(tile));

                availableTiles--;
                changeAdviseMsg("You can pick more " + availableTiles + " tiles");
            } else advertising.setText("can't choose more tiles");
        }
        else if(insertPhase){
            if(tiles.contains(tile)) {
                if (ordered.contains(tile)) {
                    tile.setEffect(null);
                    ordered.remove(tile);
                    selectedColumn=false;
                    changeAdviseMsg("Choose the order of your picked tiles");
                } else if (ordered.size() < 3) {
                    tile.setEffect(new Glow(1));
                    ordered.add(tile);
                    if(tiles.size() == ordered.size())
                    {
                        ArrayList<Tile> coordsList = new ArrayList<>();
                        for(ImageView iv : ordered)
                        {
                            Coordinates coord = imageToCoord.get(iv);
                            coordsList.add(getGui().getBoard()[coord.x()][coord.y()]);
                        }

                        try {
                            System.out.println("Choosed Order");
                            for(Tile currentT : coordsList)
                            {
                                System.out.println(currentT);
                            }
                            chooseOrderReqToServer.start();
                            getClientController().update(new Event(EventID.CHOOSE_ORDER, coordsList));
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else advertising.setText("Can't choose this  tile");
            }
        }

        tileBehaviorWatch.stop();
    }

    /**
     * It will change the image view contained in the board checking the tile array.
     * @author Abdullah Nasr
     */
    public void updateBoard()
    {

        Watch updateBoardWatch = new Watch("updateBoard");
        updateBoardWatch.start();
        if(!tilesIsMoving)
        {

            Tile[][] currboard = getGui().getBoard();

            for(int i=0;i< Board.getRowLength();i++) {
                for (int j = 0; j < Board.getColumnLength(); j++) {

                    if(boardTilesImages[i][j]!=null)
                    {
                        if(currboard[i][j]== null || currboard[i][j]==Tile.EMPTY)
                        {
                            boardTilesImages[i][j].setVisible(false);
                        }
                        else
                        {
                            boardTilesImages[i][j].setImage(getTileImage(currboard[i][j]));
                            boardTilesImages[i][j].setVisible(true);
                        }
                    }
                }
            }

        }

        updateBoardWatch.stop();
    }

    /**
     *
     * @author Pietro Andrea Niedda
     */
    public void fillBoard(){

        Tile[][] currboard = getGui().getBoard();
        int indexPosShelf = getRoot().getChildren().indexOf(mainShelf);
        
        for(int i=0;i< Board.getColumnLength();i++)
        {
            for(int j=0;j<Board.getRowLength();j++)
            {
                if(currboard[j][i]!=null && currboard[j][i]!=Tile.EMPTY)
                {
                    ImageView tile = new ImageView();
                    tile.setImage(getTileImage(currboard[j][i]));
                    tile.setCursor(Cursor.HAND);
                    tile.setFitWidth(GuiObjectsHandler.getBoardTilesSizeWidth());
                    tile.setFitHeight(GuiObjectsHandler.getBoardTilesSizeHeight());
                    tile.setLayoutX(board.getLayoutX()+board.getFitWidth()*GuiObjectsHandler.getBoardTilesPosX(j));
                    tile.setLayoutY(board.getLayoutY()+board.getFitHeight()*GuiObjectsHandler.getBoardTilesPosY(i));
                    tile.setOnMouseClicked(event ->tileBehavior(tile));
                    System.out.println("I added "+currboard[j][i]+ "in position x: "+ GuiObjectsHandler.getBoardTilesPosX(i) + " y: "+GuiObjectsHandler.getBoardTilesPosY(j));
                    imageToCoord.put(tile,new Coordinates(j,i));
                    boardTilesImages[j][i]=tile;
                    //System.out.println("picked tile:"+i+","+j);
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

        System.out.println("resize done");

    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public void runScene() {

        advertising.setText("");

        initMainShelf();
        fillBoard();
        resize();
        //getScene().setRoot(getRoot());
        getStage().setScene(new Scene(getRoot()));

    }

}
