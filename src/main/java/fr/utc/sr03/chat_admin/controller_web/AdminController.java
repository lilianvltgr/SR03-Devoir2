package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@Controller
@RequestMapping("/AdminController")
public class AdminController {
    private final UserRepository userRepository;

    @Autowired
    private AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public String getUserList(Model model, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/userInfos/{userId}")
    public String getUserInfos(Model model, @PathVariable Long userId, WebRequest request) {
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
    public String getInactiveUsers(Model model, WebRequest request,
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
        Page<User> users = userRepository.findByActiveFalseAndLastnameContainingIgnoreCase(lastname.orElse(""), PageRequest.of(page.orElse(0), 2, sortCriteria));
        model.addAttribute("users", users.getContent());
        model.addAttribute("current_page", page.orElse(0));
        model.addAttribute("total_pages", users.getTotalPages());
        model.addAttribute("lastname", lastname.orElse(""));
        model.addAttribute("sort", sort.orElse(0));
        model.addAttribute("userPage", users);
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

//    @PostMapping("/addUser")
//    public String addUser(@ModelAttribute @Valid User user, Model model,
//                          BindingResult result , WebRequest request) {
//
//        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
//        if (connected == null || !connected.toString().equals("true")) {
//            return "AuthentificationAdmin";
//        }
//        if (result.hasErrors()) {
//            return "newUserForm";
//        }
//
//        Optional<User> existingUser = userRepository.findUserByMail(user.getMail());
//        if (existingUser.isPresent()) {
//            ObjectError mail = new ObjectError("mail", "Email Already used");
//            result.addError(mail);
//            System.out.println("même e-mail");
//        } else {
//            userRepository.saveAndFlush(user);
//            model.addAttribute("lastUserAdded", user.getLastname() + " " + user.getFirstname());
//        }
//        return "newUserForm";
//    }
    @PostMapping("/addUser")
    public String postTruc(@ModelAttribute @Valid User user, BindingResult result, Model model, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "AuthentificationAdmin";
        }
        if (result.hasErrors()) {
            return "newUserForm";
        }
        String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!:;,?/.@#$%^&+=])(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(user.getPassword());

        if(!matcher.matches()){
            FieldError password = new FieldError("user", "password", "Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial");
            result.addError(password);
            return "newUserForm";

        }

        Optional<User> existingUser = userRepository.findUserByMail(user.getMail());
        if (existingUser.isPresent()) {
            FieldError mail = new FieldError("user", "mail", "Un utilisateur possède déjà cet email.");
            result.addError(mail);
        } else {
            userRepository.saveAndFlush(user);
            model.addAttribute("lastUserAdded", user.getLastname() + " " + user.getFirstname());
        }
        return "newUserForm";
    }

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
    public String getAdmins(Model model, WebRequest request) {
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
        return "userList";
    }

    //////
    @GetMapping("/truc")
    public String getTruc(Model model, WebRequest request) {
        model.addAttribute("user", new User()); // Ajoutez cette ligne pour lier le formulaire
        return "trucForm";
    }


}

