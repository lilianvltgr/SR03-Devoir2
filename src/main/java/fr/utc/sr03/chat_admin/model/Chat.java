package fr.utc.sr03.chat_admin.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "sr03_chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chatId;
    Long creatorId;
    LocalDateTime creationDate;
    Integer duration;
    String title;
    String description;

    public Chat(LocalDateTime creationDate, Integer duration, String titre, String description, Long creatorId) {
        this.creationDate = creationDate;
        this.duration = duration;
        this.title = titre;
        this.description = description;
        this.creatorId = creatorId;

    }
    public Chat() {}
    //region setters and getters

    public long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
