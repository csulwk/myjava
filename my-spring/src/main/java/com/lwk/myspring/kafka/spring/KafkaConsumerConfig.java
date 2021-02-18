package com.lwk.myspring.kafka.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring整合Kafka之消费者
 * 参考：
 * https://evernote.blog.csdn.net/article/details/113004582
 * https://blog.csdn.net/ghdqfhw/article/details/113340501
 * @author kai
 * @date 2021-02-18 13:41
 */
@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumerConfig {

    /**
     * 配置Consumer
     * @return ConsumerConfig
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constant.BROKERS);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Constant.MY_GROUP_1);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
        return props;
    }

    /**
     * 构建ConsumerFactory
     * @return ConsumerFactory
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), deserializer);
    }

    /**
     * 构建KafkaListenerContainerFactory
     * @return KafkaListenerContainerFactory
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MyMessage>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MyMessage> factory = new  ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 根据实际分区数设置并发量，必须小于等于分区数，否则会有线程一直处于空闲状态。
        factory.setConcurrency(2);
        factory.getContainerProperties().setPollTimeout(1500);
        factory.setBatchListener(false);
        return factory;
    }

    @Bean("batchFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MyMessage>> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MyMessage> factory = new  ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 根据实际分区数设置并发量，必须小于等于分区数，否则会有线程一直处于空闲状态。
        factory.setConcurrency(2);
        factory.getContainerProperties().setPollTimeout(1500);
        // 设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        return factory;
    }
    /**
     * 自定义消费者
     * @return
     */
    @Bean
    public MyConsumer myConsumer(){
        return new MyConsumer();
    }
}
