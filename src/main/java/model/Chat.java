package model;

import java.sql.Time;
import java.util.Date;

public class Chat {
    int chatId;
    Date creationDate;
    Time dureeValidite;
    String titre;
    String description;

    public Chat(int chatId, Date creationDate, Time dureeValidite, String titre, String description) {
        this.chatId = chatId;
        this.creationDate = creationDate;
        this.dureeValidite = dureeValidite;
        this.titre = titre;
        this.description = description;
    }
    //region setters and getters

    public int getChatId() {
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

    public Time getDureeValidite() {
        return dureeValidite;
    }

    public void setDureeValidite(Time dureeValidite) {
        this.dureeValidite = dureeValidite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //endregion

    public static void main(String[] args) {

    }
}
