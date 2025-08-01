package danrusso.U5_W3_Final_Project.repositories;

import danrusso.U5_W3_Final_Project.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByDateAndLocation(LocalDate date, String location);
}
