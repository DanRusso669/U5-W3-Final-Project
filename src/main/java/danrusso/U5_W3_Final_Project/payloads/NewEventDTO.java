package danrusso.U5_W3_Final_Project.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventDTO(
        @NotEmpty(message = "Title is mandatory.")
        @Size(min = 2, max = 40, message = "Name length must be between 2 and 40.")
        String title,
        @NotEmpty(message = "Description is mandatory.")
        @Size(min = 10, max = 300, message = "Name length must be between 10 and 300.")
        String description,
        @NotNull
        LocalDate date,
        @NotEmpty(message = "Location is mandatory.")
        @Size(min = 5, max = 30, message = "Name length must be between 5 and 30.")
        String location,
        @Max(1000)
        int maxGuests
) {
}
