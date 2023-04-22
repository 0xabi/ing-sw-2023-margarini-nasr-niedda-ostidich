package it.polimi.ingsw.resources.messages.MessageFromClient;
import it.polimi.ingsw.resources.Tile;

import java.util.*;
public class sendTiles extends MessageFromClient{

    protected List<Tile> content;
    public sendTiles(String PlayerID, List<Tile> ListOfTiles) {
        super("send Tiles", PlayerID);
        content=ListOfTiles;
    }

    public List<Tile> getContent(){
        return content;
    }
}
