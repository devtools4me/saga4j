package me.devtools4.saga4j.orchestrator;

import static me.devtools4.saga4j.api.Status.STARTED;
import static me.devtools4.saga4j.orchestrator.Ops.toModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.saga4j.api.Input;
import me.devtools4.saga4j.api.Output;
import me.devtools4.saga4j.api.SimpleSaga;
import me.devtools4.saga4j.api.Status;
import me.devtools4.saga4j.api.Step;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.repository.SagaRepository;
import org.slf4j.Logger;

@Slf4j
public class PersistedSaga extends SimpleSaga {
  private final SagaRepository repository;

  public PersistedSaga(UUID correlationId, String name, List<Step> steps, SagaRepository repository) {
    super(correlationId, name, steps);
    this.repository = repository;
  }

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public Output apply(Input input) {

    SagaEntity entity = repository.save(toModel(this, STARTED, LocalDateTime.now()));

    Output output = super.apply(input);

    repository.save(entity.withStatus(Status.PROCESSED, LocalDateTime.now()));

    return output;
  }
}
