package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        bag.refillBoard(this);
    }

    public boolean selectTiles(List<Coordinates> selection) {
        //check whether selection is legit and if true empties the tiles selected on the board
        //uses method checkSelection()
        //uses method emptyTiles()
        return false;
    }

    public boolean checkSelection(List<Coordinates> selection) {
        //check whether selection is legit
        return false;
    }

    private void emptyTiles(List<Coordinates> selection) {
        //empties the tile selected on board
    }

    /**
     *checks if the board isRefillable
     * @author: Edoardo
     * @return: true or false
     */
    public boolean checkToRefill() {

        for(int i = 0; i < rowLength; i++)
            for(int j = 0; j < columnLength; j++) {
                if(isCompletelyFree(i,j)==false)
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

}
