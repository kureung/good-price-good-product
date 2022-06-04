package kr.co.gpgp.auth.dto;

import kr.co.gpgp.domain.user.entity.Role;
import kr.co.gpgp.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 스프링 시큐리티 OAuth 인증을 위한 속성 객체
 * ofNaver, ofKakao 등 of플랫폼명으로 된 메소드들의 리팩토링 여지가 있음.
 */
@Slf4j
@Getter
@ToString
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {

        log.info("유저이름 :: "+userNameAttributeName);
        log.info("속성 :: "+attributes);

        return ofKakao( "nickname", attributes);

    }
    private static OAuthAttributes ofKakao( String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        properties.put("id", attributes.get("id"));

        return OAuthAttributes.builder()
            .name((String) properties.get("nickname"))
            .email((String) properties.get("email"))
            .attributes(properties)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }
    public User toEntity(){
        return User.of(name,email, Role.USER);
    }
}
