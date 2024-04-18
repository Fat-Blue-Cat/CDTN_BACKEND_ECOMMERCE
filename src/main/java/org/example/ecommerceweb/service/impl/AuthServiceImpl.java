package org.example.ecommerceweb.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.JwtAuthDto;
import org.example.ecommerceweb.dto.req.LoginReqDto;
import org.example.ecommerceweb.dto.req.SignupReqDto;
import org.example.ecommerceweb.repository.CartRepository;
import org.example.ecommerceweb.repository.UserRepository;
import org.example.ecommerceweb.service.AuthService;
import org.example.ecommerceweb.service.CartService;
import org.example.ecommerceweb.service.CustomUserDetailsService;
import org.example.ecommerceweb.util.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CartRepository cartRepository;
    private final CartService cartService;

    @Override
    public JwtAuthDto login(LoginReqDto loginReqDto) {
        Authentication authentication = authenticate(loginReqDto.getUsernameOrEmail(),loginReqDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(),authentication);
        JwtAuthDto jwtAuthDto = new JwtAuthDto();
        jwtAuthDto.setAccessToken(token);
        jwtAuthDto.setRefreshToken(refreshToken);
        return jwtAuthDto;
    }

    @Override
    public JwtAuthDto signUp(SignupReqDto signupReqDto) throws Exception {

        boolean isExistMail = userRepository.existsByEmailAddress(signupReqDto.getEmail());
        boolean isExistUserName = userRepository.existsByUserName(signupReqDto.getUsername());

        if(isExistMail || isExistUserName) throw new Exception("Email or UserName is Already used with another account");
        User user =User.builder().userName(signupReqDto.getUsername())
                .emailAddress(signupReqDto.getEmail())
                .firstName(signupReqDto.getFirstName())
                .lastName(signupReqDto.getLastName())
                .password(passwordEncoder.encode(signupReqDto.getPassword()))
                .createdAt(new Date())
                .build();

        User savedUSer  = userRepository.save(user);
        cartService.createCart(savedUSer);
        Authentication authentication = authenticate(savedUSer.getUserName(),signupReqDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(),authentication);
        JwtAuthDto jwtAuthDto = new JwtAuthDto();
        jwtAuthDto.setAccessToken(token);
        jwtAuthDto.setRefreshToken(refreshToken);

        return jwtAuthDto;
    }

    @Override
    public JwtAuthDto loginWithGoogle(OAuth2User principal) {

        // Extract information from OAuth2 authentication token
//        String email = (String) principal.getAttributes().get("email");
//        String googleId = (String) principal.getAttributes().get("sub");
//        String name = (String) principal.getAttributes().get("name");
//
//
//        Optional<Users> userOptional = userRepository.findByUserNameOrEmailAddress(email,email);
//        Users user;
//        if (userOptional.isPresent()) {
//            user = userOptional.get();
//
//        } else {
//            user = new Users();
//            user.setEmail(email);
//            user.setGoogleId(googleId);
//            user.setName(name);
//            user.setRoleName("USER");
//            user.setUserName(email);
//            user.setCreateAt(new Date());
//            user.setCheckinCode(null);
//
//
//            userRepository.save(user);
//        }
//
//
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//        String token = jwtTokenProvider.generateToken(authentication);
//        String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(),authentication);
//        JWTAuthDto jwtAuthDto = new JWTAuthDto();
//        jwtAuthDto.setAccessToken(token);
//        jwtAuthDto.setRefreshToken(refreshToken);
//
//        return jwtAuthDto;
        return new JwtAuthDto();
    }


    public JwtAuthDto refreshToken(String refreshToken){
        JwtAuthDto jwtAuthDto = new JwtAuthDto();
        String userName = jwtTokenProvider.getUsername(refreshToken);
//        Optional<Users> user = userRepository.findByUserNameOrEmail(userName,userName);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        if(jwtTokenProvider.validateToken(refreshToken)  && authentication !=null){
            String jwt  = jwtTokenProvider.generateToken(authentication);
            jwtAuthDto.setRefreshToken(refreshToken);
            jwtAuthDto.setAccessToken(jwt);

        }
        return jwtAuthDto;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if(userDetails == null){

            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncoder.matches(password,userDetails.getPassword())){

            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }




}