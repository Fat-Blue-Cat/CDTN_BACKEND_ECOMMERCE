package org.example.ecommerceweb.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupReqDto {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}