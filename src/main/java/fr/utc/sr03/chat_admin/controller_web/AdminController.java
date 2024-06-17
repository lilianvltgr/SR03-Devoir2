package fr.utc.sr03.chat_admin.controller_web;

import fr.utc.sr03.chat_admin.database.UserRepository;
import fr.utc.sr03.chat_admin.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller managing admin interface in the application
 */
@Controller
@RequestMapping("/AdminController")
public class AdminController {
    private final UserRepository userRepository;
    @Autowired
    private AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Function used to get every user on the database and display it.
     * @return a template
     */
    @GetMapping("/users")
    public String getUserList(Model model, WebRequest request) {
        // Authentification verification
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    /**
     * Function used to get specific user information and display it.
     * @return a template
     */

    @GetMapping("/userInfos/{userId}")
    public String getUserInfos(Model model, @PathVariable Long userId, WebRequest request) {
        // Authentification verification
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        // Looking for the user
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User userInfos = userOptional.get();
            // Adding user infos to the model
            model.addAttribute("userInfos", userInfos);
        }
        return "userInfos";
    }
    /**
     * Function used to get every inactive user on the database and display it with pagination.
     * @return a template
     */
    @GetMapping("/inactiveUsers")
    public String getInactiveUsers(Model model, WebRequest request,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("lastname") Optional<String> lastname,
                                   @RequestParam("sort") Optional<Integer> sort) {
        // Authentification verification
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        // Gestion of the sort
        Sort sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "firstname");
        switch (sort.orElse(0)) {
            case 0:
                sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "firstname");
                break;
            case 1:
                sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "lastname");
                break;
        }
        Page<User> users = userRepository.findByActiveFalseAndLastnameContainingIgnoreCase(lastname.orElse(""), PageRequest.of(page.orElse(0), 2, sortCriteria));
        model.addAttribute("users", users.getContent());
        model.addAttribute("current_page", page.orElse(0));
        model.addAttribute("total_pages", users.getTotalPages());
        model.addAttribute("lastname", lastname.orElse(""));
        model.addAttribute("sort", sort.orElse(0));
        model.addAttribute("userPage", users);
        return "userList";
    }
    /**
     * Function used to get the new user form
     * @return a template
     */
    @GetMapping("/getUserForm")
    public String getUserForm(Model model, WebRequest request) {
        // Authentification verification
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        // Add the user to the model to link it to the template
        model.addAttribute("user", new User());
        return "newUserForm";
    }

    /**
     * Function used go back to the Home Page.
     * @return the home page template
     */
    @GetMapping("")
    public String goHome(WebRequest request, HttpSession session) {
        // Authentification verification
        System.out.println(session.getAttribute("connected"));
        System.out.println(session);
        Object connected = session.getAttribute("connected");
//        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        return "homePage";
    }
    /**
     * Function used to display the authentification template
     * @return a template
     */
    @GetMapping("/authentification")
    public String authentification() {
        return "authentificationAdmin";
    }
    /**
     * Function that "disconnects" the user by removing sessions attributes and forcing him to authentificate again
     * @return the home Page
     */
    @GetMapping("/disconnection")
    public String disconnection(WebRequest request) {
        request.removeAttribute("email", WebRequest.SCOPE_SESSION);
        request.removeAttribute("isAdmin", WebRequest.SCOPE_SESSION);
        request.removeAttribute("connected", WebRequest.SCOPE_SESSION);
        return "redirect:/AdminController";
    }
//    /**
//     * Function that redirects
//     * @return a template
//     */
//    @GetMapping("/redirectAuth")
//    public String redirectAuth(WebRequest request) {
//        Object isAdmin = request.getAttribute("isAdmin", WebRequest.SCOPE_SESSION);
//        if (isAdmin != null && isAdmin.toString().equals("true")) {
//            return "redirect:/AdminController/adminHomePage";
//        }
//        return "redirect:/AdminController/authentification";
//    }

    /**
     * Function used to access the admin home page
     * @return a template
     */
    @GetMapping("/adminHomePage")
    public String adminHomePage(WebRequest request) {
        // Authentification verification
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        return "adminHomePage";
    }
    /**
     * Function to add an user to the database with validation
     * @return a template
     */
    @PostMapping("/addUser")
    public String postTruc(@ModelAttribute @Valid User user, BindingResult result, Model model, WebRequest request) {
        // Authentification verification
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        if (result.hasErrors()) {
            return "newUserForm";
        }
        String regExp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!:;,?/.@#$%^&+=])(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
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
        for (User entry : users) {
            if (entry.getMail().equals(email) && (entry.getPassword().equals(password))) {
                System.out.println("test");
                model.addAttribute("currentAdmin", email);
                model.addAttribute("userId", entry.getUserId());
                request.setAttribute("email", email, WebRequest.SCOPE_SESSION);
                request.setAttribute("connected", true, WebRequest.SCOPE_SESSION);
                System.out.println("connected set");
                request.setAttribute("isAdmin", true, WebRequest.SCOPE_SESSION);
                return "homePage";
            }
        }
        model.addAttribute("authFailed", true);
        return "authentificationAdmin";
    }

    @GetMapping("/connectedAdmin")
    public String adminDashboard(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
            return "adminHomePage";
        } else {
            return "authentificationAdmin";
        }
    }

    @GetMapping("/getAdmins")
    private List<User> getAdmins() {
        return userRepository.findAdminOnly();
    }

    @GetMapping("/getAdminsTemplate")
    public String getAdmins(Model model, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        List<User> admins = userRepository.findAdminOnly();
        model.addAttribute("admins", admins);
        return "adminList";  // Assurez-vous que ceci correspond au nom du fichier dans /src/main/resources/templates
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(Model model, @PathVariable Long userId, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        userRepository.deleteByUserId(userId);
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
            return "authentificationAdmin";
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
                                 @RequestParam("newPassword") String newPassword, WebRequest request) {
        Optional<User> userOptional = userRepository.findUserByMail(mail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            userRepository.saveAndFlush(user);
        }
        return "authentificationAdmin";
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
            return "authentificationAdmin";
        }
        Sort sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "firstname");
        switch (sort.orElse(0)) {
            case 0:
                sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "firstname");
                break;
            case 1:
                sortCriteria = Sort.by(Sort.Direction.ASC, "lastname", "lastname");
                break;
        }
        Page<User> users = userRepository.findByLastnameContainingIgnoreCase(lastname.orElse(""), PageRequest.of(page.orElse(0), 2, sortCriteria));
        model.addAttribute("users", users.getContent());
        model.addAttribute("current_page", page.orElse(0));
        model.addAttribute("total_pages", users.getTotalPages());
        model.addAttribute("lastname", lastname.orElse(""));
        model.addAttribute("sort", sort.orElse(0));
        model.addAttribute("userPage", users);
        return "userList";
    }

    /**
     *
     * @return a template
     */
    @GetMapping("/redirectAuth")
    public String redirectAuth(WebRequest request) {
        Object isAdmin = request.getAttribute("isAdmin", WebRequest.SCOPE_SESSION);
        if (isAdmin != null && isAdmin.toString().equals("true")) {
            return "redirect:/AdminController/adminHomePage";
        }
        return "redirect:/AdminController/authentification";
    }
}

