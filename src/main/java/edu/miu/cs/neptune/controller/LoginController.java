package edu.miu.cs.neptune.controller;

import edu.miu.cs.neptune.domain.*;
import edu.miu.cs.neptune.service.ProductService;
import edu.miu.cs.neptune.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @GetMapping(value = {"/","/login"})
    public String loginGet(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        String errorMessage = "";
        if (error != null) {
            errorMessage = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "user/login";
    }


    @GetMapping(value = {"/postlogin"})
    public String loginPost(Authentication authentication, Model model) {
        User user = userService.getByName(authentication.getName()).orElse(null);
        System.out.println(user.getEmail());
        if (user != null && UserVerificationType.NEED_TO_VERIFY.equals(user.getUserVerificationType())) {
            model.addAttribute("userId", user.getUserId());
            return "redirect:/verification";
        } else {
            if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/profile/review_list";
            }

            return "redirect:/index";
        }
    }

    @GetMapping(value = {"/index"})
    public String indexGet(Model model) {
        Page<Product> page = productService.findProductsByProductStateEquals(ProductState.SAVE_AND_RELEASE,1, "uploadDate", "decs");
        CustomerController cus = new CustomerController();
        cus.general(model, page, 1, "uploadDate", "decs");
        return "customer/productList";
    }

    @PostMapping("/resendVerificationCode")
    public String resendVerificationCodePost(Authentication authentication, Model model) {
        User user = userService.getByName(authentication.getName()).orElse(null);
        String mailSubject = "Resend verification code";
        userService.generateVerificationCode(user);
        userService.sendVerificationCode(mailSubject, user);
        userService.updateUser(user);
        return "redirect:/login";
    }

    @GetMapping("/verification")
    public String getVerification(Model model) {
        return "user/verification";
    }

    @PostMapping("/verification")
    public String postVerification(@RequestParam(value = "verificationCode", required = false) String verificationCode,
                                   Authentication authentication,
                                   Model model) {
        User user = userService.getByName(authentication.getName()).orElse(null);
        if (verificationCode != null && user.getVerificationCreationTime().isBefore(LocalDateTime.now())) {
            return "redirect:/login";
        } else if (verificationCode != null && verificationCode.equals(user.getVerificationCode())) {
            user.setUserVerificationType(UserVerificationType.VERIFIED);
            userService.updateUser(user);
            return "redirect:/index";
        } else if (user.getFailedVerificationCount() < 2) {
            user.increaseFailedVerificationCount();
            userService.updateUser(user);
            return "redirect:/verification";
        } else {
            user.resetFailedVerificationCount();
            userService.updateUser(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/denied")
    public String accessDenied() {
        return "user/accessDenied";
    }
}
