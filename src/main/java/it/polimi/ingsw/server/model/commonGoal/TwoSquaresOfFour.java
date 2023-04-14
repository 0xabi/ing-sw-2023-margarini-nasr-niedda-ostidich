package it.polimi.ingsw.server.model.commonGoal;

import it.polimi.ingsw.server.model.CommonGoal;
import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.server.model.Shelf;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

/**
 * Two groups each containing 4 tiles of the same type in a 2x2 square.
 * The tiles of one square can be different from those of the other square.
 *
 * @author Abdullah Nasr
 */
public class TwoSquaresOfFour extends CommonGoal {

    private static final int dimSquare = 2;

    private static final int times = 2;


    /**
     * <p>Class constructor.</p>
     * <p>It calls the super class constructor to generate tokens stack and given players map.</p>
     * @param playerNumber is the number of player of the game match
     * @author Abdullah Nasr
     */
    public TwoSquaresOfFour(int playerNumber)
    {
        super(playerNumber);
    }

    /**
     *
     * <p>check if the square in the specified coordinates is isolated i.e there are no
     * tiles of the same type adjacent to the contour tiles of the square.
     * </p>
     * @param shelf the player's shelf
     * @param cords the coordinates in which to check if the square is isolated
     * @return {@code true} if the square is isolated, {@code false} otherwise
     * @author Abdullah Nasr
     */
    private boolean isolatedSquare(@NotNull Shelf shelf, @NotNull Coordinates cords) {
        int x = cords.x();
        int y = cords.y();
        int row;
        int col;
        Tile type = shelf.getPosition(cords);
        Tile checkType;
        row = shelf.getRowNumber()-1;
        col = shelf.getColumnNumber()-1;

        for(int i = 0; i< dimSquare; i++) {
            //check up
            if(x!=0) {
                checkType = shelf.getPosition(new Coordinates(x-1,y+i));

                if(checkType!=Tile.EMPTY && type==checkType)
                    return false;
            }

            //check down
            if(x+dimSquare-1 != row) {
                checkType = shelf.getPosition(new Coordinates(x+dimSquare,y+i));

                if(checkType!=Tile.EMPTY && type==checkType)
                    return false;
            }

            //check left
            if(y!=0) {
                checkType = shelf.getPosition(new Coordinates(x+i,y-1));

                if(checkType!=Tile.EMPTY && type==checkType)
                    return false;
            }

            //check right
            if(y+dimSquare-1 != col) {
                checkType = shelf.getPosition(new Coordinates(x+i,y+dimSquare));

                if(checkType!=Tile.EMPTY && type==checkType)
                    return false;
            }

        }

        return true;
    }

    /**
     * <p>
     * check if there is a square of dimension {@code DIM_SQUARE} in the specified coordinates
     * the square begins from the specified coordinates and ends to the bottom right
     * </p>
     * @param shelf the player's shelf in which to check for the presence of a square in the specified coordinates
     * @param cords the coordinates in which to check for the presence of a square
     * @return {@code true} if there is a square of dimension {@code DIM_SQUARE} in the specified coordinates, {@code false} otherwise
     * @author Abdullah Nasr
     */
    private boolean checkSquareToBottomRight(@NotNull Shelf shelf, @NotNull Coordinates cords) {
        int x = cords.x();
        int y = cords.y();
        int row;
        int col;
        row = shelf.getRowNumber()-1;
        col = shelf.getColumnNumber()-1;

        //not out of the shelf
        if(y+dimSquare-1 <=  col && x+dimSquare-1 <= row) {
            for(int i = 0; i < dimSquare; i++) {
                Tile currentTile;
                Tile nextTile;

                //check if the row have the same type
                for(int j = 0; j < dimSquare-1; j++) {
                    currentTile = shelf.getPosition(new Coordinates(x+i,y+j));
                    nextTile = shelf.getPosition(new Coordinates(x+i,y+j+1));

                    if(nextTile==Tile.EMPTY || currentTile!=nextTile)
                        return false;}

                //check if the current row is the same to the next row
                //it's enough to check the first tile of the row because the row check we did
                if(i != dimSquare-1) {
                    currentTile = shelf.getPosition(new Coordinates(x+i,y));
                    nextTile = shelf.getPosition(new Coordinates(x+i+1,y));

                    if(nextTile==Tile.EMPTY || currentTile!=nextTile)
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * <p>
     * screen all the shelf to count the number of isolated squares i.e the square that do not have
     * tiles of the same type adjacent to the contour tiles of the square itself.
     * </p>
     * @param shelf player's shelf
     * @return the number of isolated squares found in the shelf
     * @author Abdullah Nasr
     */
    private int countIsolatedSquares(@NotNull Shelf shelf) {
        int count=0;
        int row = shelf.getRowNumber() -1;
        int col = shelf.getColumnNumber() -1;
        Coordinates currentCoordinate;

        for(int x=0;x<=row;x++) {
            for(int y=0;y<=col;y++) {
                currentCoordinate = new Coordinates(x,y);
                if(checkSquareToBottomRight(shelf,currentCoordinate) && isolatedSquare(shelf, currentCoordinate))
                    count++;
            }
        }
        return count;
    }


    /**
     * @author Abdullah Nasr
     */
    @Override
    public boolean check(@NotNull Shelf shelf) {
        return countIsolatedSquares(shelf) >= times;
    }

}
