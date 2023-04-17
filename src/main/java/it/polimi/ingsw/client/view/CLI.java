package it.polimi.ingsw.client.view;

import it.polimi.ingsw.resources.Coordinates;
import it.polimi.ingsw.resources.GameRoom;
import it.polimi.ingsw.resources.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CLI extends GameView {

    public CLI() {
        super();
    }

    @Override
    public void start() {
        System.out.println("My Shelfie\nLoading...");
    }

    @Override
    public String chooseIPAddress() {
        System.out.println("Choose IP address: ");
        Scanner scanner = new Scanner(System.in);
        String scannedIP = scanner.nextLine();
        while(!isIPAddress(scannedIP)) {
            scannedIP = scanner.nextLine();
        }
        return scannedIP;
    }

    private boolean isIPAddress(@NotNull String IP) {
        if(IP.equals("localhost")) return true;
        int j = 0, k = 0;
        char[] temp = new char[3];
        for(int i = 0; i < 4; i++) {
            while(IP.charAt(j) != '.') {
                try {
                    temp[k] = IP.charAt(j);
                } catch(IndexOutOfBoundsException e) {
                    return false;
                }
                k++;
                j++;
            }
            j++;
            k = 0;
            String tempString = String.copyValueOf(temp);
            try {
                if (Integer.parseInt(tempString) < 0 || Integer.parseInt(tempString) > 256)
                    return false;
            } catch(NumberFormatException e) {
                return false;
            }
        }
        return IP.length() == j;
    }

    @Override
    public String choosePlayerName() {
        return null;
    }

    @Override
    public String chooseNewOrJoin() {
        return null;
    }

    @Override
    public String chooseNewGameName() {
        return null;
    }

    @Override
    public int chooseNewGamePlayerNumber() {
        return 0;
    }

    @Override
    public void updateGameRoom(GameRoom gameRoom) {

    }

    @Override
    public void notifyGameHasStared() {
    }

    @Override
    public String chooseGameRoom(List<GameRoom> rooms) {
        return null;
    }

    @Override
    public List<Coordinates> pickTiles(int availablePickNumber) {
        return null;
    }

    @Override
    public List<Tile> chooseOrder(List<Tile> selection) {
        return null;
    }

    @Override
    public int chooseColumn() {
        return 0;
    }

    @Override
    public void assignCommonGoalPoints(String playerName, int token) {

    }

    @Override
    public void assignPersonalGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void assignAdjacentGoalPoints(Map<String, Integer> points) {

    }

    @Override
    public void announceWinner(String winnerName, Map<String, Integer> points) {

    }

    @Override
    public String waitForEndGameAction() {
        return null;
    }

}
