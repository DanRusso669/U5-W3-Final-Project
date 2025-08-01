package danrusso.U5_W3_Final_Project.payloads;

import jakarta.validation.constraints.*;

public record NewUserDTO(
        @NotEmpty(message = "Name is mandatory.")
        @Size(min = 2, max = 20, message = "Name length must be between 2 and 20.")
        String name,
        @NotEmpty(message = "Surname is mandatory.")
        @Size(min = 2, max = 20, message = "Surname length must be between 2 and 20.")
        String surname,
        @Email(message = "Invalid email format.")
        @NotEmpty(message = "Email is mandatory.")
        String email,
        @Min(4)
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,}$", message = "Password must be atleast 8 characters, atleast one upperCase, one lowerCase letter, one digit e one special character.")
        String password,
        String role
) {
}
