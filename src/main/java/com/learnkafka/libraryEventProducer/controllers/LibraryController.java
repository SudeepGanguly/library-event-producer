package com.learnkafka.libraryEventProducer.controllers;

import com.learnkafka.libraryEventProducer.domain.LibraryEvent;
import com.learnkafka.libraryEventProducer.service.LibraryEventServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryEventServices libraryEventServices;

    @PostMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> saveBook(@RequestBody LibraryEvent libraryEvent){
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEventServices.saveBook(libraryEvent));
    }

    @PutMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> updateBook(@RequestBody LibraryEvent libraryEvent){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(libraryEventServices.udpateBook(libraryEvent));
    }
}
