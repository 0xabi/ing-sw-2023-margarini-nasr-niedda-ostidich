package it.polimi.ingsw.server.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.ConfigFileNotFoundException;
import it.polimi.ingsw.exceptions.NoTokensForPlayerNumberException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Stack;

/**
 * It builds the stack based on the player number. It pops the last element of the stack and gives the points to the player.
 *
 * @author Francesco Ostidich
 */
public class ScoringTokenStack {
    
    private final Stack<Integer> tokenStack;

    /**
     * Class constructor.
     *
     * @param playerNumber is the number of players in the match
     * @throws NoTokensForPlayerNumberException if no player number configuration is defined
     * @throws ConfigFileNotFoundException if FileReader cannot read from json config file
     * @author Francesco Ostidich
     */
    public ScoringTokenStack(int playerNumber) {
        tokenStack = new Stack<>();
        File input = new File("src/main/java/it/polimi/ingsw/resources/configFiles/scoringTokenStack.json");
        try {
            JsonElement tokensElement = JsonParser.parseReader(new FileReader(input));
            JsonObject tokensObject = tokensElement.getAsJsonObject();

            JsonArray jsonTokens = tokensObject.get(String.valueOf(playerNumber)).getAsJsonArray();
            if(jsonTokens.contains(null)) throw new NoTokensForPlayerNumberException("no player number config defined");
            for(int i = 0; i < jsonTokens.size(); i++)
                tokenStack.add(jsonTokens.get(i).getAsInt());
        } catch (FileNotFoundException e) {
            throw new ConfigFileNotFoundException("scoringTokenStack not found");
        }
    }

    /**
     * Pop method for the tokens stack.
     *
     * @return the respective popped token points
     * @author Francesco Ostidich
     */
    public int pop() {
        return tokenStack.pop();
    }
    
}
