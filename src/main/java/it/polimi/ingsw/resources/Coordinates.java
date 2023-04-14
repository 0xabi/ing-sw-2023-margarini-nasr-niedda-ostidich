package it.polimi.ingsw.resources;

/**
 * A record type class make the parameters as private final, and creates by itself methods to get them: x() and y()
 *
 * @param x is the x dimension of the coordinate
 * @param y is the y dimension of the coordinate
 * @author Francesco Ostidich
 */
public record Coordinates(int x, int y) {}