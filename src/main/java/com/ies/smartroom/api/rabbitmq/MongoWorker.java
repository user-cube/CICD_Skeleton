package com.ies.smartroom.api.rabbitmq;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.ConfigurableTypeInformationMapper;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class MongoWorker {

    @Value("${spring.data.mongodb.uri}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    private MongoCollection<Document> temperature;
    private MongoCollection<Document> co2;
    private MongoCollection<Document> access;
    private MongoCollection<Document> humidity;

    public MongoWorker(){
        MongoClientURI uri = new MongoClientURI("mongodb://smart_user:W2Nx6xmAtnzK2Zxa@cluster0-shard-00-00-wq6zj.mongodb.net:27017,cluster0-shard-00-01-wq6zj.mongodb.net:27017,cluster0-shard-00-02-wq6zj.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("smartroom");
        temperature = database.getCollection("temperature");
        co2 = database.getCollection("co2");
        access = database.getCollection("access");
        humidity = database.getCollection("humidity");
    }

    void insertTemperature(Document json){
        temperature.insertOne(json);
    }

    void insertCo2(Document json){
        co2.insertOne(json);
    }

    void insertAccess(Document json){
        access.insertOne(json);
    }

    void insertHumidity(Document json){
        humidity.insertOne(json);
    }

}

