package io.cocotrip.domain.auth.application;

import io.cocotrip.domain.auth.persistence.PrincipalDetails;
import io.cocotrip.domain.user.persistence.User;
import io.cocotrip.domain.user.persistence.UserRepository;
import io.cocotrip.global.config.oauth.info.OAuth2UserInfo;
import io.cocotrip.global.config.oauth.info.OAuth2UserInfoFactory;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

//클래스 위치를 global로 옮겨야 하나?

/**
 * OAuth2UserCustomService OAuth2User를 커스터마이징하는 서비스
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
        OAuth2User oauth2user = super.loadUser(userRequest);
        String oAuthType = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("OAuthType : " + oAuthType);
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.createOAuth2MemberInfo(oAuthType, oauth2user.getAttributes());
        User user = saveOrGet(userInfo);
        return new PrincipalDetails(user, oauth2user.getAttributes());
    }


    /**
     * OAuth2UserInfo를 받아서 UserRepository에 저장 혹은 반환하는 메소드
     *
     * @param oAuth2UserInfo OAuth2UserInfo 객체
     * @return User 객체
     */
    private User saveOrGet(final OAuth2UserInfo oAuth2UserInfo) {

        String oauth2Id = oAuth2UserInfo.getProvider() + "_"
                + oAuth2UserInfo.getProviderId(); //중복이 발생하지 않도록 provider와 providerId를 조합
        String role = "ROLE_ADMIN"; //일반 유저
        Optional<User> user = userRepository.findByOauth2Id(oauth2Id);
        return user.orElseGet(() -> userRepository.save(User.builder()
                .oauth2Id(oauth2Id)
                .provider(oAuth2UserInfo.getProvider())
                .providerId(oAuth2UserInfo.getProviderId())
                .role(role)
                .email(oAuth2UserInfo.getEmail())
                .nickname(oAuth2UserInfo.getName())
                .build()));
    }


}
