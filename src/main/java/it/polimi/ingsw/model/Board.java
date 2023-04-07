package it.polimi.ingsw.model;

import java.io.File;
import java.util.*;


public class Board {

    private static final int rowLength = 9;

    private static final int columnLength = 9;

    private Tile[][] spaces;

    private final Bag bag = new Bag();

    private Optional<EndGameToken> endGameToken;

    public Board(int num) {
        spaces = new Tile[rowLength][columnLength];
        endGameToken = Optional.of(new EndGameToken());

        if(num>=2 && num<5){

            for (int j = 3; j < 5; j++) {
                    spaces[1][j] = Tile.EMPTY;
                }
            for (int j = 3; j < 6; j++) {
                    spaces[2][j] = Tile.EMPTY;
                }
            for (int j = 2; j < 8; j++) {
                    spaces[3][j] = Tile.EMPTY;
                }
            for (int j = 1; j < 8; j++) {
                    spaces[4][j] = Tile.EMPTY;
                }
            for (int j = 1; j < 7; j++) {
                    spaces[5][j] = Tile.EMPTY;
                }
            for (int j = 3; j < 6; j++) {
                    spaces[6][j] = Tile.EMPTY;
                }
            for (int j = 4; j < 6; j++) {
                    spaces[7][j] = Tile.EMPTY;
                }

            if(num>=3){
                spaces[0][3]=Tile.EMPTY;
                spaces[2][6]=Tile.EMPTY;
                spaces[3][8]=Tile.EMPTY;
                spaces[6][6]=Tile.EMPTY;
                spaces[8][5]=Tile.EMPTY;
                spaces[6][2]=Tile.EMPTY;
                spaces[5][0]=Tile.EMPTY;
                spaces[2][2]=Tile.EMPTY;


            }

            if(num==4){
                spaces[0][4]=Tile.EMPTY;
                spaces[1][5]=Tile.EMPTY;
                spaces[4][8]=Tile.EMPTY;
                spaces[5][7]=Tile.EMPTY;
                spaces[8][4]=Tile.EMPTY;
                spaces[7][3]=Tile.EMPTY;
                spaces[4][0]=Tile.EMPTY;
                spaces[3][1]=Tile.EMPTY;

            }
        }

    }

    public void setEndGameToken(Optional<EndGameToken> endGameToken) {
        this.endGameToken = endGameToken;
    }

    public Tile[][] getSpaces() {
        return spaces;
    }

    public void setSpaces(Tile[][] spaces) {
        this.spaces = spaces;
    }

    public void setSpace(int x, int y, Tile tile){
        spaces[x][y]=tile;
    }

    public Bag getBag(){
        return bag;
    }
    public Optional<EndGameToken> getEndGameToken() {
        return endGameToken;
    }




    private boolean checkNoMoreTurns() {
        //check if it is the last turn of the last cycle
        return false;
    }

    public void refill() {
        //metto nella bag tutte quelle avanzate sul tavolo
        emptyBoardInBag();

        for (int i = 0; i < rowLength; i++)
            for (int j = 0; j < columnLength; j++) {
                if (spaces[i][j]== Tile.EMPTY) {
                    Tile t=bag.draw();
                    spaces[i][j] = t;
                }
            }




    }

    public List<Tile> selectTiles(List<Coordinates> selection) {

        List<Tile> list = new ArrayList<Tile>();

        if(checkSelection(selection)) {
            for(Coordinates e : selection)
                list.add(spaces[e.x()][e.y()]);

            emptyTiles(selection);

        }
        return list;
    }

    /**
     *A series of checks that allow to know if a move is allowed
     * @author: Edoardo
     * @param selection is a list of coords of the board
     * @return true if every check has positive feedback, false if one check has negative feedback
     * */
    public boolean checkSelection(List<Coordinates> selection) {
        //checks the player has chosen max 3 tiles
        if(selection.size()>3)
            return false;

        //checks the player has chosen available tiles (!=null !=Tile.EMPTY)
        for(Coordinates e : selection){
            if(spaces[e.x()][e.y()]==null || spaces[e.x()][e.y()]==Tile.EMPTY)
                return false;
        }

        //checks the player has chosen tiles that has a free adjacent
        for(Coordinates e : selection){
            if(!adjacentTile(e.x(),e.y()).contains(null) && !adjacentTile(e.x(),e.y()).contains(Tile.EMPTY))
                return false;
        }

        //checks the player has chosen 1 single tile, in this case all the tests made are sufficient
        if(selection.size()==1)
            return true;

        //checks the player has chosen tiles in coloumn or in row
        List<Integer> listX=new ArrayList<Integer>();
        List<Integer> listY=new ArrayList<Integer>();
        for(Coordinates e : selection){
            listX.add(e.x());
            listY.add(e.y());
        }
        if(!listX.stream().allMatch(s -> s.equals(listX.get(0))) && !listY.stream().allMatch(s -> s.equals(listY.get(0))))
            return false;


        List<Integer> list = new ArrayList<Integer>();

        if(listX.get(0)==listX.get(1))
            list = listY;
        else
            list = listX;

        Collections.sort(list);

        for(int i=0;i<list.size();i++){
            if(list.get(i)!=list.get(0)+i)
                return false;
        }

        return true;



    }

    /**
     *deletes tiles from board, they are ready to be placed on a shelf
     * @author: Edoardo
     * @param selection is a list of coords of the board
     * */
    private void emptyTiles(List<Coordinates> selection) {
        selection.forEach((e)->spaces[e.x()][e.y()]=Tile.EMPTY);
    }

    /**
     *checks if the board isRefillable
     * @author: Edoardo
     * @return: true or false
     */
    public boolean checkToRefill() {

        for(int i = 0; i < rowLength; i++)
            for(int j = 0; j < columnLength; j++) {
                if(!isCompletelyFree(i, j) && spaces[i][j]!=null && spaces[i][j]!=Tile.EMPTY)
                    return false;
            }

        return true;
    }


    /**
     *checks if a tile has no other adjacent tiles
     * @author: Edoardo
     * @param: Coordinates of a space in the board
     * @return: true or false
     */
    private boolean isCompletelyFree(int x, int y){
        if(adjacentTile(x,y).contains(Tile.CATS))
            return false;
        if(adjacentTile(x,y).contains(Tile.BOOKS))
            return false;
        if(adjacentTile(x,y).contains(Tile.FRAMES))
            return false;
        if(adjacentTile(x,y).contains(Tile.GAMES))
            return false;
        if(adjacentTile(x,y).contains(Tile.PLANTS))
            return false;
        if(adjacentTile(x,y).contains(Tile.TROPHIES))
            return false;

        return true;

    }

    /**
     *Adjacent tile
     * @author: Edoardo
     * @param: Coordinates of a space in the board
     * @return: a list of adjacent Tile
     */
    private List<Tile> adjacentTile(int x, int y){

        List<Tile> adjTile = new ArrayList<Tile>();

        if(x>7)
            adjTile.add(null);
        else
            adjTile.add(spaces[x+1][y]);

        if(x<1)
            adjTile.add(null);
        else
            adjTile.add(spaces[x-1][y]);

        if(y>7)
            adjTile.add(null);
        else
            adjTile.add(spaces[x][y+1]);

        if(y<1)
            adjTile.add(null);
        else
            adjTile.add(spaces[x][y-1]);

        return adjTile;
    }

    public Tile getTileInBoard(Coordinates coordinates) {
        return spaces[coordinates.x()][coordinates.y()];
    }

    /**
     *Puts all the tiles of the board in the bag
     * @author: Edoardo
     */
    private void emptyBoardInBag() {

        for (int i = 0; i < rowLength; i++)
            for (int j = 0; j < columnLength; j++) {
                if (spaces[i][j] != null && spaces[i][j] != Tile.EMPTY) {
                    bag.addTile(spaces[i][j]);
                    spaces[i][j] = Tile.EMPTY;
                }
            }
    }

}