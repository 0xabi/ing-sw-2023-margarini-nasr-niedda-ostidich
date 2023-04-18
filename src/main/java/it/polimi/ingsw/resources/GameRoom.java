package it.polimi.ingsw.resources;

import java.util.LinkedList;

/**
 * Game room information saving.
 *
 * @author Francesco Ostidich
 * @param gameRoomName is game room's name string
 * @param creatorName is game room's creator's name string
 * @param totalPlayers is the maximum capacity of the game room
 * @param enteredPlayers is the list of players that have already entered the game room
 */
public record GameRoom(String gameRoomName, String creatorName, int totalPlayers, LinkedList<ClientData> enteredPlayers) {}
