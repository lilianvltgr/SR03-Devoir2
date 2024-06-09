package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.User;
import fr.utc.sr03.chat_admin.model.ChatUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser,Integer>, ChatUserRepositoryCustom {

    List<ChatUser> findChatUsersByChat(Chat chat);
    List<ChatUser> findChatUserByUser(User user);
    @Transactional
    void deleteChatUsersByChat(Chat chat);
    @Transactional
    void deleteChatUsersByChatAndUser(Chat chat, User user);

}
