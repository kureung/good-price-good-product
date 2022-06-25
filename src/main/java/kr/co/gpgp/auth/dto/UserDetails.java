package kr.co.gpgp.auth.dto;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserDetails {
    private Long id;
    private String nickname;
    private String profileImageUrl;
    private String thumbnailImageUrl;

    public UserDetails(Long id, String nickname, String profile_image_url, String thumbnail_image_url) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profile_image_url;
        this.thumbnailImageUrl = thumbnail_image_url;
    }

    public static UserDetails of(Object principal) {
        Map<String, Object> attributes = ((OAuth2User) principal).getAttributes();
        Long id             = (Long) attributes.get("userId");
        String profileImageUrl  = (String) attributes.get("profile_image_url");
        String thumbnailImageUrl= (String) attributes.get("thumbnail_image_url");
        String nickname         = (String) attributes.get("nickname");

        return new UserDetails(id,nickname,profileImageUrl,thumbnailImageUrl);
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }
}
