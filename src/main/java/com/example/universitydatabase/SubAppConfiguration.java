package com.example.universitydatabase;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubAppConfiguration {

  @Value("${subAppDataStore.mongo.primary}")
  private String mongoPrimary;
  @Value("${subAppDataStore.mongo.database}")
  private String databaseName;

  @Bean
  public void mongoDatabase() {
    try (MongoClient mongoClient = MongoClients.create(mongoPrimary)) {
      MongoDatabase mongoUniversityDatabase = mongoClient.getDatabase(databaseName);

      //Create database and SubAppStatus collection
      mongoUniversityDatabase.createCollection("subAppStatus");
    }
  }
}
