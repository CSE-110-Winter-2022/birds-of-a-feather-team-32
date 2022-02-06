package com.example.birdsofafeather;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.nio.charset.StandardCharsets;

public class FakedMessageListener extends MessageListener {

    private final MessageListener messageListener;
    private final Message message;

    public FakedMessageListener(MessageListener realMessageListener, int frequency, String messageStr) {
        this.messageListener = realMessageListener;
        this.message = new Message(messageStr.getBytes(StandardCharsets.UTF_8));
    }

    public void getMessage() {
        this.messageListener.onFound(message);
        return;
    }
}
