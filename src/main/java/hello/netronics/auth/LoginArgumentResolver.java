package hello.netronics.auth;

import hello.netronics.domain.User;
import hello.netronics.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { //user클래스에 대해서 적용
        // 파라미터에 @LoginUser 어노테이션이 있는지 확인
        return parameter.getParameterAnnotation(LoginUser.class) != null
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        // 세션에서 SessionUser 가져오기
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            throw new RuntimeException("세션에 없는 사용자");
        }
        // SessionUser의 ID를 통해 User 엔티티 조회
        Optional<User> userOptional = userRepository.findById(sessionUser.getId());
        return userOptional.orElse(null);
    }
}
