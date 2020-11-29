package me.devtools4.saga4j.orchestrator;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import me.devtools4.saga4j.api.Saga;
import me.devtools4.saga4j.api.SimpleSaga;
import me.devtools4.saga4j.api.SimpleStep;
import me.devtools4.saga4j.api.Status;
import me.devtools4.saga4j.api.Step;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.model.StepEntity;
import me.devtools4.saga4j.repository.SagaRepository;

public class Ops {
  private Ops() {}

  public static Saga persisted(Saga saga, SagaRepository repository) {
    return new PersistedSaga(saga.getCorrelationId(),
        saga.getName(),
        saga.getSteps()
            .stream()
            .map(x -> persisted(x, repository))
            .collect(Collectors.toList()),
        repository);
  }

  public static Step persisted(Step step, SagaRepository repository) {
    return new PersistedStep(step.getName(),
        step.getExecutor(),
        step.getTransformer(),
        step.getFallback(),
        repository);
  }

  public static SagaEntity toModel(Saga saga, Status status, LocalDateTime dateTime) {
    return SagaEntity.builder()
        .status(status)
        .dateTime(dateTime)
        .build();
  }

  public static StepEntity toModel(Step step) {
    return StepEntity.builder()
        .build();
  }

  public static Saga toApi(SagaEntity e) {
    return SimpleSaga.builder()
        .build();
  }

  public static Step toApi(StepEntity e) {
    return SimpleStep.builder()
        .build();
  }
}