package danrusso.U5_W3_Final_Project.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
        @Email(message = "Invalid email format.")
        @NotEmpty(message = "Email is mandatory.")
        String email,
        // @NotEmpty(message = "Password is mandatory.")
        String password) {
}
