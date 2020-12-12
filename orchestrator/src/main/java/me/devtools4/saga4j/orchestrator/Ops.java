package me.devtools4.saga4j.orchestrator;

import java.util.stream.Collectors;
import me.devtools4.saga4j.api.Saga;
import me.devtools4.saga4j.api.SimpleSaga;
import me.devtools4.saga4j.api.SimpleStep;
import me.devtools4.saga4j.api.Step;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.model.StepEntity;
import me.devtools4.saga4j.repository.IdGenerator;
import me.devtools4.saga4j.repository.SagaRepository;

public class Ops {
  private final IdGenerator<Long> idGenerator;
  private final SagaRepository repository;

  public Ops(IdGenerator<Long> idGenerator, SagaRepository repository) {
    this.idGenerator = idGenerator;
    this.repository = repository;
  }

  public Saga persisted(Saga saga) {
    return new PersistedSaga(saga.getCorrelationId(),
        saga.getName(),
        saga.getSteps()
            .stream()
            .map(this::persisted)
            .collect(Collectors.toList()),
        idGenerator,
        repository);
  }

  public Step persisted(Step step) {
    return new PersistedStep(step.getName(),
        step.getExecutor(),
        step.getTransformer(),
        step.getFallback(),
        repository);
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