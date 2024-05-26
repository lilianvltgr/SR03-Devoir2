package fr.utc.sr03.chat_admin.controller_web;
import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/AdminController")
public class AdminController {
    private final UserRepository userRepository;

    @Autowired
    private AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public String getUserList(Model model,WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/userInfos/{userId}")
    public String getUserInfos(Model model, @PathVariable Long userId,WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User userInfos = userOptional.get();
            model.addAttribute("userInfos", userInfos);
        }
        return "userInfos";
    }

    @GetMapping("/inactiveUsers")
    public String getInactiveUsers(Model model,WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        List<User> allUsers = userRepository.findAll();
        List<User> inactiveUsers = new ArrayList<>();
        for (User entry : allUsers) {
            if (!entry.isActive()) {
                inactiveUsers.add(entry);
            }
        }
        model.addAttribute("users", inactiveUsers);
        return "userList";
    }

    @GetMapping("/getUserForm")
    public String getUserForm(Model model, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        model.addAttribute("user", new User()); // Ajoutez cette ligne pour lier le formulaire
        return "newUserForm";
    }


    @GetMapping("")
    public String goHome(WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        return "homePage";
    }

    @GetMapping("/authentification")
    public String authentification() {
        return "AuthentificationAdmin";
    }

    @GetMapping("/deconnexion")
    public String deconnexion(WebRequest request) {
        request.removeAttribute("email", WebRequest.SCOPE_SESSION);
        request.removeAttribute("isAdmin", WebRequest.SCOPE_SESSION);
        request.removeAttribute("connected", WebRequest.SCOPE_SESSION);

        return "redirect:/AdminController";
    }

    @GetMapping("/redirectAuth")
    public String redirectAuth(WebRequest request) {
        Object isAdmin = request.getAttribute("isAdmin", WebRequest.SCOPE_SESSION);
        if (isAdmin != null && isAdmin.toString().equals("true")) {
            return "redirect:/AdminController/adminHomePage";
        }
        return "redirect:/AdminController/authentification";
    }

    @GetMapping("/adminHomePage")
    public String adminHomePage(WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        return "AdminHomePage";
    }

    @PostMapping("/addUser")
    public String addUser(Model model,WebRequest request,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email,
                          @RequestParam("firstname") String firstname,
                          @RequestParam("lastname") String lastname,
                          @Param("admin") boolean admin) {

        //addUser pourrait être supprimé pour être remplacé par saveAndFlush
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        userRepository.addUser(admin, lastname, firstname, email, password);
        model.addAttribute("lastUserAdded", lastname + " " + firstname);
        return "newUserForm";
    }
//    @PostMapping("/addUser")
//    public String addUser(@Valid User user, BindingResult result, WebRequest request, Model model) {
//        // Vérifier l'état de la connexion de l'utilisateur
//        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
//        if (connected == null || !connected.toString().equals("true")) {
//            return "AuthentificationAdmin"; // Assurez-vous que le nom de vue est correct
//        }
//
//        // Gestion des erreurs de validation
//        if (result.hasErrors()) {
//            model.addAttribute("user", user);
//            return "newUserForm"; // Assurez-vous que le nom de vue est correct
//        }
//
//        // Sauvegarder l'utilisateur si aucune erreur de validation
//        userRepository.addUser(user.getAdmin(), user.getLastname(), user.getFirstname(), user.getMail(), user.getPassword());
//        model.addAttribute("lastUserAdded", user.getLastname() + " " + user.getFirstname());
//        return "newUserForm"; // Rediriger ou retourner selon la logique souhaitée
//    }

    @PostMapping("/isAdmin")
    public String isAdmin(Model model, WebRequest request,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email) {
        List<User> users = userRepository.findAll();
        List<User> filteredUsers;
        for (User entry : users) {
            if (entry.getMail().equals(email) && (entry.getPassword().equals(password))) {
                model.addAttribute("currentAdmin", email);
                //TODO ajouter la vérification de la désactivation temporaire du compte
                request.setAttribute("email", email, WebRequest.SCOPE_SESSION);
                request.setAttribute("connected", true, WebRequest.SCOPE_SESSION);
                System.out.println("connected set");
                request.setAttribute("isAdmin", true, WebRequest.SCOPE_SESSION);
                return "homePage";
            }
        }
        model.addAttribute("authFailed", true);
        return "AuthentificationAdmin";
        //Connexion Invalide
    }

    @GetMapping("/getAdmins")
    private List<User> getAdmins() {
        List<User> admins = userRepository.findAdminOnly();
        return admins;
    }

    @GetMapping("/getAdminsTemplate")
    public String getAdmins(Model model,WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        List<User> admins = userRepository.findAdminOnly();
        model.addAttribute("admins", admins);
        return "adminList";  // Assurez-vous que ceci correspond au nom du fichier dans /src/main/resources/templates
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(Model model, @PathVariable Long userId, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        Integer deleted = userRepository.deleteByUserId(userId);
        return getUserList(model, request);
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("firstname") String firstname,
                             @RequestParam("lastname") String lastname,
                             @Param("admin") boolean admin,
                             @Param("active") boolean active,
                             @RequestParam("userId") int userId,
                             Model model, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        User user = new User(admin, active, lastname, firstname, email, password);
        user.setUserId(userId);
        userRepository.saveAndFlush(user);
        model.addAttribute("userInfos", user);
        model.addAttribute("userUpdated", true);
        return "userInfos";
    }


    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("mail") String mail,
                                 @RequestParam("newPassword") String newPassword,
                                 Model model, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        Optional<User> userOptional = userRepository.findUserByMail(mail);
        System.out.println("User found: " + userOptional.isPresent());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.saveAndFlush(user);
            model.addAttribute("updateSuccess", "Votre mot de passe a été mis à jour avec succès.");
        } else {
            model.addAttribute("updateError", "Aucun utilisateur trouvé avec l'e-mail fourni.");
        }

        return "AuthentificationAdmin";
    }

    @GetMapping("/resetPassword")
    public String resetPasswordPage() {
        return "reinitialisationPage";
    }

    @GetMapping("usersPage")
    public String getUserList(Model model, WebRequest request,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("lastname") Optional<String> lastname,
                              @RequestParam("sort") Optional<Integer> sort) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        // Init du tri
        Sort sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "firstname");
        switch (sort.orElse(0)) {
            case 0:
                sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "firstname");
                break;
            case 1:
                sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "lastname");
                break;
        }
        // Recup des users
        Page<User> users = userRepository.findByLastnameContainingIgnoreCase(lastname.orElse(""), PageRequest.of(page.orElse(0), 2, sortCriteria));
        model.addAttribute("users", users.getContent());
        model.addAttribute("current_page", page.orElse(0));
        model.addAttribute("total_pages", users.getTotalPages());
        model.addAttribute("lastname", lastname.orElse(""));
        model.addAttribute("sort", sort.orElse(0));
        model.addAttribute("userPage", users);

        System.out.println("users: " + users.getContent());
        System.out.println("current_page: " + page.orElse(0));
        System.out.println("total_pages: " + users.getTotalPages());
        System.out.println("lastname: " + lastname.orElse(""));
        System.out.println("sort: " + sort.orElse(0));
        return "userList";
    }
}

