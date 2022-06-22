package kr.co.gpgp.web.api.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.co.gpgp.auth.dto.SessionUser;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {
    private final UserService userService;
    private final UserRepository userRepository;

    //권한 변경
    @GetMapping("/role/{id}/{role}")
    public ResponseEntity<Void> roleUpdate(
            @PathVariable Long id,
            @PathVariable String role
    ) {
        userService.changeOfPermission(id, role);
        User user = userRepository.findById(id).get();


        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<SessionUser> roleUpdate(
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");

        return ResponseEntity.ok().body(user );
    }
}
