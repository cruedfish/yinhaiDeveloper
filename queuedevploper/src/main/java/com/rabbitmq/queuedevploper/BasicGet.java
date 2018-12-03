package com.rabbitmq.queuedevploper;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.utils.SerializationUtils;

import java.io.Serializable;

public class BasicGet implements ChannelCallback<User> {
    private String queueName;
    public BasicGet(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public User doInRabbit(Channel channel) throws Exception {
        GetResponse response = channel.basicGet(queueName,false);
        channel.basicQos(1000);
       User user = (User)SerializationUtils.deserialize(response.getBody());
        System.out.print("收到:"+user.getName());
        return null;
    }
}
