package kz.example.config.com;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CustomResponse {
    public CustomResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    public CustomResponse(String message) {
        super();
        this.message = message;
    }

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;

}