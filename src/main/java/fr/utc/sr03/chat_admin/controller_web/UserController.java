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
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/userInfos/{userId}")
    public User getUserInfos(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        //no user found
        return null;
    }

    @GetMapping("/getUserByMail")
    public User getUserByMail(String mail) {
        Optional<User> userOptional = userRepository.findUserByMail(mail);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        //no user found
        return null;
    }

    @GetMapping("/authentification")
    public long authentification(String mail, String password) {
        Optional<User> userOptional = userRepository.findUserByMail(mail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(Objects.equals(user.getPassword(), password)){
                return user.getUserId();
            }
        }
        return -1;
    }

//    @PostMapping("/authentification")
//    public long authentification(@RequestParam("mail") String mail,
//                                              @RequestParam("password") String password,
//                                              HttpServletRequest request,
//                                              HttpServletResponse response) {
//        Optional<User> userOptional = userRepository.findUserByMail(mail);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            if (Objects.equals(user.getPassword(), password)) {
//                // Création session et configuration cookie
//                request.getSession().setAttribute("user", user);
//                Cookie sessionCookie = new Cookie("JSESSIONID", request.getSession().getId());
//                sessionCookie.setHttpOnly(true);
//                sessionCookie.setPath("/");
//                sessionCookie.setSecure(true);
//                response.addCookie(sessionCookie);
//                return user.getUserId();
//            }
//        }
//        return -1;
//    }
    @GetMapping("/getAllActiveUsers")
    public List<User> getAllActiveUsers() {
        return userRepository.findAllByActive(true);
    }

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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chatsCreatedBy/{userId}")
    public List<Chat> getChatsUser(@PathVariable Long userId) {
        return chatRepository.findChatByCreatorId(userId);
    }

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

    @PostMapping("/createChat")
    public Chat createChat(@RequestBody Chat chat) {
        return chatRepository.save(chat);
    }

    @DeleteMapping("/deleteChat/{chatId}")
    public int deleteChat(@PathVariable Long chatId) {
        return chatRepository.deleteChatByChatId(chatId);
    }

    @DeleteMapping("/deleteChatUser/{chatId}")
    public void deleteChatUser(@PathVariable Long chatId) {
        Optional<Chat> optionalChat = chatRepository.findChatsByChatId(chatId);
        if (optionalChat.isPresent()) {
            chatUserRepository.deleteChatUsersByChat(optionalChat.get());
        }
    }

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
    @PostMapping("/updateChat")
    public Chat updateChat(Chat chat) {
        return chatRepository.saveAndFlush(chat);
    }


}
