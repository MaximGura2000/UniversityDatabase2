package com.example.universitydatabase;

import com.example.universitydatabase.serializers.InstantSerializer;
import com.example.universitydatabase.serializers.ZonedDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.time.Instant;
import java.time.ZonedDateTime;
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
    // Register the JavaTimeModule to handle Java 8 date/time types
    objectMapper.registerModule(new JavaTimeModule());

    SimpleModule module = new SimpleModule()
        .addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer())
        .addSerializer(Instant.class, new InstantSerializer());
    objectMapper.registerModule(module);

    objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    objectMapper.setSerializationInclusion(Include.NON_NULL);

    return objectMapper;
  }
}
