package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.User;

import java.util.List;

/*
Cette interface déclare les méthodes customisées pour la gestion de la bdd spécifique à ce projet
 */
// Utilisez l'interface, pas la classe
public interface UserRepositoryCustom {
    List<User> findAdminOnly();
    List<Chat> findChatsCreatedBy(long userId);
    List<User> findUsersInChat(long chatId);
    List<Chat> findChatsRelatedToUser(long userId);


    boolean addUser(boolean admin, String lastname, String firstname, String mail, String password);

    boolean modifyLastName(long id,String lastname);

}