package me.devtools4.saga4j.orchestrator;

import static me.devtools4.saga4j.orchestrator.Ops.persisted;

import me.devtools4.saga4j.api.Input;
import me.devtools4.saga4j.api.Output;
import me.devtools4.saga4j.api.Saga;
import me.devtools4.saga4j.repository.SagaRepository;

public class SagaOrchestrator {

  private final SagaRepository repository;

  public SagaOrchestrator(SagaRepository repository) {
    this.repository = repository;
  }

  public Output execute(Saga saga, Input input) {
    return persisted(saga, repository)
        .apply(input);
  }
}