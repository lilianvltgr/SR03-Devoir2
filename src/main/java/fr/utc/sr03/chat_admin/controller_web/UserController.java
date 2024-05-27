package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import fr.utc.sr03.chat_admin.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
//@RestController
//@RequestMapping("/UserController")
//public class UserController {
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    /*
//    add one user to the database
//     */
//    @PostMapping("/userAdd")
//    public ResponseEntity<String> addUser(@RequestBody User newUser) {
//        try {
//            //addUser pourrait être supprimé pour être remplacé par saveAndFlush
//            userRepository.addUser(newUser.getAdmin(), newUser.getLastname(), newUser.getFirstname(), newUser.getMail(), newUser.getPassword());
//            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to add user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/userUpdate")
//    public ResponseEntity<String> updateUser(@RequestBody User newUser) {
//        try {
//            userRepository.saveAndFlush(newUser);
//            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to add user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /*
//    get one user with his id in parameter
//     */
//    @GetMapping("/getAllUsers")
//    public List<User> getUser() {
//        return userRepository.findAll();
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<?> getUser(@PathVariable Long userId) {
//        try {
//            Optional<User> userOptional = userRepository.findByUserId(userId);
//
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//                return ResponseEntity.ok(user); // JSON format with status 200 OK
//            } else {
//                return ResponseEntity.notFound().build(); // response 404 NOT FOUND if User not found
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to get user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    /*
//    Reflechir a si on fait un controller de modification global,
//    ou 1 pour chaque parametre d'un User
//    Si on fait un formulaire de modification on pourrait n'appeler qu'une fois le controller
//    sinon faire separement
//     */
//
//    @DeleteMapping("/{userId}")
//    @Transactional
//    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
//        try {
//            Integer deleted = userRepository.deleteByUserId(userId);
//            if (deleted == 1) {
//                return ResponseEntity.ok("User deleted successfully");
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to delete user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/findUserByEmail")
//    public ResponseEntity<User> findUserByEmail(@RequestParam String mail) {
//        Optional<User> userOptional = userRepository.findUserByMail(mail);
//        if (userOptional.isPresent()) {
//            return ResponseEntity.ok(userOptional.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}



@Controller
@ResponseBody
@RequestMapping("/UserController")
public class UserController{

    private final UserRepository userRepository;

    @Autowired
    private UserController(UserRepository userRepository) {
        this.userRepository = userRepository;}

    @GetMapping("/userInfos/{userId}")
    public User getUserInfos(Model model, @PathVariable Long userId, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return null;
        }
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        //no user found
        return null;
    }
    }