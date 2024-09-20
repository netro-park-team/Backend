package hello.netronics.auth;

import hello.netronics.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Getter
public class SessionUser implements Serializable {

    private Long id;
    private String name;
    private String email;

    public SessionUser(User user) {
        this.id=user.getId();
        this.name = user.getName();
        this.email = user.getEmail();

    }
}