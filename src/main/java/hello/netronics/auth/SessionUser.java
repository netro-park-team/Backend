package hello.netronics.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class SessionUser implements OAuth2User {
    private Long id;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    public SessionUser(Long id, Map<String, Object> attributes,
                       Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.attributes = attributes;
        this.authorities = authorities;
    }
    public Long getId() {
        return id;
    }
    // OAuth2User 인터페이스 메소드 구현
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name"); // 구글의 경우 "name" 속성
    }
}
