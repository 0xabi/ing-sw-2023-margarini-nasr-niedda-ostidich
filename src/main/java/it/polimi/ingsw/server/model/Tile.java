package it.polimi.ingsw.server.model;

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

    EMPTY

}
