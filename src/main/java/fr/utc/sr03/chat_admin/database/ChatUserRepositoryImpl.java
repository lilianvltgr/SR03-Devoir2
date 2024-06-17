package fr.utc.sr03.chat_admin.database;
import fr.utc.sr03.chat_admin.model.ChatUser;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link ChatUserRepositoryCustom} interface.
 * This class provides custom repository functionalities to interact with {@link ChatUser} entities.
 */
@Repository
public class ChatUserRepositoryImpl implements ChatUserRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<User> getUserFromChatUserList(List<ChatUser> chatUserList){
        List<User> userList = new ArrayList<>();
        for (ChatUser chatUser : chatUserList){
            userList.add(chatUser.getUser());
        }
        return userList;
    }
}
