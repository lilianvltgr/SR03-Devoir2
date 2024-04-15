package database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import model.Chat;
import model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> findAdminOnly(){
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.admin = :admin", User.class).setParameter("admin", true);
        // Execution de la requete (JPSQL)
        List<User> users = query.getResultList();
        return users;
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
    @Override
    public List<Chat> findChatsRelatedToUser(long userId){
        Query query = entityManager.createQuery("SELECT chat FROM ChatUser WHERE user = :userId", User.class).setParameter("userId", userId);
        return query.getResultList();
    }
}
