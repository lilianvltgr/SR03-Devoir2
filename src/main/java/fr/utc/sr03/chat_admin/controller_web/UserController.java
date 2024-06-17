package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.ChatRepository;
import fr.utc.sr03.chat_admin.database.ChatUserRepository;
import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.ChatUser;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * Rest Controller managing user interface in the application.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/UserController")
public class UserController {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatUserRepository chatUserRepository;

    @Autowired
    private UserController(UserRepository userRepository, ChatRepository chatRepository, ChatUserRepository chatUserRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatUserRepository = chatUserRepository;
    }

    /**
     * Functiion that returns all user attributes.
     * @param userId
     * @return User infos
     */
    @GetMapping("/userInfos/{userId}")
    public User getUserInfos(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        //no user found
        return null;
    }

    /**
     * Function that returns all user attributes with the user's email.
     * The goal is for the email to be a second identifier for each user.
     * Two users cannot have the same email.
     * @param mail
     * @return User infos
     */
    @GetMapping("/getUserByMail")
    public User getUserByMail(String mail) {
        Optional<User> userOptional = userRepository.findUserByMail(mail);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        //no user found
        return null;
    }

    /**
     * Function that authentifies a user with his email and password.
     * @param mail
     * @param password
     * @param request
     * @param session
     * @return userId to store in the frontend
     */
    @PostMapping("/authentification")
    public long authentification(@RequestParam("mail") String mail,
                                              @RequestParam("password") String password,
                                              WebRequest request, HttpSession session
    ) {
        Optional<User> userOptional = userRepository.findUserByMail(mail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (Objects.equals(user.getPassword(), password)) {
                // Création session et configuration cookie
                request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
                request.setAttribute("connected", true, WebRequest.SCOPE_SESSION);
                session.setAttribute("connected", true);
                session.setAttribute("user", user);

// ------- PISTES DE RESOLUTION -----------
//                Cookie sessionCookie = new Cookie("JSESSIONID", request.getSession().getId());
//                sessionCookie.setHttpOnly(true);
//                sessionCookie.setPath("/");
//                sessionCookie.setSecure(true);
//                response.addCookie(sessionCookie);

                return user.getUserId();
            }
        }
        return -1;
    }

    /**
     * Function that a user list of all active users.
     * @return a user list of all active users
     */
    @GetMapping("/getAllActiveUsers")
    public List<User> getAllActiveUsers() {
        return userRepository.findAllByActive(true);
    }

    /**
     * Function that returns a userList of all users invited to the chat.
     * @param chatId
     * @return a userList
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getUsersInChat/{chatId}")
    public List<User> getUsersInChat(@PathVariable Long chatId) {
        Optional<Chat> chat = chatRepository.findChatsByChatId(chatId);
        if (chat.isPresent()) {
            List<ChatUser> chatUserList = chatUserRepository.findChatUsersByChat(chat.get());
            return chatUserRepository.getUserFromChatUserList(chatUserList);
        }
        return null;
    }

    /**
     * Function to add a user in the group chat.
     * Creates a new ChatUser row entity.
     * @param userId
     * @param chatId
     * @return ChatUser entity
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addUserToChat")
    public ChatUser adUserToChat(@RequestParam Long userId, @RequestParam Long chatId) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Chat> chat = chatRepository.findChatsByChatId(chatId);
        if (user.isPresent() && chat.isPresent()) {
            ChatUser chatUser = new ChatUser(user.get(), chat.get());
            return chatUserRepository.saveAndFlush(chatUser);
        }
        return null;
    }

    /**
     * Function that adds multiple users to the chat.
     * Creates new ChatUser row entities.
     * @param userIds
     * @param chatId
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addUsersToChat")
    public void adUserToChat(@RequestParam List<Long> userIds, @RequestParam Long chatId) {
        Optional<Chat> chat = chatRepository.findChatsByChatId(chatId);
        if (chat.isPresent()) {
            for (Long userId : userIds) {
                Optional<User> user = userRepository.findByUserId(userId);
                if (user.isPresent()) {
                    System.out.println("adding user" + userId + "to chat" + chatId);
                    ChatUser chatUser = new ChatUser(user.get(), chat.get());
                    chatUserRepository.saveAndFlush(chatUser);
                }
            }
        }
    }

    /**
     * Function that returns a chat list of all the chats created by one user.
     * @param userId
     * @return chat list
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chatsCreatedBy/{userId}")
    public List<Chat> getChatsUser(@PathVariable Long userId) {
        return chatRepository.findChatByCreatorId(userId);
    }

    /**
     * Fucntion that returns all the chats where the user was invited.
     * @param userId
     * @return a chat list
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/InvitedChatsFor/{userId}")
    public List<Chat> getInvitedChatsFor(@PathVariable Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            List<Chat> chats = new ArrayList<>();
            List<ChatUser> listChatUsers = chatUserRepository.findChatUserByUser(user.get());
            for (ChatUser chatUser : listChatUsers)
                if (chatUser.getChat().getCreatorId() != userId)
                    chats.add(chatUser.getChat());
            return chats;
        }
        return null;
    }

    /**
     * Function that creates a new chat.
     * @param chat
     * @return Chat entity
     */
    @PostMapping("/createChat")
    public Chat createChat(@RequestBody Chat chat) {
        return chatRepository.save(chat);
    }

    /**
     * Fucntion that deletes a chat
     * @param chatId
     * @return int
     */
    @DeleteMapping("/deleteChat/{chatId}")
    public int deleteChat(@PathVariable Long chatId) {
        return chatRepository.deleteChatByChatId(chatId);
    }

    /**
     * Function that deletes a user from a group chat.
     * Deletes corresponding entity from ChatUser table.
     * @param chatId
     */

    @DeleteMapping("/deleteChatUser/{chatId}")
    public void deleteChatUser(@PathVariable Long chatId) {
        Optional<Chat> optionalChat = chatRepository.findChatsByChatId(chatId);
        if (optionalChat.isPresent()) {
            chatUserRepository.deleteChatUsersByChat(optionalChat.get());
        }
    }

    /**
     * Function that deletes several users from a group chat.
     * Deletes corresponding entities from ChatUser table.
     * @param userIds
     * @param chatId
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/deleteChatUsers")
    public void deleteChatUsers(@RequestParam List<Long> userIds, @RequestParam Long chatId) {
        Optional<Chat> optionalChat = chatRepository.findChatsByChatId(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            for (Long userId : userIds) {
                Optional<User> optionalUser = userRepository.findByUserId(userId);
                if (optionalUser.isPresent()) {
                    chatUserRepository.deleteChatUsersByChatAndUser(chat, optionalUser.get());
                }
            }
        }
    }

    /**
     * Function that allows a chat to be modified.
     * The entity is modified and nos created.
     * @param chat
     * @return Chat entity
     */
    @PostMapping("/updateChat")
    public Chat updateChat(Chat chat) {
        return chatRepository.saveAndFlush(chat);
    }


}
