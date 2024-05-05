package fr.utc.sr03.chat_admin.controller_web;


import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("Controlleur")
public class Controlleur {
        @Autowired
        private UserRepository userRepository;

        @PostMapping
        public String postLogin(User user, Model model){
            return "redirect: http://localhost:63342/chat_admin/src/main/resources/static/HTML/Accueil.html";
        }
}
