package it.polimi.ingsw.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.exceptions.ConfigFileNotFoundException;
import it.polimi.ingsw.model.exceptions.NoTokensForPlayerNumberException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Stack;

public class ScoringTokenStack {
    
    private final Stack<Integer> tokenStack;
    
    public ScoringTokenStack(int playerNumber) {
        tokenStack = new Stack<>();
        File input = new File("src/main/java/it/polimi/ingsw/model/configFiles/scoringTokenStack.json");
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
    
    public int pop() {
        return tokenStack.pop();
    }
    
}
