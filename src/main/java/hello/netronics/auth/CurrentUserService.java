package hello.netronics.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService { //스프링 시큐르티 컨테스트에서 userid를 뽑아내기 위한 로직

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof SessionUser) {
            return ((SessionUser) principal).getId();
        } else {
            throw new RuntimeException("사용자 정보를 가져올 수 없습니다.");
        }
    }
}
