package fr.utc.sr03.chat_admin.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> findAdminOnly(){
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.admin = :admin", User.class).setParameter("admin", true);
        // Execution de la requête (JPSQL)
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
//    @Override
//    public List<Chat> findChatsRelatedToUser(long userId){
//        Query query = entityManager.createQuery("SELECT chat FROM ChatUser WHERE user = :userId", User.class).setParameter("userId", userId);
//        return query.getResultList();
//    }
    @Transactional
    @Override
    public boolean addUser(boolean admin, String lastname, String firstname, String mail, String password) {
        User newUser = new User(admin,true,lastname, firstname,mail,password);
        entityManager.persist(newUser);
        return true;
    }

}
