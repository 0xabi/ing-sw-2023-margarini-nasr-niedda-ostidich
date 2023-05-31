package it.polimi.ingsw.server.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.general.exceptions.ConfigFileNotFoundException;
import it.polimi.ingsw.general.exceptions.NoTokensForPlayerNumberException;

import java.io.InputStreamReader;
import java.util.Objects;
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
     * @throws ConfigFileNotFoundException      if FileReader cannot read from json config file
     * @author Francesco Ostidich
     */
    public ScoringTokenStack(int playerNumber) {
        tokenStack = new Stack<>();
        JsonElement tokensElement = JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("configFiles/scoringTokenStack.json"))));
        JsonObject tokensObject = tokensElement.getAsJsonObject();
        JsonArray jsonTokens = tokensObject.get(String.valueOf(playerNumber)).getAsJsonArray();
        if (jsonTokens.contains(null))
            throw new NoTokensForPlayerNumberException("no player number config defined");
        for (int i = 0; i < jsonTokens.size(); i++)
            tokenStack.add(jsonTokens.get(i).getAsInt());
    }

    /**
     * Pop method for the tokens stack.
     *
     * @return the respective popped token points
     * @author Francesco Ostidich
     */
    protected int pop() {
        return tokenStack.pop();
    }

    /**
     * Getter for token stack
     *
     * @return scoring token points stack
     * @author Francesco Ostidich
     */
    public Stack<Integer> getTokenStack() {
        return tokenStack;
    }

}
