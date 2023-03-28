package it.polimi.ingsw.model;

import java.util.*;

/**
 * The board is a matrix of tiles. When a turn is ending a check to look for a refill scenario is launched, and if true the board
 * is refilled (except if the current turn is going to be the last).
 * Player selection is sent via a list of coordinates, which are checked for legality, and if they are, emptied from the board.
 * An optional containing an end game token tells whether the game match is to be finished at the end of the turn's cycle.
 *
 * @author
 */
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
            for (int j = 1; j < 5; j++) {
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

    public Optional<EndGameToken> getEndGameToken() {
        return endGameToken;
    }

    private boolean checkNoMoreTurns() {
        //check if it is the last turn of the last cycle
        return false;
    }

    public void refill() {
        //puts back in the back the ones left on the board
        emptyBoardInBag();

        for (int i = 0; i < rowLength; i++)
            for (int j = 0; j < columnLength; j++) {
                if (spaces[i][j]== Tile.EMPTY) {
                    Tile t=bag.draw();
                    spaces[i][j] = t;
                }
            }




    }

    public boolean selectTiles(List<Coordinates> selection) {
        if(checkSelection(selection)) {
            emptyTiles(selection);
            return true;
        }
        return false;
    }

    public boolean checkSelection(List<Coordinates> selection) {
        //checks the player has chosen max 3 tiles
        if(selection.size()>3)
            return false;

        //checks the player has chosen tiles that has a free adjacent
        for(Coordinates e : selection){
            if(!adjacentTile(e.x(),e.y()).contains(null) && !adjacentTile(e.x(),e.y()).contains(Tile.EMPTY))
                return false;
        }

        //checks the player has chosen tiles in column or in row
        List<Integer> listX= new ArrayList<>();
        List<Integer> listY= new ArrayList<>();
        for(Coordinates e : selection){
            listX.add(e.x());
            listY.add(e.y());
        }
        return listX.stream().allMatch(s -> s.equals(listX.get(0))) || listY.stream().allMatch(s -> s.equals(listY.get(0)));

    }

    private void emptyTiles(List<Coordinates> selection) {
        selection.forEach((e)->spaces[e.x()][e.y()]=Tile.EMPTY);
    }

    /**
     * checks if the board isRefillable
     *
     * @author Edoardo
     */
    public void checkToRefill() {

        for(int i = 0; i < rowLength; i++)
            for(int j = 0; j < columnLength; j++) {
                if(!isCompletelyFree(i, j))
                    return;
            }

        return true;
    }


    /**
     *checks if a tile has no other adjacent tiles
     * @author Edoardo
     * @param x is x of coordinate
     * @param y is y of coordinate
     * @return true or false
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
     * @author Edoardo
     * @param x is x of coordinate
     * @param y is y of coordinate
     * @return a list of adjacent Tile
     */
    private List<Tile> adjacentTile(int x, int y){

        List<Tile> adjTile = new ArrayList<>();

        if(x>7)
            adjTile.add(null);
        else
            adjTile.add(spaces[x+1][y]);

        if(x<7)
            adjTile.add(null);
        else
            adjTile.add(spaces[x-1][y]);

        if(y>7)
            adjTile.add(null);
        else
            adjTile.add(spaces[x][y+1]);

        if(y<7)
            adjTile.add(null);
        else
            adjTile.add(spaces[x][y-1]);

        return adjTile;
    }

    public Tile getTileInBoard(Coordinates coordinates) {
        return spaces[coordinates.x()][coordinates.y()];
    }

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
