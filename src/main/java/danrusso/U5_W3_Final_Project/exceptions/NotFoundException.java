package danrusso.U5_W3_Final_Project.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Record with id " + id + " not found.");
    }
}
