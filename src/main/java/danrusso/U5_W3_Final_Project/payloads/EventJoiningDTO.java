package danrusso.U5_W3_Final_Project.payloads;

import danrusso.U5_W3_Final_Project.entities.User;

import java.time.LocalDate;
import java.util.List;

public record EventJoiningDTO(String title, LocalDate date, List<User> participants) {
}
