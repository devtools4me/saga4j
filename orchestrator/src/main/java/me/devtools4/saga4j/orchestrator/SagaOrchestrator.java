package me.devtools4.saga4j.orchestrator;

import static me.devtools4.saga4j.orchestrator.Ops.toApi;

import me.devtools4.saga4j.api.Input;
import me.devtools4.saga4j.api.Output;
import me.devtools4.saga4j.api.Status;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.repository.SagaRepository;

public class SagaOrchestrator {

  private final SagaRepository repository;

  public SagaOrchestrator(SagaRepository repository) {
    this.repository = repository;
  }

  public Output execute(SagaEntity saga) {
    return toApi(saga)
        .execute(Input.builder()
        .input(saga.getInput())
        .build());
  }
}