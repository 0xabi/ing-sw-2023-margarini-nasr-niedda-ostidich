package it.polimi.ingsw.resources;

/**
 * View answers the controller requests by sending an event object.
 *
 * @author Francesco Ostidich
 * @param eventName is the event ID of the method calling
 * @param value is the data to pass to the controller
 */
public record Event(EventID eventName, Object value) {}
