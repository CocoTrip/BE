package io.cocotrip.domain.auth.application;

import io.cocotrip.domain.user.persistence.User;
import io.cocotrip.domain.user.persistence.UserRepository;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * OAuth2UserCustomService OAuth2User를 커스터마이징하는 서비스 클래스
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    /**
     * OAuth2UserRequest를 받아서 UserRepositiry에 저장 혹은 닉네임 업데이트 후 OAuth2User를 반환하는 메소드
     *
     * @param userRequest OAuth2UserRequest 객체
     * @return OAuth2User 객체
     */
    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String oAuthType = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("OAuthType : " + oAuthType);
        saveOrUpdate(user);
        return user;
    }

    /**
     * OAuth2User를 받아서 UserRepository에 저장 혹은 닉네임 업데이트하는 메소드
     *
     * @param oAuth2User OAuth2User 객체
     * @return User 객체
     */
    private User saveOrUpdate(final OAuth2User oAuth2User) {
        final Map<String, Object> attributes = oAuth2User.getAttributes();
        for (String key : attributes.keySet()) {
            System.out.println(key + " : " + attributes.get(key));
        }
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        User user = userRepository.findByEmail(email).orElse(User.builder().email(email).nickname(name).build());
        return userRepository.save(user);
    }
}