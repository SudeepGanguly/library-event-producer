package com.learnkafka.libraryEventProducer.service;

import com.learnkafka.libraryEventProducer.domain.LibraryEvent;
import org.springframework.stereotype.Service;

@Service
public class LibraryEventServicesImpl implements LibraryEventServices {
    @Override
    public LibraryEvent saveBook(LibraryEvent libraryEvent) {

        //invoke Kafka Producer
        return null;
    }

    @Override
    public LibraryEvent udpateBook(LibraryEvent libraryEvent) {
        //invoke Kafka Producer

        return null;
    }
}
