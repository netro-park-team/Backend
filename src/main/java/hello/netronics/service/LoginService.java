package hello.netronics.service;


import hello.netronics.domain.Role;
import hello.netronics.domain.User;
import hello.netronics.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // DefaultOAuth2UserService를 사용하여 사용자 정보 로드
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 사용자 정보 추출
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // 이메일 도메인 검증 (예: ajou.ac.kr 도메인만 허용)
        if (email == null || !email.endsWith("@ajou.ac.kr")) {
            throw new OAuth2AuthenticationException("Invalid email domain. Only ajou.ac.kr emails are allowed.");
        }

        // DB에서 사용자 조회 또는 생성
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            // 필요 시 사용자 정보 업데이트
            user.setName(name);
            userRepository.save(user);
            log.info("Existing user updated: {}", email);
        } else {
            user = User.builder()
                    .name(name)
                    .email(email)
                    .role(Role.GUEST) // 기본 역할 설정
                    .build();
            userRepository.save(user);
            log.info("New user created: {}", email);
        }

        // 세션서장
        httpSession.setAttribute("userId", user.getId());

        // DefaultOAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                oAuth2User.getAttributes(),
                "name" // "name" 속성을 username으로 사용
        );
    }

}
