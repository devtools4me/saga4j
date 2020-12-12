package me.devtools4.saga4j.orchestrator;

import static me.devtools4.saga4j.api.Status.STARTED;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.saga4j.api.Input;
import me.devtools4.saga4j.api.Output;
import me.devtools4.saga4j.api.Recovery;
import me.devtools4.saga4j.api.SimpleSaga;
import me.devtools4.saga4j.api.Status;
import me.devtools4.saga4j.api.Step;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.model.StepEntity;
import me.devtools4.saga4j.repository.IdGenerator;
import me.devtools4.saga4j.repository.SagaRepository;
import org.slf4j.Logger;

@Slf4j
public class PersistedSaga extends SimpleSaga {
  private final IdGenerator<Long> idGenerator;
  private final SagaRepository repository;

  public PersistedSaga(UUID correlationId, String name, List<Step> steps,
      IdGenerator<Long> idGenerator,
      SagaRepository repository) {
    super(correlationId, name, steps);
    this.idGenerator = idGenerator;
    this.repository = repository;
  }

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public Output apply(Input input) {

    LocalDateTime now = LocalDateTime.now();
    SagaEntity entity = SagaEntity.builder()
            .id(idGenerator.get())
            .correlationId(getCorrelationId())
            .name(getName())
            .recovery(Recovery.MANUAL)
            .status(STARTED)
            .input(input.getInput())
            .dateTime(now)
            .build();
    entity.withSteps(getSteps()
            .stream()
            .map(x -> toModel(x, entity, input, now))
                .collect(Collectors.toList()));
    repository.save(entity);

    Output output = super.apply(input);

    repository.save(entity.withStatus(Status.PROCESSED, LocalDateTime.now()));

    return output;
  }

  public StepEntity toModel(Step step, SagaEntity saga, Input input, LocalDateTime dateTime) {
    return StepEntity.builder()
        .id(idGenerator.get())
        .saga(saga)
        .name(step.getName())
        .status(STARTED)
        .input(input.getInput())
        .dateTime(dateTime)
        .build();
  }
}
