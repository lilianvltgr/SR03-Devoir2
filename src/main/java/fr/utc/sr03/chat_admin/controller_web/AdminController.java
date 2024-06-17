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
     * Function used to get every user from the database and display it.
     * @return a template
     */
    @GetMapping("/users")
    public String getUserList(Model model, WebRequest request) {
        // Authentification verification
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        //find users
        List<User> users = userRepository.findAll();
        // Add them to the model to display them in the template
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
        //Getting users
        Page<User> users = userRepository.findByActiveFalseAndLastnameContainingIgnoreCase(lastname.orElse(""), PageRequest.of(page.orElse(0), 5, sortCriteria));
        // Adding users to the model
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
    public String goHome(WebRequest request) {
        // Authentification verification
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
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
        // regular expression used for password creation
        String regExp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!:;,?/.@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
        // Test the password matching with the regexp
        Matcher matcher = pattern.matcher(user.getPassword());
        if(!matcher.matches()){
            FieldError password = new FieldError("user", "password", "Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial");
            result.addError(password);
            return "newUserForm";
        }
        // Try to find an user with the email
        Optional<User> existingUser = userRepository.findUserByMail(user.getMail());
        if (existingUser.isPresent()) {
            // If the mail already exists an error is put in the result
            FieldError mail = new FieldError("user", "mail", "Un utilisateur possède déjà cet email.");
            result.addError(mail);
        } else {
            // Finally the user is saved in the database
            userRepository.saveAndFlush(user);
            // Adding the usernames to the model in order to display it in the template as the last user added
            model.addAttribute("lastUserAdded", user.getLastname() + " " + user.getFirstname());
        }
        return "newUserForm";
    }
    /**
     * Function that test if the email corresponds to an admin user and verify if the password is correct
     * @return a template
     */
    @PostMapping("/isAdmin")
    public String isAdmin(Model model, WebRequest request,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email) {
        // Getting all admin users
        List<User> users = userRepository.findAdminOnly();
        for (User entry : users) {
            if (entry.getMail().equals(email) && (entry.getPassword().equals(password))) {
                model.addAttribute("currentAdmin", email);
                model.addAttribute("userId", entry.getUserId());
                request.setAttribute("email", email, WebRequest.SCOPE_SESSION);
                request.setAttribute("connected", true, WebRequest.SCOPE_SESSION);
                request.setAttribute("isAdmin", true, WebRequest.SCOPE_SESSION);
                return "homePage";
            }
        }
        //If no user has been found, the template will display the fail
        model.addAttribute("authFailed", true);
        return "authentificationAdmin";
    }
//
//    @GetMapping("/connectedAdmin")
//    public String adminDashboard(HttpServletRequest request, Model model) {
//        User user = (User) request.getSession().getAttribute("user");
//
//        if (user != null) {
//            model.addAttribute("user", user);
//            return "adminHomePage";
//        } else {
//            return "authentificationAdmin";
//        }
//    }
//    @GetMapping("/getAdmins")
//    private List<User> getAdmins() {
//        return userRepository.findAdminOnly();
//    }
    /**
     * FUnction used to get admins and display them
     * @return the adminList template
     */
    @GetMapping("/getAdminsTemplate")
    public String getAdmins(Model model, WebRequest request) {
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        // Find users that are admins
        List<User> admins = userRepository.findAdminOnly();
        // Adding theme to the model to display them in the template
        model.addAttribute("admins", admins);
        return "adminList";  // Assurez-vous que ceci correspond au nom du fichier dans /src/main/resources/templates
    }
    /**
     * Function to delete the user using its id
     * @return an update template of users without the deleted one
     */
    @DeleteMapping("/delete/{userId}")
    public String deleteUser(Model model, @PathVariable Long userId, WebRequest request) {
        // Authentification validation
        Object connected = request.getAttribute("connected", WebRequest.SCOPE_SESSION);
        if (connected == null || !connected.toString().equals("true")) {
            return "authentificationAdmin";
        }
        userRepository.deleteByUserId(userId);
        return getUserList(model, request);
    }
    /**
     * Function that update an user
     * @return a template
     */
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
        // Creating a new user with the same Id that the one we want to update
        User user = new User(admin, active, lastname, firstname, email, password);
        user.setUserId(userId);
        // Update the user in the database
        userRepository.saveAndFlush(user);
        // Adding information to the model to display update in the template
        model.addAttribute("userInfos", user);
        model.addAttribute("userUpdated", true);
        return "userInfos";
    }
    /**
     * Function used to update the password when it is asked by the user (Simulates a password reinitialisation)
     * @return a template
     */
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("mail") String mail,
                                 @RequestParam("newPassword") String newPassword, WebRequest request) {
        // Looking for the user
        Optional<User> userOptional = userRepository.findUserByMail(mail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            //Updating the user's password
            user.setPassword(newPassword);
            userRepository.saveAndFlush(user);
        }
        // Going back to the authentification page
        return "authentificationAdmin";
    }
    /**
     * Function that redirects to the password reintialisation page
     * @return the template reinitialisationPage
     */
    @GetMapping("/resetPassword")
    public String resetPasswordPage() {
        return "reinitialisationPage";
    }
    /**
     * Function that get users in a page format
     * @return a template
     */
    @GetMapping("usersPage")
    public String getUserList(Model model, WebRequest request,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("lastname") Optional<String> lastname,
                              @RequestParam("sort") Optional<Integer> sort) {
        // Authentification verification
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
        Page<User> users = userRepository.findByLastnameContainingIgnoreCase(lastname.orElse(""), PageRequest.of(page.orElse(0), 5, sortCriteria));
        // Adding information in the model to display it in the template
        model.addAttribute("users", users.getContent());
        model.addAttribute("current_page", page.orElse(0));
        model.addAttribute("total_pages", users.getTotalPages());
        model.addAttribute("lastname", lastname.orElse(""));
        model.addAttribute("sort", sort.orElse(0));
        model.addAttribute("userPage", users);
        return "userList";
    }

    /**
     * Function that redirects for the authentification
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

