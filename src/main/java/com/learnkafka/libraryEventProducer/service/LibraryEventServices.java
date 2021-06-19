package com.learnkafka.libraryEventProducer.service;

import com.learnkafka.libraryEventProducer.domain.LibraryEvent;

public interface LibraryEventServices {
    public LibraryEvent saveBook(LibraryEvent libraryEvent);
    public LibraryEvent udpateBook(LibraryEvent libraryEvent);
}
