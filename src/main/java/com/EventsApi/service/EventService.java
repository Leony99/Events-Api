package com.EventsApi.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.EventsApi.model.event.Event;
import com.EventsApi.model.event.EventRequestDTO;
import com.EventsApi.model.event.EventResponseDTO;
import com.EventsApi.repositories.EventRepository;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class EventService {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressService addressService;

    public Event createEvent(EventRequestDTO data) {
        String imageUrl = null;
        if (data.image() != null) {
            imageUrl = this.uploadImage(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setDate(new Date(data.date()));
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setImgUrl(imageUrl);
        newEvent.setRemote(data.remote());

        eventRepository.save(newEvent);
        
        if (!data.remote()) {
            addressService.createAddress(data, newEvent);
        }

        return newEvent;
    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = eventRepository.findUpcomingEvents(new Date(), pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : " ",
                event.getAddress() != null ? event.getAddress().getUf() : " ",
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl()
        )).stream().toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String title, String city, String uf, Date startDate, Date endDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = eventRepository.findFilteredEvents(new Date(), title, city, uf, startDate, endDate, pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity() : " ",
                event.getAddress() != null ? event.getAddress().getUf() : " ",
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl()
        )).stream().toList();
    }

    private String uploadImage(MultipartFile multipartFile) {
        String imgName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            File file = this.convertMultiPartToFile(multipartFile);
            s3Client.putObject(bucketName, imgName, file);
            file.delete();
            return s3Client.getUrl(bucketName, imgName).toString();
        } catch (Exception e) {
            System.out.println("Error while uploading image to S3: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }
}
