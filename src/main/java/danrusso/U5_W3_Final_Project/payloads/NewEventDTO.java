package danrusso.U5_W3_Final_Project.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO(
        @NotEmpty(message = "Title is mandatory.")
        @Size(min = 2, max = 40, message = "Name length must be between 2 and 40.")
        String title,
        @NotEmpty(message = "Description is mandatory.")
        @Size(min = 3, max = 300, message = "Name length must be between 3 and 300.")
        String description,
        @NotNull
        @Future(message = "Date must be in the future.")
        LocalDate date,
        @NotEmpty(message = "Location is mandatory.")
        @Size(min = 3, max = 30, message = "Name length must be between 3 and 30.")
        String location,
        @NotNull
        int maxGuests
) {
}
