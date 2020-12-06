package me.devtools4.saga4j.repository;

import javax.persistence.EntityManager;
import me.devtools4.saga4j.repository.impl.EntityManagerIdGenerator;
import org.springframework.context.annotation.Bean;

public class TestConfig {
  @Bean
  public IdGenerator<Long> sagaIdGenerator(EntityManager em) {
    return new EntityManagerIdGenerator(em, "SELECT NEXT VALUE FOR SAGA_ID_SEQ");
  }
}