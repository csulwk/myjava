package com.lwk.myspring.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者
 * @author kai
 * @date 2021-02-18 11:40
 */
@Slf4j
public class ConsumerSample {

    public static void main(String[] args) throws InterruptedException {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9091,192.168.99.100:9092,192.168.99.100:9093");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");

        String topicName = "producer_sample";
        Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            log.info("CONSUMER...");
            for (ConsumerRecord<String, String> record : records) {
                log.info("CONSUMER: {}", record);
            }
            Thread.sleep(6000);
        }

    }
}
