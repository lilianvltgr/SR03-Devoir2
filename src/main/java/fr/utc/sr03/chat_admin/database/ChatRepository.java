package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.Chat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Provides the repository interface for managing {@link Chat} entities.
 * This interface extends JpaRepository, enabling CRUD operations and
 * custom queries such as finding chats by chat ID or creator ID and deleting a chat by chat ID.
 */
public interface ChatRepository extends JpaRepository<Chat,Integer> {

    Optional<Chat> findChatsByChatId(long id);

    List<Chat> findChatByCreatorId(long id);
    @Transactional
    int deleteChatByChatId(long chatId);
}
