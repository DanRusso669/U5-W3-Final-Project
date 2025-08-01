package danrusso.U5_W3_Final_Project.controllers;

import danrusso.U5_W3_Final_Project.entities.Event;
import danrusso.U5_W3_Final_Project.entities.User;
import danrusso.U5_W3_Final_Project.exceptions.ValidationException;
import danrusso.U5_W3_Final_Project.payloads.NewEventDTO;
import danrusso.U5_W3_Final_Project.payloads.NewEventRespDTO;
import danrusso.U5_W3_Final_Project.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public Page<Event> findAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "location") String sortBy) {
        return this.eventService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PLANNER')")
    public NewEventRespDTO createEvent(@RequestBody @Validated NewEventDTO payload, BindingResult validationResults, @AuthenticationPrincipal User currentAuthUser) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Event newEvent = this.eventService.save(payload, currentAuthUser);
            return new NewEventRespDTO(newEvent.getId());
        }
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('PLANNER')")
    public NewEventRespDTO updateEvent(@RequestBody @Validated NewEventDTO payload, BindingResult validationResults, @AuthenticationPrincipal User currentAuthUser, @PathVariable UUID eventId) {
        if (validationResults.hasErrors()) {
            throw new ValidationException(validationResults.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Event updatedEvent = this.eventService.findByIdAndUpdate(eventId, payload, currentAuthUser);
            return new NewEventRespDTO(updatedEvent.getId());
        }
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('PLANNER')")
    public void deleteEvent(@PathVariable UUID eventId, @AuthenticationPrincipal User currentAuthUser) {
        this.eventService.findByIdAndDelete(eventId, currentAuthUser);
    }
}
