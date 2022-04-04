package uy.com.sofka.biblioteca.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

  /*
   * Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.
   */
   public @Bean MongoClient mongoClient() {
       return MongoClients.create("mongodb+srv://MyMyself:kito123321@kitorosanocluster.qeuyn.mongodb.net/SOFKA_serverside-biblioteca?retryWrites=true&w=majority");
   }

}
