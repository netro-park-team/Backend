package hello.netronics.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomOAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String LOGIN_FAILURE_URL = "/login?error=true&message=";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "로그인에 실패했습니다.";

        if (exception instanceof OAuth2AuthenticationException) {
            errorMessage = exception.getMessage(); // 예: "Invalid email domain. Only ajou.ac.kr emails are allowed."
        }

        errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
        response.sendRedirect(LOGIN_FAILURE_URL + errorMessage);
    }
}
