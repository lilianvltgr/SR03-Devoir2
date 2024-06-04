package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.ChatRepository;
import fr.utc.sr03.chat_admin.database.ChatUserRepository;
import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.Chat;
import fr.utc.sr03.chat_admin.model.ChatUser;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.utc.sr03.chat_admin.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


@Controller
@ResponseBody
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
    public User getUserInfos(@PathVariable Long userId, WebRequest request) {
//        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
//        if (connected == null || !connected.toString().equals("true")) {
//            return null;
//        }
        // throw error if there is no connexion ??
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        //no user found
        return null;
    }   @GetMapping("/getUserByMail")
    public User getUserByMail(String mail, WebRequest request) {
        Optional<User> userOptional = userRepository.findUserByMail(mail);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        //no user found
        return null;
    }
@CrossOrigin(origins = "http://localhost:3000")
@GetMapping ("/getUsersInChat")
public List<User> getUsersInChat(@RequestParam Long chatId, WebRequest request) {
    Optional<Chat> chat = chatRepository.findChatsByChatId(chatId);
    if (chat.isPresent()) {
        List<ChatUser> chatUserList = chatUserRepository.findChatUsersByChat(chat.get());
        return chatUserRepository.getUserFromChatUserList(chatUserList);
    }
    return null;
}
    @GetMapping("/addUserToChat")
    public ChatUser adUserToChat(@RequestParam Long userId, @RequestParam Long chatId, WebRequest request) {
        Optional<User> user = userRepository.findByUserId(userId);
        Optional<Chat> chat = chatRepository.findChatsByChatId(chatId);
        System.out.println("user : " + user.isPresent());
        System.out.println("chat : " + chat.isPresent());

        if (user.isPresent() && chat.isPresent()) {
            ChatUser chatUser = new ChatUser(user.get(), chat.get());
            return chatUserRepository.saveAndFlush(chatUser);
        }
        return null;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chatsCreatedBy/{userId}")
    public List<Chat> getChatsUser(WebRequest request, @PathVariable Long userId) {
        return chatRepository.findChatByCreatorId(userId);
    }

//    @PostMapping("/createChat")
//    public Chat createChat(String title, String description, Integer duration, Integer creatorId, WebRequest request) {
//
//        System.out.println("title : "+ title);
//        System.out.println("creatorId : "+ creatorId);
//        LocalDateTime currentDate = LocalDateTime.now();
//        Long creatorIdLong = Long.valueOf(creatorId);
//        Chat chat = new Chat(currentDate, duration, title, description, creatorIdLong);
//        return chatRepository.save(chat);
//    }
    @PostMapping("/createChat")
    public Chat createChat(@RequestBody Chat chat, WebRequest request) {
        return chatRepository.save(chat);
    }

    @PostMapping("/createChatByHand")
    public Chat createChatByHand(WebRequest request) {

        Integer dureeValidite = 45;
        Long creatorId = 50L;
//        Date currentDate = new Date();
        LocalDateTime currentDate = LocalDateTime.now();
        Chat chat = new Chat(currentDate, dureeValidite, "test", "descriptionTest", creatorId);
        return chatRepository.save(chat);
    }

    @DeleteMapping("/deleteChat/{chatId}")
    public int deleteChat(@PathVariable Long chatId, WebRequest request) {
//        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
//        if (connected == null || !connected.toString().equals("true")) {
//            return false;
//        }
        return chatRepository.deleteChatByChatId(chatId);
    }

    //modifier chat
    @PostMapping("/updateChat")
    public Chat updateChat(Chat chat, WebRequest request) {
//        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
//        if (connected == null || !connected.toString().equals("true")) {
//            return null;
//        }
        return chatRepository.saveAndFlush(chat);
    }


}
