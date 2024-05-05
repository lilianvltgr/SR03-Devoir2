package fr.utc.sr03.chat_admin.model;

import jakarta.persistence.*;

import java.io.Serializable;

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


}
