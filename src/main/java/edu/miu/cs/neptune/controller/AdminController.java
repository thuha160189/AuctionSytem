package edu.miu.cs.neptune.controller;

import edu.miu.cs.neptune.domain.ProfileVerificationType;
import edu.miu.cs.neptune.domain.User;
import edu.miu.cs.neptune.service.DMVService;
import edu.miu.cs.neptune.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    DMVService dmvService;

    //Temporary load template file
    @RequestMapping(value = "")
    public String loadTemplate() {
        return "Admin";
    }

    //List not verified profile
    @RequestMapping(value = "/profile/review_list")
    public String listPendingProfile(Model model) {
        List<User> users = userService.findAllPendingProfile();
        model.addAttribute("users", users);

        return "admin/ListReviewProfiles";
    }

    //Handle request verify
    @RequestMapping(value = "/profile/verify/{userId}")
    public String verify(@PathVariable Long userId) {
        Optional<User> optionalUser = userService.getById(userId);

        if(optionalUser.isPresent()) {
            User user = optionalUser.orElse(null);

            //call fake checking to validate user profile based on (email,licenseNumber)
            ProfileVerificationType verificationType = dmvService.verifyProfile(user);

            user.setProfileVerificationType(verificationType);
            userService.updateUser(user);
        }

        return "redirect:/admin/profile/review_list";
    }
}
