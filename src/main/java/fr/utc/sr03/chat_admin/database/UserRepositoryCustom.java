package fr.utc.sr03.chat_admin.database;

import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.ChatUser;
import fr.utc.sr03.chat_admin.model.User;

import java.util.List;

/**
 * Custom repository interface providing additional query methods for {@link User} entities.
 * These methods include querying for admin users, finding chats created by a user, and finding users within a specific chat.
 */
public interface UserRepositoryCustom {
    List<User> findAdminOnly();
    List<Chat> findChatsCreatedBy(long userId);
    List<User> findUsersInChat(long chatId);
}