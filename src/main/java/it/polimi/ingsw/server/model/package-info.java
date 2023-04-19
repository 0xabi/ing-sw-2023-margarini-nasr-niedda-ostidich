/**
 * Model package wants to define the game data structures, letting the server work with it by simply giving actions doable on it.
 * Looking on ModelActions.java interface the server knows what model is able to do.
 * Game model never imports classes outside its package, only the server controller can launch methods on it.
 */
package it.polimi.ingsw.server.model;