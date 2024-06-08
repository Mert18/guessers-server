package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.PagingRequest;
import dev.m2t.unlucky.dto.request.CreateEventRequest;
import dev.m2t.unlucky.model.Event;
import dev.m2t.unlucky.model.enums.EventStatusEnum;
import dev.m2t.unlucky.repository.EventPagingRepository;
import dev.m2t.unlucky.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventPagingRepository eventPagingRepository;

    public EventService(EventRepository eventRepository, EventPagingRepository eventPagingRepository) {
        this.eventRepository = eventRepository;
        this.eventPagingRepository = eventPagingRepository;
    }

    public BaseResponse createEvent(CreateEventRequest createEventRequest) {
        Event event = eventRepository.save(new Event(createEventRequest.getName(), createEventRequest.getDate(), createEventRequest.getOptions(), EventStatusEnum.NOT_STARTED));
        return new BaseResponse("Event created successfully.", true, true, event);
    }

    public BaseResponse listEvents(PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize());
        Page<Event> events = eventPagingRepository.findByStatus(EventStatusEnum.NOT_STARTED, pageable);

        return new BaseResponse("Events fetched successfully.", true, false, events);
    }
}
