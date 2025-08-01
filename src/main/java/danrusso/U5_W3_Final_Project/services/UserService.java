package danrusso.U5_W3_Final_Project.services;

import danrusso.U5_W3_Final_Project.entities.Roles;
import danrusso.U5_W3_Final_Project.entities.User;
import danrusso.U5_W3_Final_Project.exceptions.BadRequestException;
import danrusso.U5_W3_Final_Project.payloads.NewUserDTO;
import danrusso.U5_W3_Final_Project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public User save(NewUserDTO payload) {

        this.userRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("Email " + payload.email() + "is already in use.");
        });

        User newUser = new User(payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()), Roles.valueOf(payload.role()));
        this.userRepository.save(newUser);
        return newUser;
    }

    public Page<User> findAll(int pageNum, int pageSize, String sortBy) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return this.userRepository.findAll(pageable);
    }
}
