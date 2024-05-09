package org.example.ecommerceweb.controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.User;
import org.example.ecommerceweb.dto.JwtAuthDto;
import org.example.ecommerceweb.dto.req.LoginReqDto;
import org.example.ecommerceweb.dto.req.SignupReqDto;
import org.example.ecommerceweb.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    // EXAMPLE CONSTRUCTOR-BASED INJECTION
    private final AuthService authService;

//    private final UserService userService;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDto loginDto) {
          try {
              JwtAuthDto jwtAuthDto = authService.login(loginDto);
              return new ResponseEntity<>(jwtAuthDto,HttpStatus.OK);
//              User user = authService.loginReturnUser(loginDto);
//              return new ResponseEntity<>(user,HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
          }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupReqDto signupDto) {
        try {
//            JwtAuthDto jwtAuthDto = authService.signUp(signupDto);
//            return new ResponseEntity<>(jwtAuthDto,HttpStatus.OK);
            User user = authService.SignupReturnUser(signupDto);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("/profile")
    public ResponseEntity<?> getUSerProfileHandler(
            @RequestHeader(value = "Authorization") String jwt
    )throws Exception{
        User user = authService.findUserByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        try {

            return new ResponseEntity<>( authService.forgotPassword(email),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/user")
//    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
//        return Collections.singletonMap("name", principal.getAttribute("name"));
//    }
//
//    @GetMapping("/signingoogle")
//    public ResponseEntity<?> login1(@AuthenticationPrincipal OAuth2User principal){
//        ResponseData responseData = new ResponseData();
//
//        try {
//            JWTAuthDto jwtAuthDto = authService.loginWithGoogle(principal);
//            responseData.setData(jwtAuthDto);
//        }catch (Exception e){
//            responseData.setStatus(400);
//            responseData.setDesc(e.getMessage());
//            responseData.setSuccess(false);
//            responseData.setData(null);
//        }
//        return new ResponseEntity<>(responseData,HttpStatus.OK);
//    }

    @PostMapping("/refreshtoken")
    ResponseEntity<?> refreshToken(@RequestParam String token){
        try {
            JwtAuthDto jwtAuthDto = authService.refreshToken(token);
            return new ResponseEntity<>(jwtAuthDto,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }









}