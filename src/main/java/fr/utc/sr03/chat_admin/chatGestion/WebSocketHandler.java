package fr.utc.sr03.chat_admin.chatGestion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class WebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
    private final String wsServerName;
    private final List<WebSocketSession> sessions;
    private final List<MessageSocket> messageSocketsHistory;

    public WebSocketHandler(String wsServerName) {
        this.wsServerName = wsServerName;
        this.sessions = new ArrayList<>();
        this.messageSocketsHistory = new ArrayList<>();
    }

    /**
     * Connexion établie
     *
     * @param session
     * @throws IOException
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        LOGGER.info(session.getId());
        System.out.println("ajout de la sessison" + session.getUri());
        sessions.add(session);
        LOGGER.info("Connexion etablie sur " + this.wsServerName);
    }

    /**
     * Connexion fermee
     *
     * @param session
     * @param status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // Suppression de la session a la liste
        sessions.remove(session);
        LOGGER.info("Deconnexion de " + this.wsServerName);
    }

    /**
     * Reception d'un message
     *
     * @param session
     * @param message
     * @throws IOException
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        ObjectMapper mapper = new ObjectMapper();
        String receivedMessage = (String) message.getPayload();
        System.out.println("Received msg: " + receivedMessage);
        try {
            MessageSocket messageSocket = mapper.readValue(receivedMessage, MessageSocket.class);
            // Pour stocker le message dans l'historique
            messageSocketsHistory.add(messageSocket);
            // Envoi du message à tous les connectes
            this.broadcast(messageSocket,messageSocket.getChatId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void broadcast(MessageSocket messageSocket, String chatId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String messageJson = mapper.writeValueAsString(messageSocket);

        for (WebSocketSession session : sessions) {
            if (session != null) {
                String uri = session.getUri().toString();
                String chatIdSession = uri.split("chat/")[1];
                if (chatId.equals(chatIdSession)) {
                    System.out.println("Sending message to session " + session.getId());
                    session.sendMessage(new TextMessage(messageJson));
                }
            }
        }
    }
}