package me.devtools4.saga4j.orchestrator;

import me.devtools4.saga4j.api.Input;
import me.devtools4.saga4j.api.Output;
import me.devtools4.saga4j.api.Saga;
import me.devtools4.saga4j.repository.IdGenerator;
import me.devtools4.saga4j.repository.SagaRepository;

public class SagaOrchestrator {

  private final IdGenerator<Long> idGenerator;
  private final SagaRepository repository;
  private final Ops ops;

  public SagaOrchestrator(IdGenerator<Long> idGenerator, SagaRepository repository) {
    this.idGenerator = idGenerator;
    this.repository = repository;
    this.ops = new Ops(idGenerator, repository);
  }

  public Output execute(Saga saga, Input input) {
    return ops.persisted(saga)
        .apply(input);
  }
}