package it.polimi.ingsw.model;

public class Shelf {
    final int rowLength=6 ;
    final int coloumnLength=5;
    private Tile[][] shelf;

    public Shelf(){
        shelf=new Tile[rowLength][coloumnLength];
    }
}


