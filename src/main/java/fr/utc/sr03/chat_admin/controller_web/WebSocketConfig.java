//package fr.utc.sr03.chat_admin.controller_web;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(new MyWebSocketHandler(), "/chat/{chatId}").setAllowedOrigins("*");
//    }
//
//    private static class MyWebSocketHandler extends TextWebSocketHandler {
//        @Override
//        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//            // Logique pour manipuler les messages reçus
//            System.out.println("Received msg: " + message.getPayload());
//            session.sendMessage(message); // Echo back the message to the client
//        }
//    }
//}
