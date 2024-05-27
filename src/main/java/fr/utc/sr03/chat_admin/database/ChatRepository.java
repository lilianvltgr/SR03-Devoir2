package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Integer>, ChatRespositoryCustom {

    int findChatsByChatId(long id);



}
