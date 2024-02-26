package com.facenet.mrp.producer_kafka;

import com.facenet.mrp.service.MqqPriceService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Producer {
    private final Logger log = LogManager.getLogger(MqqPriceService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaService kafkaService;

    /**
     * cảnh báo đơn hàng so và fc
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendMessage(){
        Gson gson = new Gson();
        String topic = "mrp-so";
        List<ContextAlert> alertList = kafkaService.getInfoSoAndFc();
        for (ContextAlert element:alertList
             ) {
            String json = gson.toJson(element);
            this.kafkaTemplate.send(topic,json);
        }
    }

    /**
     * cảnh báo nvl,btp trong đơn hàng so và fc
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendMessageForItem(){
        Gson gson = new Gson();
        String topic = "mrp-so-item";
        List<ItemContextAlert> alertList = kafkaService.getInfoItemInSoFc();
        for (ItemContextAlert element:alertList
        ) {
            String json = gson.toJson(element);
            this.kafkaTemplate.send(topic,json);
        }
    }
}
