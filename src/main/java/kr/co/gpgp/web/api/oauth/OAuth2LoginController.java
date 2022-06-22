package kr.co.gpgp.web.api.oauth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class OAuth2LoginController {

    @GetMapping("/oauth2/not-authorized")
    public ResponseEntity<String> notAuthorized() {
        return ResponseEntity.ok().body("권한 부족 ");
    }

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
    public String logout() {
        return "/logout/oauth2/code/kakao";
    }

}
