package it.polimi.ingsw.general;

/**
 * <p>Enumeration class that defines tiles' types.</p>
 * <p>Types of tiles:<br>
 * - CATS: green<br>
 * - BOOKS: white<br>
 * - GAMES: yellow<br>
 * - FRAMES: blue<br>
 * - TROPHIES: cyan<br>
 * - PLANTS: pink<br>
 * - EMPTY: no tile</p>
 * <p>EMPTY value represent a valid space in the board to be played, but without a tile, since it has been already picked up.</p>
 *
 * @author Francesco Ostidich
 */
public enum Tile {

    CATS,
    BOOKS,
    GAMES,
    FRAMES,
    TROPHIES,
    PLANTS,

    EMPTY;

    public void printColorForShelf() {
        switch (this) {
            case CATS -> System.out.print((char)27+ "[42m"+(char)27+ "[30m"+"C");
            case BOOKS -> System.out.print((char)27+  "[47m"+(char)27+ "[30m"+"B");
            case GAMES -> System.out.print((char)27+  "[43m"+(char)27+ "[30m"+"F");
            case FRAMES -> System.out.print((char)27+ "[44m"+(char)27+ "[30m"+"F");
            case TROPHIES -> System.out.print((char)27+  "[46m"+(char)27+ "[30m"+"T");
            case PLANTS -> System.out.print((char)27+  "[45m"+(char)27+ "[30m"+"P");
            case EMPTY -> {}
        }
    }
    public void printColorForBoard() {
        switch (this) {
            case CATS -> System.out.print((char)27+ "[42m"+(char)27+ "[30m"+" C ");
            case BOOKS -> System.out.print((char)27+  "[47m"+(char)27+ "[30m"+" B ");
            case GAMES -> System.out.print((char)27+  "[43m"+(char)27+ "[30m"+" G ");
            case FRAMES -> System.out.print((char)27+ "[44m"+(char)27+ "[30m"+" F ");
            case TROPHIES -> System.out.print((char)27+  "[46m"+(char)27+ "[30m"+" T ");
            case PLANTS -> System.out.print((char)27+  "[45m"+(char)27+ "[30m"+" P ");
            case EMPTY -> {}
        }
    }
}