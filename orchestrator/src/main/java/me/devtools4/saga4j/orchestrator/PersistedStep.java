package me.devtools4.saga4j.orchestrator;

import static me.devtools4.saga4j.api.Status.PROCESSED;
import static me.devtools4.saga4j.api.Status.PROCESSING;
import static me.devtools4.saga4j.api.Status.STARTED;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.saga4j.api.Executor;
import me.devtools4.saga4j.api.Fallback;
import me.devtools4.saga4j.api.Input;
import me.devtools4.saga4j.api.Output;
import me.devtools4.saga4j.api.SimpleStep;
import me.devtools4.saga4j.api.Transformer;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.model.StepEntity;
import me.devtools4.saga4j.repository.SagaRepository;

@Slf4j
public class PersistedStep extends SimpleStep {
  private final SagaRepository repository;

  public PersistedStep(String name, Executor executor, Transformer transformer, Fallback fallback, SagaRepository repository) {
    super(name, executor, transformer, fallback);
    this.repository = repository;
  }

  @Override
  public Output apply(UUID correlationId, Input input) {
    log.info("[{}] input={}", correlationId, input);

    SagaEntity entity = repository.findDistinctByCorrelationId(correlationId);

    StepEntity stepEntity = entity.getSteps().stream()
        .filter(x -> getName().equals(x.getName()))
        .findFirst().get();

    LocalDateTime start = LocalDateTime.now();
    stepEntity.withStatus(STARTED, start);

    repository.save(entity.withStatus(PROCESSING, start));

    Output output = super.apply(correlationId, input);

    LocalDateTime stop = LocalDateTime.now();
    stepEntity.withStatus(PROCESSED, stop);

    repository.save(entity.withStatus(PROCESSING, stop));

    log.info("Processed, correlationId={}", correlationId);

    return output;
  }
}
