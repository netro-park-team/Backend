package hello.netronics.controller;

import hello.netronics.service.LoginService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/login")
public class LoginController {


    @GetMapping
    public String login() {
        // 구글 OAuth 로그인 페이지로 리다이렉트
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/success")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User) {
        // 로그인 성공 시 사용자 정보 처리
        return "redirect:/home";  // 로그인 성공 후 보여줄 페이지
    }
}
