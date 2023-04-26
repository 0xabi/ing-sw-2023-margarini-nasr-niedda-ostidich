package it.polimi.ingsw.resources.messages.MessageFromClient;
import it.polimi.ingsw.resources.Coordinates;

import java.util.*;

public class sendCoords extends MessageFromClient{

    protected List<Coordinates> content;
    public sendCoords(String PlayerID, List<Coordinates> ListOfTiles) {
        super("send Coordinates", PlayerID);
        content=ListOfTiles;
    }

    public List<Coordinates> getContent(){
        return content;
    }
}