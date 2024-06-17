package fr.utc.sr03.chat_admin.model;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * ChatUser class that joins the Chat class and the User class.
 * Allows users to be part of a chat.
 */
@Entity
@Table(name = "sr03_chatUser")
@IdClass(ChatUserId.class)
public class ChatUser implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="userId")
    User user;

    @Id
    @ManyToOne
    @JoinColumn(name="chatId")
    Chat chat;

    public ChatUser(User newUser, Chat newChat){
        user = newUser;
        chat = newChat;
    }

    public ChatUser() {

    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
