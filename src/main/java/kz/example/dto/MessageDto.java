package kz.example.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MessageDto {

    @NotBlank(message = "Name string cannot be blank")
    private String name;

    @NotBlank(message = "Message string cannot be blank")
    private String message;
}
