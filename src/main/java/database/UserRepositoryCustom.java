package database;

import model.Chat;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
Cette interface déclare les méthodes customisées pour la gestion de la bdd spécifique à ce projet
 */
// Utilisez l'interface, pas la classe
public interface UserRepositoryCustom {
    List<User> findAdminOnly();
    List<Chat> findChatsCreatedBy(long userId);
    List<User> findUsersInChat(long chatId);
    List<Chat> findChatsRelatedToUser(long userId);

}