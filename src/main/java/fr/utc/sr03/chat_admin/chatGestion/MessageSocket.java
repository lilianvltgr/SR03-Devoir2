package fr.utc.sr03.chat_admin.chatGestion;

public class MessageSocket {
    private String user;
    private String message;

    public MessageSocket() {}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}