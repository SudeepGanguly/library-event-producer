package com.learnkafka.libraryEventProducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.libraryEventProducer.domain.LibraryEvent;

import java.util.concurrent.ExecutionException;

public interface LibraryEventServices {
    public LibraryEvent saveBook(LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException;
    public LibraryEvent udpateBook(LibraryEvent libraryEvent) throws JsonProcessingException;
}
