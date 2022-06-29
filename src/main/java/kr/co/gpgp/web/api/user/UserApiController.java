package kr.co.gpgp.web.api.user;

import kr.co.gpgp.auth.dto.UserDetails;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;

    @PutMapping
    public ResponseEntity<Void> roleUpdate(
            @RequestParam("role") Role role,
            Authentication authentication
    ) {
        UserDetails userDetails = UserDetails.of(authentication.getPrincipal());

        userService.changeOfPermission(userDetails.getId(), role);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public String homes(Model model, Authentication authentication) {
        UserDetails userDetails = UserDetails.of(authentication.getPrincipal());
        User user = userService.findOne(userDetails.getId());
        UserRequest userResponse = UserRequest.of(user);

        model.addAttribute("user", userResponse);
        return "mypage";
    }

}
