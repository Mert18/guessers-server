package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateEventRequest;
import dev.m2t.unlucky.model.Event;
import dev.m2t.unlucky.model.enums.EventStatusEnum;
import dev.m2t.unlucky.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public BaseResponse createEvent(CreateEventRequest createEventRequest) {
        Event event = eventRepository.save(new Event(createEventRequest.getName(), createEventRequest.getDate(), createEventRequest.getOptions(), EventStatusEnum.NOT_STARTED));
        return new BaseResponse("Event created successfully", true, true, event);
    }
}
