package com.learnkafka.libraryEventProducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.libraryEventProducer.domain.LibraryEvent;
import com.learnkafka.libraryEventProducer.producers.LibraryEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryEventServicesImpl implements LibraryEventServices {
    private final LibraryEventProducer libraryEventProducer;

    @Override
    public LibraryEvent saveBook(LibraryEvent libraryEvent) throws JsonProcessingException,
                                                    ExecutionException, InterruptedException {
        log.info("Before calling Producer");
        //invoke Kafka Producer
        //libraryEventProducer.sendLibraryEvent(libraryEvent);
        //SendResult<Integer,String> result = libraryEventProducer.sendLibraryEventSynchronously(libraryEvent);
        libraryEventProducer.sendLibraryEventUsingSend(libraryEvent);
//        log.info("SendResult : {}",result.toString());
        log.info("After calling Producer");
        return libraryEvent;
    }

    @Override
    public LibraryEvent udpateBook(LibraryEvent libraryEvent) throws JsonProcessingException {
        //invoke Kafka Producer
        libraryEventProducer.sendLibraryEvent(libraryEvent);
        return libraryEvent;
    }
}
