/**
 * Server controller is the core of the game.
 * All games are stored here to be played with full power by GameModelController.
 * It's defined as game states so every part of the match is clearly subdivided.
 * It the only one to modify models, and it talks to the virtual view as it was simply the client view.
 * All game rooms are managed here.
 */
package it.polimi.ingsw.server.serverController;