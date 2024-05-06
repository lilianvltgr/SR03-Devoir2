package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * URL de base du endpoint : http://localhost:8080/admin<br>
 * ex users : http://localhost:8080/admin/users
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("users")
    public List<User> getAdmins() {
        List<User> admins = userRepository.findAll();
        return admins;
    }

}
