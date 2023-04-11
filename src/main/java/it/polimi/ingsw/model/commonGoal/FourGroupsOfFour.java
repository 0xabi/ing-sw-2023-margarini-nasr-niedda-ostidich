package it.polimi.ingsw.model.commonGoal;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another group.
 *
 * @author Edoardo Margarini
 */
public class FourGroupsOfFour extends CommonGoal {

    Tile[][] copy;

    Queue<Coordinates> queue = new LinkedList<>();

    private static final int dimGroup = 4;

    private static final int times = 4;

    public FourGroupsOfFour(int playerNumber) {
        super(playerNumber);
    }

    /**
     * @author Edoardo Margarini
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {

        copy = shelf.getPositions();
        int count=0;

        for (int i = 0; i < shelf.getRowNumber(); i++)
            for (int j = 0; j < shelf.getColumnNumber(); j++)
                if (copy[i][j] != Tile.EMPTY && copy[i][j] !=null) { //if it's full, checks whether part of a 4-tiles group
                    if(BelongToABlock(i,j)== dimGroup)
                       count++;
                }

        return count >= times;
    }


    private @NotNull List<Coordinates> adjacentTile(int x, int y) {

        List<Coordinates> adjTile = new ArrayList<>();

        if (x <= 4)
            if(copy[x + 1][y]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY) {
                Coordinates coords = new Coordinates(x+1,y);
                adjTile.add(coords);
            }

        if (x>=1)
            if(copy[x - 1][y]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY) {
                Coordinates coords = new Coordinates(x-1,y);
                adjTile.add(coords);
            }

        if (y <= 3)
            if(copy[x ][y+1]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY) {
                Coordinates coords = new Coordinates(x,y+1);
                adjTile.add(coords);
            }

        if (y >= 1)
            if(copy[x ][y-1]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY) {
                Coordinates coords = new Coordinates(x,y-1);
                adjTile.add(coords);
            }

        return adjTile;
    }


    private int BelongToABlock( int x, int y) {
        int count=0;

        queue.addAll(adjacentTile(x, y));
        copy[x][y]=Tile.EMPTY;
        count++;

        int xTemp;
        int yTemp;

        while(queue.size()>0){

            xTemp=queue.peek().x();
            yTemp=queue.peek().y();
            queue.remove();
            for(Coordinates e : adjacentTile(xTemp, yTemp)){
               if(!queue.stream().toList().contains(e))
                    queue.add(e);
            }
            copy[xTemp][yTemp] = Tile.EMPTY;
            count++;
        }

        return count;
    }

}