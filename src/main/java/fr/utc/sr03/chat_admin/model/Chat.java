package fr.utc.sr03.chat_admin.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Time;
import java.util.Date;
@Entity
@Table(name = "sr03_chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long chatId;
    long creatorId;
    Date creationDate;
    Time duration;
    String title;
    String description;

    public Chat(Date creationDate, Time dureeValidite, String titre, String description, long creatorId) {
        this.creationDate = creationDate;
        this.duration = dureeValidite;
        this.title = titre;
        this.description = description;
        this.creatorId = creatorId;

    }
    public Chat() {}
    //region setters and getters

    public long getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time dureeValidite) {
        this.duration = dureeValidite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titre) {
        this.title = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
//endregion

    public static void main(String[] args) {

    }
}
