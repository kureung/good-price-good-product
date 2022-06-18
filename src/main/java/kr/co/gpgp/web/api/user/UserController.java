package kr.co.gpgp.web.api.user;

import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    //권한 변경
    @GetMapping("/role/{id}/{role}")
    public ResponseEntity<Void> roleUpdate(
            @PathVariable  Long id,
            @PathVariable   String role
    ){
        userService.changeOfPermission(id, role);
        User user = userRepository.findById(id).get();
        System.out.println(user.getRole());
        return ResponseEntity.ok().build();
    }
}
