package com.myproject.project.service;

import com.myproject.project.model.dto.UserRegistrationDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    public OAuthSuccessHandler(UserService userService) {
        this.userService = userService;
//        setDefaultTargetUrl("/");
    }



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken){

            UserRegistrationDto userRegistrationDto = getUserInfo(oAuth2AuthenticationToken);




            String userEmail = oAuth2AuthenticationToken
                    .getPrincipal()
                    .getAttribute("email");


            this.userService.createUserIfNotExist(userEmail);
            this.userService.login(userEmail);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private UserRegistrationDto getUserInfo(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        String clientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        Map<String, Object> attributes = oAuth2AuthenticationToken
                .getPrincipal()
                .getAttributes();
        switch (clientRegistrationId){
            case "google":
                userRegistrationDto
                        .setEmail(attributes.get("email").toString())
                        .setFirstName(attributes.get("given_name").toString())
                        .setLastName(attributes.get("family_name").toString());
                break;
            case "github":
                String name = attributes.get("name").toString();
                String[] names = name.split("\\s+");
                userRegistrationDto
                        .setEmail(attributes.get("email").toString())
                        .setFirstName(names[0])
                        .setLastName(names.length > 1 ? names[1]: "");
                break;
            default:
                //error
        }

        return userRegistrationDto;
    }
}
