package fr.utc.sr03.chat_admin.model;

import java.io.Serializable;

public class ChatUserId implements Serializable {
    long chat;
    long user;
    public ChatUserId() {
    }
    public long getChat() {
        return chat;
    }
    public void setChat(long chat) {
        this.chat = chat;
    }
    public long getUser() {
        return user;
    }
    public void setUser(long user) {
        this.user = user;
    }
}
