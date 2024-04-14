package model;

import database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
/*
Classe service qui utilise les méthodes définies dans le repository
dans des méthodes qui lui sont propres
 */

@Service
public class UserService {
    private final UserRepository userRepository;

    /*
    Injectez les instances des repositories dans vos services
    en utilisant l'annotation @Autowired
     */
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public Optional<User> findUserById (Integer id) {
        return userRepository.findByUserId(id);
    }

}
