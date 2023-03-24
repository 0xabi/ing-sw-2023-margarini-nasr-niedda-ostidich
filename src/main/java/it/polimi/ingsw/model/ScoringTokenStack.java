package it.polimi.ingsw.model;

import java.util.Stack;

public class ScoringTokenStack {
    
    private final Stack<Integer> tokenStack;
    
    public ScoringTokenStack(int playerNumber) {
        tokenStack = new Stack<Integer>();
        //push of tokens based on playerNumber
    }
    
    public int pop() {
        return tokenStack.pop();
    }
    
}
