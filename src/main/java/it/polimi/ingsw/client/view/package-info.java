/**
 * The view part of the project defines all the actions that controllers can make on the UI.
 * It's divided in CLI and GUI: when player chooses to player launches only one of them.
 * View classes never imports classes outside this package, actions can only be called on it via ViewActions.java interface.
 * When controller starts a method, usually it is to wait until player writes on the scanner, and after the inserted string
 * is checked for eligibility, it's passed as return data, so that the controller is to use the information provided.
 * In first place, client controller uses methods to ask initial information to the user so server connection can be build.
 * After that, the view is completely put in hands of the server, so it can directly call view actions remotely (RMI or Socket are invisible).
 * At the end of the game the control is given back to the client controller.
 */
package it.polimi.ingsw.client.view;