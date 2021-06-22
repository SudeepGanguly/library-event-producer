package com.learnkafka.libraryEventProducer.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.libraryEventProducer.domain.LibraryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class LibraryEventProducer {
    private final KafkaTemplate<Integer,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "library-events";

    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        ListenableFuture<SendResult<Integer,String>> listenableFuture =
                                                    kafkaTemplate.sendDefault(key,value);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key,value,ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key,value,result);
            }
        });
    }

    public void sendLibraryEventUsingSend(LibraryEvent libraryEvent)
            throws JsonProcessingException {

        SendResult<Integer,String> result = null;
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ProducerRecord<Integer,String> producerRecord = buildProducerRecord(key,value,TOPIC);

        ListenableFuture<SendResult<Integer,String>> listenableFuture =
                kafkaTemplate.send(producerRecord);
    }

    public ProducerRecord<Integer,String> buildProducerRecord(Integer key , String value , String topic){
        List<Header> recordHeaders = List.of(new RecordHeader("event-source","scanner".getBytes()) ,
                                           new RecordHeader("event-author","sudeep".getBytes()));
        return new ProducerRecord<Integer,String>(topic,null,key , value,recordHeaders);
    }

   public SendResult<Integer,String> sendLibraryEventSynchronously(LibraryEvent libraryEvent)
           throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

            SendResult<Integer,String> result = null;
            Integer key = libraryEvent.getLibraryEventId();
            String value = objectMapper.writeValueAsString(libraryEvent);

            ListenableFuture<SendResult<Integer,String>> listenableFuture =
                    kafkaTemplate.sendDefault(key,value);

            //Calling get for synchronous processing
            try {
                 result = listenableFuture.get(1, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                log.error("ExecutionException/InterruptedException: " +
                        "Error sending the message. The exception is {}", e.getMessage());
                throw e;
            }
                return result;
    }


    public void handleFailure(Integer key , String value , Throwable ex){
        log.error("Error Sending the message and the exception is {}",ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error onFailure : {} ",throwable.getMessage());
        }
    }

    public void handleSuccess(Integer key , String value , SendResult<Integer,String> result){
        log.info("Mesage sent Successfully for the key : {} and the value is {} " +
                "and the partition is {}",key,value,result.getRecordMetadata().partition());
    }
}
