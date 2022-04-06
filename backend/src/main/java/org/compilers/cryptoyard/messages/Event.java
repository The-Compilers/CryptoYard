package org.compilers.cryptoyard.messages;

/**
 * A message which signals an event (without specifying how the listeners should react)
 * Use this for events which can be perceived differently by different receivers.
 */
public abstract class Event extends Message {
}
