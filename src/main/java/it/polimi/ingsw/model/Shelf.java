package it.polimi.ingsw.model;
import java.util.List;

public class Shelf {
    private static final int rowLength = 6;

    private static final int columnLength = 5;

    private Tile[][] positions;

    public Shelf(){
        positions = new Tile[rowLength][columnLength];
        for(int i = 0; i < rowLength; i++)
            for(int j = 0; j < columnLength; j++)
                positions[i][j] = null;
    }

    public Tile[][] getPositions() {
        return positions;
    }

    public Tile getPosition(Coordinates coordinates) {
        return positions[coordinates.x()][coordinates.y()];
    }

    public boolean insertInColumn(List<Tile> tiles, int column) {
        //inserts tiles in column with list order
        //uses method checkSpaceInColumn()
        return false;
    }

    private boolean checkSpaceInColumn(int selectionLength, int column) {
        //checks the remaining space in a certain column
        return false;
    }

    public boolean isFull(){
        for(int i = 0; i < rowLength; i++)
            if(positions[i][0] == null) return false;
        return true;
    }

}


