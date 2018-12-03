package com.rabbitmq.queuedevploper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.RequestWrapper;

@Controller
public class SendController {
    @Autowired
    RabbitProducer rabbitProducer;
    @RequestMapping(value = "/test/send")
    public void testSend(){
        rabbitProducer.send();
    }
    @RequestMapping(value = "/test/send2")
    public void testSend2(){
        rabbitProducer.sendFanout();
    }
}