package com.lwk.myspring.kafka.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring整合Kafka之生产者
 * 参考：
 * https://evernote.blog.csdn.net/article/details/113004582
 * https://blog.csdn.net/ghdqfhw/article/details/113340501
 * @author kai
 * @date 2021-02-18 13:41
 */
@Configuration
@EnableKafka
@Slf4j
public class KafkaProducerConfig {

    /**
     * 配置Producer
     * @return ProducerConfig
     */
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constant.BROKERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
    /**
     * 构建producerFactory
     * @return ProducerFactory
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * 构建kafkaTemplate
     * @return KafkaTemplate
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    };
}
