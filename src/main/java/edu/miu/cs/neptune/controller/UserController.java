package edu.miu.cs.neptune.controller;

import edu.miu.cs.neptune.domain.Role;
import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.domain.UserVerificationType;
import edu.miu.cs.neptune.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //click Create User button, go to createUser page
    @RequestMapping("/create")
    public String createUserGet(@ModelAttribute("user") User user, Model model) {
        List<Role> roleList = Arrays.asList(Role.values())
                            .stream().filter(r->r!=Role.ROLE_ADMIN).collect(Collectors.toList());
        model.addAttribute("availableRoles", roleList);
        return "user/createUser";
    }

    //submit createUser page
    @PostMapping(value = "/create")
    public String createUserPost(User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    //click Forgot Password button, go to Forgot Password page
    @RequestMapping("/forgotPassword")
    public String forgotPasswordGet(Model model) {
        return "user/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordPost(@RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "email", required = false) String email,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        User user = userService.getByName(username).orElse(null);
        if (user == null) {
            String userError = "Input user is not exist";
            model.addAttribute("errorMessage", userError);
            return "user/forgotPassword";
        } else if (!email.equals(user.getEmail())) {
            String emailError = "Email input is not correct";
            model.addAttribute("errorMessage", emailError);
            return "user/forgotPassword";
        } else {
            redirectAttributes.addFlashAttribute("username", username);
            userService.forgotPasswordSendVC(user);
            return "redirect:/users/resetPasswordVerification";
        }
    }

    @GetMapping("/resetPasswordVerification")
    public String resetPasswordVerificationGet(@ModelAttribute(value = "username") String username, Model model) {
        //if user is not exist, need to go to accessDenied
        if (!StringUtils.hasText(username)) {
            return "redirect:/users/accessDenied";
        }else
        return "user/resetPasswordVerification";
    }

    @PostMapping("/resetPasswordVerification")
    public String resetPasswordVerificationPost(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "verificationCode", required = false) String verificationCode,
                                                Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getByName(username).orElse(null);
        if (verificationCode != null && user.getVerificationCreationTime().isBefore(LocalDateTime.now())) {
            return "redirect:/login";
        } else if (verificationCode != null && verificationCode.equals(user.getVerificationCode())) {
            user.setUserVerificationType(UserVerificationType.VERIFIED);
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("username", username);
            return "redirect:/users/resetPassword";
        } else if (user.getFailedVerificationCount() < 2) {
            user.increaseFailedVerificationCount();
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("username", username);
            return "redirect:/users/resetPasswordVerification";
        } else {
            user.resetFailedVerificationCount();
            userService.updateUser(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/resetPassword")
    public String resetPasswordGet(@ModelAttribute(value = "username") String username, Model model) {
        //if user is not exist, need to go to accessDenied
        if (!StringUtils.hasText(username)) {
            return "redirect:/users/accessDenied";
        } else
            return "user/resetPassword";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedGet() {
        return "user/accessDenied";
    }


    @PostMapping("/resetPassword")
    public String resetPasswordPost(@RequestParam(value = "username", required = false) String username,
//                                    @RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "newPassword", required = false) String newPassword,
                                    @RequestParam(value = "verifyNewPassword", required = false) String verifyNewPassword,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        User user = userService.getByName(username).orElse(null);
        if (!newPassword.equals(verifyNewPassword)) {
            String passwordError = "Input Passwords are not matched";
            model.addAttribute("errorMessage", passwordError);
            model.addAttribute("username", username);
            return "user/resetPassword";
        } else {
            user.setPassword(newPassword);
            userService.updatePassword(user);
            return "redirect:/login";
        }
    }
}
