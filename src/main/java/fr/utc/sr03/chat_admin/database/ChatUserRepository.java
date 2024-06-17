package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.User;
import fr.utc.sr03.chat_admin.model.ChatUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface for managing {@link ChatUser} entity relationships between {@link Chat} and {@link User}.
 * Extends {@link JpaRepository} to provide common persistence methods and includes custom methods
 * defined in {@link ChatUserRepositoryCustom} for additional operations.
 */
public interface ChatUserRepository extends JpaRepository<ChatUser,Integer>, ChatUserRepositoryCustom {

    List<ChatUser> findChatUsersByChat(Chat chat);
    List<ChatUser> findChatUserByUser(User user);
    @Transactional
    void deleteChatUsersByChat(Chat chat);
    @Transactional
    void deleteChatUsersByChatAndUser(Chat chat, User user);

}
