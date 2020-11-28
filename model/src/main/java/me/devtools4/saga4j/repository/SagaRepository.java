package me.devtools4.saga4j.repository;

import java.util.Optional;
import me.devtools4.saga4j.model.SagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaRepository  extends JpaRepository<SagaEntity, Long> {
  Optional<SagaEntity> findDistinctByContextAndName(String context, String name);
}