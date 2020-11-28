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
import me.devtools4.saga4j.api.Step;
import me.devtools4.saga4j.api.Transformer;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.model.StepEntity;
import me.devtools4.saga4j.repository.SagaRepository;

@Slf4j
public class PersistedStep implements Step {

  private final Step step;
  private final SagaRepository repository;

  public PersistedStep(Step step, SagaRepository repository) {
    this.step = step;
    this.repository = repository;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public Executor getExecutor() {
    return null;
  }

  @Override
  public Transformer getTransformer() {
    return null;
  }

  @Override
  public Fallback getFallback() {
    return null;
  }

  @Override
  public Output apply(Input input) {
    log.info("input={}", input);

    UUID correlationId = UUID.fromString(input.getInput().get("correlationId"));
    log.info("correlationId={}", correlationId);

    SagaEntity entity = repository.findDistinctByCorrelationId(correlationId);

    StepEntity stepEntity = entity.getSteps().stream()
        .filter(x -> getName().equals(x.getName()))
        .findFirst().get();

    LocalDateTime start = LocalDateTime.now();
    stepEntity.withStatus(STARTED, start);

    repository.save(entity.withStatus(PROCESSING, start));

    Output output = step.apply(input);

    LocalDateTime stop = LocalDateTime.now();
    stepEntity.withStatus(PROCESSED, stop);

    repository.save(entity.withStatus(PROCESSING, stop));

    log.info("Processed, correlationId={}", correlationId);

    return output;
  }
}
