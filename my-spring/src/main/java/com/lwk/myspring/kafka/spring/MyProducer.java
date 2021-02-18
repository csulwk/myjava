package com.lwk.myspring.kafka.spring;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kai
 * @date 2021-02-18 14:31
 */
@RestController
@Slf4j
public class MyProducer {

    @Autowired
    public KafkaTemplate<String, Object> kafkaTemplate;
    private int num = 1;

    /**
     * 发送消息
     */
    @GetMapping("/producer/1/k/{key}/v/{val}")
    public String sendProducer1(@PathVariable String key, @PathVariable String val) {
        MyMessage msg = new MyMessage(num, val);
        kafkaTemplate.send(Constant.MY_TOPIC_1, key, msg);
        log.info("PRODUCER_1: {}", msg);

        num++;
        return JSONObject.toJSONString(msg);
    }

    /**
     * 发送消息
     */
    @GetMapping("/producer/2/cnt/{cnt}")
    public String sendProducer2(@PathVariable int cnt) {
        for (int i = 1; i <= cnt; i++) {
            MyMessage msg = new MyMessage(i, "val-" + i);
            kafkaTemplate.send(Constant.MY_TOPIC_2, "key-" + i, msg);
            log.info("PRODUCER_2: {}", msg);
        }
        return JSONObject.toJSONString("PRODUCER_2");
    }
}
