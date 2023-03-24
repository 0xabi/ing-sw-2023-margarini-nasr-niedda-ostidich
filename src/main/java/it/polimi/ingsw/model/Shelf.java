package it.polimi.ingsw.model;
import java.util.List;

public class Shelf {
    private int rowLength=6 ;
    private int coloumnLength=5;
    private Tile positions[rowLengtyh][columnLength];

    public Shelf(){
        for(int i = 0; i < rowLength; i++)
            for(int j = 0; j< columnLength; j++)
                position[i][j] = EMPTY;
    }
    public Tile  getPositions() {

    }

    public Tile getPosition(Coordinates coord) {
        return positions[coord.x][coord.y];
}
    public boolean insertInColumn(List<Tile> Tiles, int column){

    }

    private boolean checkSpaceInColumn(int x, int y){

    }

    public boolean isFull(){
        for(int j = 0; j< columnLength; j++)
            if(positions[0][j] == EMPTY) return false;

        return true;
    }
}


