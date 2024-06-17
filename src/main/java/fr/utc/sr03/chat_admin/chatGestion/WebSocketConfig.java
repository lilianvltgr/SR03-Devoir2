package fr.utc.sr03.chat_admin.chatGestion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for WebSocket connections in the "SR03 Chat Server".
 * Enables WebSocket functionality and defines the endpoint and handler configurations
 * for managing WebSocket connections.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler("SR03 Chat Server"), "/chat/{chatId}").setAllowedOrigins("*");
    }
}