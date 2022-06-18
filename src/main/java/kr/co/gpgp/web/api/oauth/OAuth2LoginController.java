package kr.co.gpgp.web.api.oauth;

import java.security.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

;

@Controller
@RequestMapping("/")
public class OAuth2LoginController {

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/index")
    public String inex() {
        return "/index";
    }
    @GetMapping("/logout")
    public String logout(){
        return "/logout/oauth2/code/kakao";
    }

}
