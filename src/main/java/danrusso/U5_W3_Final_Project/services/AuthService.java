package danrusso.U5_W3_Final_Project.services;

import danrusso.U5_W3_Final_Project.entities.User;
import danrusso.U5_W3_Final_Project.exceptions.UnauthorizedException;
import danrusso.U5_W3_Final_Project.payloads.LoginDTO;
import danrusso.U5_W3_Final_Project.tools.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    public String checkCredentialsAndGenerateToken(LoginDTO payload) {

        User found = this.userService.findByEmail(payload.email());

        if (bcrypt.matches(payload.password(), found.getPassword())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Wrong credentials.");
        }

    }

}
