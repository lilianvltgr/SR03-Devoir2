package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Integer userId) {
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

    @PutMapping("/{userId}/lastname")
    public ResponseEntity<String> modifyUserLastName(
            @PathVariable("userId") long userId,
            @RequestParam String lastname) {

        boolean updated = userRepository.modifyLastName(userId, lastname);

        if (updated) {
            return ResponseEntity.ok("User's lastname updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + userId);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") long userId) {
        try {
            boolean deleted = userRepository.deleteByUserId(userId);
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
