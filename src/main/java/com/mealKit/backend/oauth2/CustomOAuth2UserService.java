package com.mealKit.backend.oauth2;

import com.mealKit.backend.domain.User;
import com.mealKit.backend.dto.GoogleResponse;
import com.mealKit.backend.dto.OAuth2Response;
import com.mealKit.backend.dto.UserDTO;
import com.mealKit.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        if (existData == null){
            User user = User.builder()
                            .email(oAuth2Response.getEmail()).
                            name(oAuth2Response.getName()).
                            role(User.role.ROLE_USER).build();
            userRepository.save(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(userDTO);
        }else{
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole().toString());

            return new CustomOAuth2User(userDTO);
        }

    }
}
