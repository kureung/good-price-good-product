package kr.co.gpgp.auth.dto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private Long id;
    private String nickname;
    private String profileImageUrl;
    private String thumbnailImageUrl;
    private Set<GrantedAuthority> authorities;


    public UserDetails(Long id, String nickname, String profile_image_url, String thumbnail_image_url) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profile_image_url;
        this.thumbnailImageUrl = thumbnail_image_url;
    }

    public static UserDetails of(Object principal) {
        Map<String, Object> attributes = ((OAuth2User) principal).getAttributes();
        Long id                 = (Long) attributes.get("userId");
        String profileImageUrl  = (String) attributes.get("profile_image_url");
        String thumbnailImageUrl= (String) attributes.get("thumbnail_image_url");
        String nickname         = (String) attributes.get("nickname");

        return new UserDetails(id, nickname, profileImageUrl, thumbnailImageUrl);
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return "Not-Password";
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
