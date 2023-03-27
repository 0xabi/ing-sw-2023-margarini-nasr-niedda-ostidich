package it.polimi.ingsw.model;
import java.sql.RowIdLifetime;
import java.util.List;
import java.io.*;

public class Shelf {
     private final int rowLength=6 ;
     private final int columnLength=5;
    private Tile[][] positions;

    public Shelf(){

        Tile[][] shelf = new Tile[rowLength][columnLength];

        for(int i = 0; i < rowLength; i++)
            for(int j = 0; j< columnLength; j++)
                positions[i][j] = Tile.EMPTY;
    }

    public int getRow(){
        return rowLength;
    }

    public int getColumn(){
        return columnLength;
    }

    public Tile[][]  getPositions() {
        return positions;
    }

    public Tile getPosition(Coordinates coord) {
        return positions[coord.x()][coord.y()];
    }

    public Tile getTile(int posX, int posY){
        return positions[posX][posY];
    }

    public int getTilesInColumn(int column){
        for(int i = 0; i < rowLength; i++)
            if(positions[i][column] != Tile.EMPTY) return rowLength-i;

        return rowLength;
    }

    public boolean insertInColumn(List<Tile> tiles, int column){
        int index = 1;

        while(index < rowLength + 1 && positions[index-1][column] == Tile.EMPTY) index++;

        for(int i = 0; i < tiles.size(); i++){
            positions[i][column] = tiles.get(i);
            index--;
        }

        return true;
    }

    private boolean checkSpaceInColumn(int x, int y){

    }

    public boolean isFull(){
        for(int j = 0; j< columnLength; j++)
            if(positions[0][j] == Tile.EMPTY) return false;

        return true;
    }
}


