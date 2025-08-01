package danrusso.U5_W3_Final_Project.controllers;

import danrusso.U5_W3_Final_Project.entities.User;
import danrusso.U5_W3_Final_Project.exceptions.ValidationException;
import danrusso.U5_W3_Final_Project.payloads.LoginDTO;
import danrusso.U5_W3_Final_Project.payloads.LoginRespDTO;
import danrusso.U5_W3_Final_Project.payloads.NewUserDTO;
import danrusso.U5_W3_Final_Project.payloads.NewUserRespDTO;
import danrusso.U5_W3_Final_Project.services.AuthService;
import danrusso.U5_W3_Final_Project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public LoginRespDTO login(@RequestBody @Validated LoginDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            String accessToken = this.authService.checkCredentialsAndGenerateToken(payload);
            return new LoginRespDTO(accessToken);
        }
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserRespDTO saveUser(@RequestBody @Validated NewUserDTO payload, BindingResult validationResults) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            User newUser = this.userService.save(payload);
            return new NewUserRespDTO(newUser.getId());
        }
    }
}
