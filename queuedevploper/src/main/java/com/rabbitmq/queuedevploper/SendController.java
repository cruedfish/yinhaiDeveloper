package com.rabbitmq.queuedevploper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.RequestWrapper;

@Controller
public class SendController {
    @Autowired
    RabbitProducer rabbitProducer;

    @Autowired
    KafkaProducer kafkaProducer;
    @RequestMapping(value = "/test/send")
    public void testSend(){
        rabbitProducer.send();
    }
    @RequestMapping(value = "/test/send2")
    public void testSend2(){
        rabbitProducer.sendFanout();
    }

    @RequestMapping(value = "/test/send3")
    @Transactional
    public void testSend3() throws Exception{
        kafkaProducer.send();
    }

    @RequestMapping(value = "/test/send4")
    @Transactional
    public void testSend4()throws Exception{
        kafkaProducer.send();
    }
}
