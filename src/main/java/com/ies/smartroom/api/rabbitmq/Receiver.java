package com.ies.smartroom.api.rabbitmq;

import com.ies.smartroom.api.entities.Credential;
import com.ies.smartroom.api.service.AccessService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.bson.Document;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {

    @Autowired
    private MongoWorker worker;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqp://rniqsthq:oEncQ4eBhISeQn-BAxkyJ7OHyRfgR9BN@gopher.rmq.cloudamqp.com/rniqsthq");
        connectionFactory.setRequestedHeartBeat(30);
        connectionFactory.setConnectionTimeout(30000);
        return connectionFactory;
    }

    @RabbitListener(queues = "gen_data")
    public void processOrder(String data) {

        Document doc = Document.parse(data);
        if (doc.containsKey("temp"))
            worker.insertTemperature(doc);
        else if (doc.containsKey("co2(ppm)"))
            worker.insertCo2(doc);
        else if (doc.containsKey("humidity")) {
            worker.insertHumidity(doc);
        } else if (doc.containsKey("origin")) {
            worker.insertAccess(doc);
        }
    }
}
