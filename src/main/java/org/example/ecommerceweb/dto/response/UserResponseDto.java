package org.example.ecommerceweb.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String userName;
    private String emailAddress;
    private String firstName;
    private String lastName;

}
