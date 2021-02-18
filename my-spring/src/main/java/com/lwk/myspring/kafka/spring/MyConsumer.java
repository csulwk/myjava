package com.lwk.myspring.kafka.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author kai
 * @date 2021-02-18 14:31
 */
@Slf4j
public class MyConsumer {

    @KafkaListener(id = "k1", topics = Constant.MY_TOPIC_1)
    public void listen1(ConsumerRecord<?, ?> record) {
        log.info("LISTENER_1...");
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object msg = kafkaMessage.get();
            log.info("LISTENER_1: {}", msg);
        }
    }

    @KafkaListener(id = "k2", topics = Constant.MY_TOPIC_2, containerFactory = "batchFactory")
    public void listen2(List<ConsumerRecord<?, ?>> records) {
        log.info("LISTENER_2...{}", records.size());
        records.forEach(record -> {
            log.info("LISTENER_2: {}", record);
        });
    }
}
