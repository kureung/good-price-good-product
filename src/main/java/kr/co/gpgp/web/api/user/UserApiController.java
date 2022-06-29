package kr.co.gpgp.web.api.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.co.gpgp.auth.dto.UserDetails;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {
    private final UserService userService;

    //권한 변경
    @GetMapping("/role/{id}/{role}")
    public ResponseEntity<Void> roleUpdate(
            @PathVariable Long id,
            @PathVariable String role
    ) {
        userService.changeOfPermission(id, role);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<Void> roleUpdate(
    ) {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public String roleup(Authentication authentication){
        UserDetails user = UserDetails.of(authentication.getPrincipal());

        return "mypage";
    }

}
