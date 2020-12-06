package me.devtools4.saga4j.repository;

import java.util.UUID;
import me.devtools4.saga4j.model.SagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaRepository extends JpaRepository<SagaEntity, Long> {
  SagaEntity findDistinctByCorrelationId(UUID correlationId);
}