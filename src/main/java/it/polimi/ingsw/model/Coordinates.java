package it.polimi.ingsw.model;

public record Coordinates(int x, int y) {}

//A record type class make the parameters as private final, and creates by itself methods to get them: x() and y()