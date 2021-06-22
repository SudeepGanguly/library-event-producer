package com.learnkafka.libraryEventProducer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.libraryEventProducer.domain.LibraryEvent;
import com.learnkafka.libraryEventProducer.domain.LibraryEventType;
import com.learnkafka.libraryEventProducer.service.LibraryEventServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryEventServices libraryEventServices;

    @PostMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> saveBook(@RequestBody LibraryEvent libraryEvent)
                                    throws JsonProcessingException, ExecutionException, InterruptedException {
        libraryEvent.setEventType(LibraryEventType.NEW);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEventServices.saveBook(libraryEvent));
    }

    @PutMapping("/v1/libraryEvent")
    public ResponseEntity<?> updateBook(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {

        //Validation
        if(Objects.isNull(libraryEvent.getLibraryEventId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body("LibraryEventId Missing");
        }
        libraryEvent.setEventType(LibraryEventType.UPDATE);
        return ResponseEntity.status(HttpStatus.OK)
                            .body(libraryEventServices.udpateBook(libraryEvent));
    }
}
