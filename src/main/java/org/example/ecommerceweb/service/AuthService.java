package org.example.ecommerceweb.service;



import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.JwtAuthDto;
import org.example.ecommerceweb.dto.req.LoginReqDto;
import org.example.ecommerceweb.dto.req.SignupReqDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {
    JwtAuthDto login(LoginReqDto loginDto);
    JwtAuthDto signUp(SignupReqDto signupReqDto) throws Exception;

    JwtAuthDto loginWithGoogle(OAuth2User principal);

    JwtAuthDto refreshToken(String refreshToken);

    User loginReturnUser(LoginReqDto loginDto);
    User SignupReturnUser(SignupReqDto signupReqDto);

    User updateProfile(User currentUser,User user);

    User findUserByJwt(String jwt);

    String forgotPassword(String email);
}