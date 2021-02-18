package com.lwk.myspring.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产者
 * @author kai
 * @date 2021-02-18 10:48
 */
@Slf4j
public class ProducerSample {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9091,192.168.99.100:9092,192.168.99.100:9093");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        String topicName = "producer_sample";
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        int id = 0;
        while (true) {
            String key = Integer.toString(id);
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, "spring");
            producer.send(record);
            log.info("PRODUCER: {}", record);
            id++;
            Thread.sleep(3000);
        }
    }
}
