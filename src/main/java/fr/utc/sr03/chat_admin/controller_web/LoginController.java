//package controller_web;
//import database.UserRepository;
//import model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * URL de base du endpoint : http://localhost:8080/login
// */
//@Controller
//@RequestMapping("login")
//public class LoginController {
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping
//    public String getLogin(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }
//
//    @PostMapping
//    public String postLogin(@ModelAttribute User user, Model model) {
//        User loggedUser = userRepository.findByMailAndPassword(user.getMail(), user.getPassword());
//
//        if (loggedUser != null && loggedUser.isAdmin()){
//            return "redirect:/admin/users";
//        }
//        else{
//            model.addAttribute("invalid", true);
//            return "login";
//        }
//    }
//}
