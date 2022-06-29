package kr.co.gpgp.web.api.user;

import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {

    private String name;
    private String email;
    private Role role;
    private String profileImageUrl;

    private UserRequest(String name, String email, Role role, String profileImageUrl) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.profileImageUrl = profileImageUrl;
    }

    public static UserRequest of(User user) {
        return new UserRequest(user.getName(), user.getEmail(), user.getRole(), user.getProfileImage());
    }

}
