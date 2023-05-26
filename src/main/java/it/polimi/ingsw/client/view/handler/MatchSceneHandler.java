package it.polimi.ingsw.client.view.handler;


import it.polimi.ingsw.resources.Tile;
import it.polimi.ingsw.server.model.Shelf;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;

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


    ImageView[][] mainShelfTiles = new ImageView[6][5];

    private boolean pickPhase = false;
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


        getGui().updatePlayerShelves(plShelves);

    }

    public void resetArrows(){
        col1.setCursor(null);
        col2.setCursor(null);
        col3.setCursor(null);
        col4.setCursor(null);
        col5.setCursor(null);
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

    public void callPick(){
        int n = 0;

        if(!pickPhase){
            advertising.setText("It's not pick phase");
            return;
        }
        if(tiles.size() == 0){
            advertising.setText("Must choose tiles first");
            return;
        }

        for(ImageView tile : tiles) pick(tile, n++);

        col1.setCursor(Cursor.HAND);
        col2.setCursor(Cursor.HAND);
        col3.setCursor(Cursor.HAND);
        col4.setCursor(Cursor.HAND);
        col5.setCursor(Cursor.HAND);
        pickPhase = false;
        insertPhase = true;
    }

    public void callInsert(){
        int n = 0;

        for(ImageView tile : ordered) insert(tile, n++);

        tiles.clear();
        ordered.clear();
        resetArrows();
    }

    private void insert(ImageView tile, int n){
        TranslateTransition translate = new TranslateTransition();

        tile.setFitWidth(32);
        tile.setFitHeight(32);
        translate.setNode(tile);
        translate.setDuration(Duration.millis(1000));
        translate.setByX((249+47*column)-(tile.getLayoutX()+tile.getTranslateX()));
        translate.setByY((431-38*n)-(tile.getLayoutY()+tile.getTranslateY()));
        tile.setEffect(null);
        tile.setOnMouseClicked(null);
        translate.play();

        insertPhase = false;
        pickPhase = true;
    }

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
        resetArrows();
        callInsert();
    }

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
        resetArrows();
        callInsert();
    }

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
        resetArrows();
        callInsert();
    }

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
        resetArrows();
        callInsert();
    }

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
        resetArrows();
        callInsert();
    }

    public void sendMsg(){
        if(!txtField.getText().equals("")){
            txtArea.appendText("palceholder" + ": " + txtField.getText() + "\n");
            txtField.setText("");
        }
    }

    private void pick(ImageView tile, int n) {
        TranslateTransition translate = new TranslateTransition();
        double x = (228+50*n)-tile.getLayoutX(), y = 53-tile.getLayoutY();

        translate.setNode(tile);
        translate.setDuration(Duration.millis(1000));
        translate.setToX(x);
        translate.setToY(y);
        tile.setEffect(null);
        translate.play();
//        translate.setOnFinished(e -> System.out.println(tile.getTranslateX()));
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
                    iv.setPreserveRatio(true);

                    double a =  mainShelf.getBoundsInParent().getHeight();
                    double b = GuiObjectsHandler.mainShelfPosY;
                    double test =a/b;

                    iv.setFitWidth(GuiObjectsHandler.getMainShelfTilesSizeWidth());

                    //iv.setLayoutX(GuiObjectsHandler.getMainShelfTilesPosX(i));
                    iv.setLayoutX(GuiObjectsHandler.getMainShelfTilesPosX(i));
                    iv.setLayoutY(GuiObjectsHandler.getMainShelfTilesPosY(j));
                    //iv.toFront();
                    getRoot().getChildren().add(indexPosShelf, iv);
                }
            }
        }

    }

    private void tileBehavior(ImageView iview){
        if(pickPhase) {
            if (tiles.contains(iview)) {
                iview.setEffect(null);
                tiles.remove(iview);
            } else if (tiles.size() < 3) {
                iview.setEffect(new Glow(1));
                tiles.add(iview);
            } else advertising.setText("can't choose more than 3 tiles");
        }
        if(insertPhase){
            if(tiles.contains(iview)) {
                if (ordered.contains(iview)) {
                    iview.setEffect(null);
                    ordered.remove(iview);
                } else if (ordered.size() < 3) {
                    iview.setEffect(new Glow(1));
                    ordered.add(iview);
                }
                else advertising.setText("Can't choose this  tile");
            }
        }
    }
    public void fillBoard(){ // ogni tile si distanzia di 53 lungo x & y ed ha dimensioni 46 (all'interno della board)
        double setx = 565, sety = 72;

        for (int i = 0; i < 9; i++) {
            for(int j = 0; j <  9 /*&& board.getSpaces[i][j] != null*/; j++) {
                //Image img = new Image(getTileImage(Tile.CATS));
                ImageView iview = new ImageView();
                iview.setImage(getTileImage(Tile.CATS));
                iview.setCursor(Cursor.HAND);
                iview.setFitWidth(46);
                iview.setFitHeight(46);
                iview.setLayoutY(sety+53*i);
                iview.setLayoutX(setx+53*j);
                iview.setPreserveRatio(true );
                iview.setOnMouseClicked(event ->tileBehavior(iview));
                getRoot().getChildren().add(iview);
                //root.getChildren().add(iview);
                //this.root = root;
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
                //currentImage.setFitHeight(currentImage.getFitHeight()*ratio_y);
            }
            else if(n instanceof Label currentLabel)
            {

                //currentLabel.setLayoutX(mainShelf.getLayoutX()+((mainShelf.getFitWidth()-currentLabel.getBoundsInParent().getWidth())/2));
                currentLabel.setFont(new Font(currentLabel.getFont().getFamily(),currentLabel.getFont().getSize()*ratio_y));
                currentLabel.setPrefWidth(currentLabel.getPrefWidth()*ratio_x);
                //currentLabel.setPrefHeight(currentLabel.getPrefHeight()*ratio_y);
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

        pickPhase=true;
        advertising.setText("");
        resize();
        test();
        initMainShelf();

        fillBoard();


        getScene().setRoot(getRoot());

    }
}
