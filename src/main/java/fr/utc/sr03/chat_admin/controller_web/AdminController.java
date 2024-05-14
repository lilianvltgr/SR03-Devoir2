package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/AdminController")
public class AdminController {
    private final UserRepository userRepository;

    @Autowired
    private AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/users")
    public String getUserList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/inactiveUsers")
    public String getInactiveUsers(Model model) {
        List<User> allUsers = userRepository.findAll();
        List<User> inactiveUsers = new ArrayList<>();
        for (User entry : allUsers) {
            if (!entry.isActive()) {
                inactiveUsers.add(entry);}
        }
        model.addAttribute("users", inactiveUsers);
        return "userList";
    }

    @GetMapping("/getUserForm")
    public String getUserForm() {
        return "newUserForm";
    } @GetMapping("")
    public String goHome() {
        return "homePage";
    }

    @GetMapping("/authentification")
    public String authentification() {
        return "AuthentificationAdmin";
    }

    @GetMapping("/adminHomePage")
    public String adminHomePage() {
        return "AdminHomePage";
    }

    @PostMapping("/addUser")
    public String addUser(Model model,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email,
                          @RequestParam("firstname") String firstname,
                          @RequestParam("lastname") String lastname,
                          @RequestParam("admin") boolean admin) {

        //addUser pourrait être supprimé pour être remplacé par saveAndFlush
        userRepository.addUser(admin, lastname, firstname, email, password);
        model.addAttribute("lastUserAdded", lastname+" "+firstname);
        return "newUserForm";
    }

    @PostMapping("/isAdmin")
    public String isAdmin(Model model, WebRequest request,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email) {
        List<User> users = userRepository.findAll();
        List<User> filteredUsers;
        for (User entry : users) {
            // filtre les valeurs qui commencent par `B`
            if (entry.getMail().equals(email) && (entry.getPassword().equals(password))) {
                model.addAttribute("currentAdmin", email);
                //TODO ajouter la vérification de la désactivation temporaire du compte
                request.setAttribute("email", email, WebRequest.SCOPE_SESSION);
                return "homePage";
            }
        }
        model.addAttribute("authFailed", true);
        return "AuthentificationAdmin";
        //Connexion Invalide
    }

    @GetMapping("/getAdmins")
    public List<User> getAdmins() {
        List<User> admins = userRepository.findAdminOnly();
        return admins;
    }

    @GetMapping("/getAdminsTemplate")
    public String getAdmins(Model model) {
        List<User> admins = userRepository.findAdminOnly();
        model.addAttribute("admins", admins);
        return "adminList";  // Assurez-vous que ceci correspond au nom du fichier dans /src/main/resources/templates
    }
}
