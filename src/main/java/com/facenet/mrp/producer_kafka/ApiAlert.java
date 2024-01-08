package com.facenet.mrp.producer_kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/kafka")
public class ApiAlert {
    @Autowired
    private Producer producer;

    @GetMapping("/alert-so")
    public void AlertSo(){
        producer.sendMessage();
    }

    @GetMapping("/alert-so-item")
    public void AlertSoItem(){
        producer.sendMessageForItem();
    }
}
