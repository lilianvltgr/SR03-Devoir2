package fr.utc.sr03.chat_admin.database;
import fr.utc.sr03.chat_admin.model.ChatUser;
import fr.utc.sr03.chat_admin.model.User;

import java.util.List;

/**
 * Custom repository interface providing additional query methods for {@link ChatUser} entities.
 * These methods include querying users from a chatUser list.
 */
public interface ChatUserRepositoryCustom {
    List<User> getUserFromChatUserList(List<ChatUser> chatUserList);
}