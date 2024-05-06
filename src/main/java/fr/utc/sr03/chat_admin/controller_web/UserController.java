package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/UserControlleur")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /*
    add one user to the database
     */
    @PostMapping("/userAdd")
    public ResponseEntity<String> addUser(@RequestBody User newUser) {
        try {
            userRepository.addUser(newUser.getAdmin(), newUser.getLastname(), newUser.getFirstname(), newUser.getMail(), newUser.getPassword());
            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
    get one user with his id in parameter
     */
    @GetMapping("getAllUser")
    public List<User> getUser() {
        List<User> users = userRepository.findAll();
        return users;
    }
    @GetMapping("getAdmins")
    public List<User> getAdmins() {
        List<User> admins = userRepository.findAdminOnly();
        return admins;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        System.out.println("hello world");
        try {
            Optional<User> userOptional = userRepository.findByUserId(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return ResponseEntity.ok(user); // JSON format with status 200 OK
            } else {
                return ResponseEntity.notFound().build(); // response 404 NOT FOUND if User not found
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
    Reflechir a si on fait un controller de modification global,
    ou 1 pour chaque parametre d'un User
    Si on fait un formulaire de modification on pourrait n'appeler qu'une fois le controller
    sinon faire separement
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            Integer deleted = userRepository.deleteByUserId(userId);
            if (deleted == 1) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
