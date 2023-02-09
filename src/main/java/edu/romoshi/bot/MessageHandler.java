package edu.romoshi.bot;

public abstract class MessageHandler {

    private MessageHandler next;
    public MessageHandler(MessageHandler next) {
        this.next = next;
    }

    public MessageHandler getNext() { return this.next;}
}
