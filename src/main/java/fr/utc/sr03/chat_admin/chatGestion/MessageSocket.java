package fr.utc.sr03.chat_admin.chatGestion;

public class MessageSocket {
    private String message;
    private String user;

    private String userId;
    private String chatId;
    public String getUser() {
        return user;
    }
    public String getChatId() {
        return chatId;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public void setUser(String user) {
        this.user = user;
    }
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}