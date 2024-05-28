package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat,Integer>, ChatRespositoryCustom {

    Optional<Chat> findChatsByChatId(long id);

    List<Chat> findChatByCreatorId(long id);
    @Transactional
    int deleteChatByChatId(long chatId);
}
