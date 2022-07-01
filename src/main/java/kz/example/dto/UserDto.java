package kz.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDto {
    @NotBlank(message = "Username cannot be blank")
    private String name;

    @NotBlank(message = "Password cannot be blank")
    private String password;

}
