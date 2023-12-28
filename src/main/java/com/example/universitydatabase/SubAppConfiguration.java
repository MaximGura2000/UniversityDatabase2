package com.example.universitydatabase;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class SubAppConfiguration {

  public static final String SUB_APP_STATUS = "subAppStatus";
  @Value("${subAppDataStore.mongo.primary}")
  private String mongoPrimary;
  @Value("${subAppDataStore.mongo.database}")
  private String databaseName;

  @Bean
  public void mongoDatabase() {
    try (MongoClient mongoClient = MongoClients.create(mongoPrimary)) {
      MongoDatabase mongoUniversityDatabase = mongoClient.getDatabase(databaseName);

      //Create database and SubAppStatus collection
      if (!mongoUniversityDatabase.listCollectionNames().into(new ArrayList<>()).contains(SUB_APP_STATUS)) {
        mongoUniversityDatabase.createCollection(SUB_APP_STATUS);
      }
    }
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    MongoClient mongoClient = MongoClients.create(mongoPrimary);
    return new MongoTemplate(mongoClient, databaseName);
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    return objectMapper;
  }
}
