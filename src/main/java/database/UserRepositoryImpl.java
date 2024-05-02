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
        // Execution de la requête (JPSQL)
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
    @Override
    public boolean addUser(boolean admin, String lastname, String firstname, String mail, String password) {
        User newUser = new User(admin,true, 10,lastname, firstname,mail,password);
        entityManager.persist(newUser);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public boolean modifyLastName(long id, String lastname) {
        // Création et exécution de la requête UPDATE
        int updatedEntities = entityManager.createQuery(
                        "UPDATE User u SET u.lastname = :lastname WHERE u.id = :id")
                .setParameter("lastname", lastname)
                .executeUpdate();

        // Vérifier si des entités ont été mises à jour
        return updatedEntities > 0;
    }


}
