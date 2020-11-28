package me.devtools4.saga4j.orchestrator;

import static me.devtools4.saga4j.api.Status.STARTED;
import static me.devtools4.saga4j.orchestrator.Ops.toModel;

import io.vavr.collection.List;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.saga4j.api.Input;
import me.devtools4.saga4j.api.Output;
import me.devtools4.saga4j.api.Saga;
import me.devtools4.saga4j.api.Status;
import me.devtools4.saga4j.api.Step;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.repository.SagaRepository;
import org.slf4j.Logger;

@Slf4j
public class PersistedSaga implements Saga {

  private final Saga saga;
  private final SagaRepository repository;

  public PersistedSaga(Saga saga, SagaRepository repository) {
    this.saga = saga;
    this.repository = repository;
  }

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public String getContext() {
    return saga.getContext();
  }

  @Override
  public String getName() {
    return saga.getName();
  }

  @Override
  public List<Step> getSteps() {
    return saga.getSteps();
  }

  @Override
  public Output apply(Input input) {

    SagaEntity entity = repository.save(toModel(saga, STARTED, LocalDateTime.now()));

    Output output = saga.apply(input);

    repository.save(entity.withStatus(Status.PROCESSED, LocalDateTime.now()));

    return output;
  }
}
