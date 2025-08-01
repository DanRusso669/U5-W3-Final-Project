package danrusso.U5_W3_Final_Project.services;

import danrusso.U5_W3_Final_Project.entities.Event;
import danrusso.U5_W3_Final_Project.entities.User;
import danrusso.U5_W3_Final_Project.exceptions.NotFoundException;
import danrusso.U5_W3_Final_Project.exceptions.UnauthorizedException;
import danrusso.U5_W3_Final_Project.payloads.NewEventDTO;
import danrusso.U5_W3_Final_Project.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event save(NewEventDTO payload, User planner) {

        Event newEvent = new Event(payload.title(), payload.description(), payload.date(), payload.location(), payload.maxGuests(), planner);
        this.eventRepository.save(newEvent);

        return newEvent;
    }

    public Page<Event> findAll(int pageNum, int pageSize, String sortBy) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortBy));
        return this.eventRepository.findAll(pageable);
    }

    public Event findById(UUID eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
    }

    public void checkPlanner(UUID eventPlannerId, UUID plannerId) {
        if (!eventPlannerId.equals(plannerId))
            throw new UnauthorizedException("You can't modified someone else's event.");
    }

    public Event findByIdAndUpdate(UUID eventId, NewEventDTO payload, User currentAuthUser) {
        Event found = this.findById(eventId);
        this.checkPlanner(found.getPlanner().getId(), currentAuthUser.getId());
//        if (found.getPlanner().getId() != plannerId)
//            throw new UnauthorizedException("You can't modified someone else's event.");

        found.setTitle(payload.title());
        found.setDescription(payload.description());
        found.setDate(payload.date());
        found.setLocation(payload.location());
        found.setMaxGuests(payload.maxGuests());

        return this.eventRepository.save(found);
    }

    public void findByIdAndDelete(UUID eventId, User currentAuthUser) {
        Event found = this.findById(eventId);
        this.checkPlanner(found.getPlanner().getId(), currentAuthUser.getId());
        this.eventRepository.delete(found);
    }
}
