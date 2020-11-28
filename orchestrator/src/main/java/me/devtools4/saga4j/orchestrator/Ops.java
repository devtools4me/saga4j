package me.devtools4.saga4j.orchestrator;

import java.time.LocalDateTime;
import me.devtools4.saga4j.api.Saga;
import me.devtools4.saga4j.api.SimpleSaga;
import me.devtools4.saga4j.api.SimpleStep;
import me.devtools4.saga4j.api.Status;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.model.StepEntity;
import me.devtools4.saga4j.repository.SagaRepository;

public class Ops {
  private Ops() {}

  public static Saga persisted(Saga saga, SagaRepository repository) {
    return new PersistedSaga(saga, repository);
  }

  public static SagaEntity toModel(Saga other, Status status, LocalDateTime dateTime) {
    return SagaEntity.builder()
        .status(status)
        .dateTime(dateTime)
        .build();
  }

  public static StepEntity toModel(SimpleStep other) {
    return StepEntity.builder()
        .build();
  }

  public static SimpleSaga toApi(SagaEntity e) {
    return SimpleSaga.builder()
        .build();
  }

  public static SimpleStep toApi(StepEntity e) {
    return SimpleStep.builder()
        .build();
  }
}