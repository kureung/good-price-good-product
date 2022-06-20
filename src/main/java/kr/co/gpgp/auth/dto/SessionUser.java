package kr.co.gpgp.auth.dto;

import java.io.Serializable;
import kr.co.gpgp.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String profileImage;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
    }

}