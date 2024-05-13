package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
@RequestMapping("/AdminController")
public class AdminController {
    private final UserRepository userRepository;

    @Autowired
    private AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        return "";
    }

    @GetMapping("/users") // This function should call the template userList but it does not :(
    public String getUserList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

@GetMapping("/authentification")
public String authentification(){
        return "AuthentificationAdmin";
}
    @PostMapping("/isAdmin")
    public String isAdmin(Model model, WebRequest request,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email){
        List<User> users = userRepository.findAll();
        List<User> filteredUsers;
        for (User entry: users)
        {
            // filtre les valeurs qui commencent par `B`
            if (entry.getMail().equals(email) && (entry.getPassword().equals(password))){
                model.addAttribute("currentAdmin", email);
                request.setAttribute("email", email, WebRequest.SCOPE_SESSION );
                return "currentAdmin";
            }
        }
        model.addAttribute("currentAdmin", "");
        return "currentAdmin";
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
