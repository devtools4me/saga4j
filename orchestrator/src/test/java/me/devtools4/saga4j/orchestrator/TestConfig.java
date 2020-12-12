package me.devtools4.saga4j.orchestrator;

import javax.persistence.EntityManager;
import me.devtools4.saga4j.repository.IdGenerator;
import me.devtools4.saga4j.repository.SagaRepository;
import me.devtools4.saga4j.repository.impl.EntityManagerIdGenerator;
import org.springframework.context.annotation.Bean;

public class TestConfig {
  @Bean
  public IdGenerator<Long> sagaIdGenerator(EntityManager em) {
    return new EntityManagerIdGenerator(em, "SELECT NEXT VALUE FOR SAGA_ID_SEQ");
  }

  @Bean
  public SagaOrchestrator SagaOrchestrator(IdGenerator<Long> idGenerator, SagaRepository repository) {
    return new SagaOrchestrator(idGenerator, repository);
  }
}