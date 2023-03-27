package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.NoTokensForPlayerNumberException;

import java.util.Stack;

public class ScoringTokenStack {
    
    private final Stack<Integer> tokenStack;
    
    public ScoringTokenStack(int playerNumber) {
        if(playerNumber < 2 || playerNumber > 4) throw new NoTokensForPlayerNumberException("player number not available");
        tokenStack = new Stack<Integer>();
        if(playerNumber == 4) {
            tokenStack.add(8);
            tokenStack.add(6);
            tokenStack.add(4);
            tokenStack.add(2);
        }
        else if(playerNumber == 3) {
            tokenStack.add(8);
            tokenStack.add(6);
            tokenStack.add(4);
        }
        else {
            tokenStack.add(8);
            tokenStack.add(4);
        }
    }
    
    public int pop() {
        return tokenStack.pop();
    }
    
}
