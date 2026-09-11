package assembly.general.api.controllers;


import assembly.general.api.dto.ProfileResponse;
import assembly.general.api.security.AuthUser;
import assembly.general.api.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name="bearerAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ProfileResponse profile(@AuthenticationPrincipal AuthUser user) {
        return userService.profile(user);
    }
}
