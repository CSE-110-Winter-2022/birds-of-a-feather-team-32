package com.example.birdsofafeather;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Our mock of the real message listener
 */
public class FakedMessageListener extends MessageListener {

    private final MessageListener messageListener;
    private final ArrayList<Message> allMessages;

    /**
     * Constructor that initializes the fields of this fake message listener
     * @param realMessageListener defines the behavior of the message listener to be mocked
     * @param messages a list of mock messages
     */
    public FakedMessageListener(MessageListener realMessageListener, ArrayList<String> messages) {
        this.messageListener = realMessageListener;
        allMessages = new ArrayList<>();
        for (String s : messages) {
            Message newMessage = new Message(s.getBytes(StandardCharsets.UTF_8));
            allMessages.add(newMessage);
        }
    }

    /**
     * Mocks the finding of a message
     */
    public void getMessage() {
        for (Message m : allMessages) {
            this.messageListener.onFound(m);
        }
        return;
    }
}
