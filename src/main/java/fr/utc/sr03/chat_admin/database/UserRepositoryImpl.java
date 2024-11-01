package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.ChatUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Implementation of the {@link UserRepositoryCustom} interface.
 * This class provides custom repository functionalities to interact with {@link User} entities.
 */
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<User> findAdminOnly(){
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.admin = :admin", User.class).setParameter("admin", true);
        return query.getResultList();
    }
    @Override
    public List<Chat> findChatsCreatedBy(long userId){
        Query query = entityManager.createQuery("SELECT c FROM Chat c WHERE c.creatorId = :userId").setParameter("userId", userId);
        return query.getResultList();
    }
    @Override
    public List<User> findUsersInChat(long chatId){
        Query query = entityManager.createQuery("SELECT user FROM ChatUser WHERE chat = :chatId").setParameter("chatId", chatId);
        return query.getResultList();
    }
}
