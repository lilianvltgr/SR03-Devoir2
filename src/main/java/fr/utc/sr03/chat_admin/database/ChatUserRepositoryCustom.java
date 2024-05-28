package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.ChatUser;
import fr.utc.sr03.chat_admin.model.User;

import java.util.List;

/*
Cette interface déclare les méthodes customisées pour la gestion de la bdd spécifique à ce projet
 */
// Utilisez l'interface, pas la classe
public interface ChatUserRepositoryCustom {

    List<User> getUserFromChatUserList(List<ChatUser> chatUserList);
}