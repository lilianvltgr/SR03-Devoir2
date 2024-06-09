//package fr.utc.sr03.chat_admin.chatGestion;
//
//import java.util.Hashtable;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import javax.websocket.server.ServerEndpointConfig;
//@ServerEndpoint(value="/chatserver/{pseudo}", configurator = ChatServer.EndpointConfigurator.class)
//public class ChatServer {
//    private static ChatServer singleton = new ChatServer();
//    private ChatServer() {
//    }
//
//    /**
//     * Acquisition de notre unique instance ChatServer
//     */
//    public static ChatServer getInstance() {
//        return ChatServer.singleton;
//    }
//
//    /**
//     * On maintient toutes les sessions utilisateurs dans une collection.
//     */
//    private Hashtable<String, Session> sessions = new Hashtable<>();
//
//    /**
//     * Cette m�thode est d�clench�e � chaque connexion d'un utilisateur.
//     */
//    @OnOpen
//    public void open(Session session, @PathParam("pseudo") String pseudo ) {
//        System.out.println("open");
//        sendMessage( "Admin >>> Connection established for " + pseudo );
//        session.getUserProperties().put( "pseudo", pseudo );
//        sessions.put( session.getId(), session );
//    }
//
//    /**
//     * Cette m�thode est d�clench�e � chaque d�connexion d'un utilisateur.
//     */
//    @OnClose
//    public void close(Session session) {
//        System.out.println("close");
//
//        String pseudo = (String) session.getUserProperties().get( "pseudo" );
//        sessions.remove( session.getId() );
//        sendMessage( "Admin >>> Connection closed for " + pseudo );
//    }
//
//    /**
//     * Cette m�thode est d�clench�e en cas d'erreur de communication.
//     */
//    @OnError
//    public void onError(Throwable error) {
//        System.out.println( "Error: " + error.getMessage() );
//    }
//
//    /**
//     * Cette m�thode est d�clench�e � chaque r�ception d'un message utilisateur.
//     */
//    @OnMessage
//    public void handleMessage(String message, Session session) {
//        System.out.println("handleMessage");
//        String pseudo = (String) session.getUserProperties().get( "pseudo" );
//        String fullMessage = pseudo + " >>> " + message;
//
//        sendMessage( fullMessage );
//    }
//
//    /**
//     * Une m�thode priv�e, sp�cifique � notre exemple.
//     * Elle permet l'envoie d'un message aux participants de la discussion.
//     */
//    private void sendMessage( String fullMessage ) {
//        System.out.println("sendMessage");
//
//        // Affichage sur la console du server Web.
//        System.out.println( fullMessage );
//
//        // On envoie le message � tout le monde.
//        for( Session session : sessions.values() ) {
//            try {
//                session.getBasicRemote().sendText( fullMessage );
//            } catch( Exception exception ) {
//                System.out.println( "ERROR: cannot send message to " + session.getId() );
//            }
//        }
//    }
//
//    /**
//     * Permet de ne pas avoir une instance diff�rente par client.
//     * ChatServer est donc g�rer en "singleton" et le configurateur utilise ce singleton.
//     */
//    public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
//        @Override
//        @SuppressWarnings("unchecked")
//        public <T> T getEndpointInstance(Class<T> endpointClass) {
//            return (T) ChatServer.getInstance();
//        }
//    }
//}