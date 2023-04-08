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

    private static final int groupsNumber = 4;

    private static final int tilesInGroup = 4;

    Tile[][] copy;
    Queue<Coordinates> queue = new LinkedList<Coordinates>();
    private final int DIM_GROUP= 4;
    private final int TIMES = 4;

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
                if (copy[i][j] != Tile.EMPTY && copy[i][j] !=null) { //se è piena allora cerco di capire se fa parte di un gruppo di 4 caselle adiacenti
                    if(BelongToABlock(i,j)==DIM_GROUP)
                       count++;
                }

        if(count>=TIMES)
            return true;
        return false;
    }


    private List<Coordinates> adjacentTile(int x, int y) {

        List<Coordinates> adjTile = new ArrayList<Coordinates>();

        if (x <= 4)
            if(copy[x + 1][y]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY){
                Coordinates coords = new Coordinates(x+1,y);
                adjTile.add(coords);
            }


        if (x>=1)
            if(copy[x - 1][y]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY){
                Coordinates coords = new Coordinates(x-1,y);
                adjTile.add(coords);
            }

        if (y <= 3)
            if(copy[x ][y+1]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY){
                Coordinates coords = new Coordinates(x,y+1);
                adjTile.add(coords);
            }

        if (y >= 1)
            if(copy[x ][y-1]==copy[x][y] && copy[x][y]!=null &&copy[x][y]!=Tile.EMPTY){
                Coordinates coords = new Coordinates(x,y-1);
                adjTile.add(coords);
            }

        return adjTile;
    }


    private int BelongToABlock( int x, int y){
        int count=0;

        for(Coordinates e : adjacentTile(x,y)){
            queue.add(e);
        }
        copy[x][y]=Tile.EMPTY;
        count++;

        int xtemp=0;
        int ytemp=0;

        while(queue.size()>0){

            xtemp=queue.peek().x();
            ytemp=queue.peek().y();
            queue.remove();
            for(Coordinates e : adjacentTile(xtemp, ytemp)){
               if(!queue.stream().toList().contains(e))
                    queue.add(e);
            }
            copy[xtemp][ytemp]=Tile.EMPTY;
            count++;
        }

        return count;
    }

}