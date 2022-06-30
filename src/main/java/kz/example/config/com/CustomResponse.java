package kz.example.config.com;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CustomResponse {

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;

}