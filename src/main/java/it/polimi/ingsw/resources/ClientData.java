package it.polimi.ingsw.resources;

/**
 * Client/player information saving.
 *
 * @author Francesco Ostidich
 * @param playerName is the player's name string
 * @param clientIP is the IP address
 * @param gameRoom is the game room where player is playing
 */
public record ClientData(String playerName, String clientIP, GameRoom gameRoom) {}
