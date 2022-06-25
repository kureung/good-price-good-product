package kr.co.gpgp.auth.dto;

import java.util.Map;
import kr.co.gpgp.domain.user.Role;
import kr.co.gpgp.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String profileImage;
    private Long id;


    @Builder
    public OAuthAttributes(String name, String nameAttributeKey, Map<String, Object> attributes, String email, String profileImage, Long id) {
        this.name = name;
        this.nameAttributeKey = nameAttributeKey;
        this.attributes = attributes;
        this.email = email;
        this.profileImage = profileImage;
        this.id = id;

    }

    public static OAuthAttributes of(Map<String, Object> attributes) {
        return ofKakao((Map<String, Object>) attributes.get("kakao_account"));
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> map = (Map<String, Object>) attributes.get("profile");

        String name = (String) map.get("nickname");
        String email = (String) attributes.get("email");
        String url = (String) map.get("profile_image_url");

        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .attributes(map)
                .profileImage(url)
                .nameAttributeKey("nickname")
                .build();
    }

    public User toEntity() {
        return User.of(name, email, Role.USER, profileImage);
    }

}
