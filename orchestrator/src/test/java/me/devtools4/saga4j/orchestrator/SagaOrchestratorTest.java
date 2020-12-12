package me.devtools4.saga4j.orchestrator;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.saga4j.api.Executor;
import me.devtools4.saga4j.api.Input;
import me.devtools4.saga4j.api.Output;
import me.devtools4.saga4j.api.SimpleSaga;
import me.devtools4.saga4j.api.SimpleStep;
import me.devtools4.saga4j.api.Status;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.repository.SagaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = SagaRepository.class)
@EntityScan(basePackageClasses = SagaEntity.class)
public class SagaOrchestratorTest {
  @Autowired
  private SagaOrchestrator sut;

  @Autowired
  private SagaRepository repository;

  @Test
  public void test() {
    UUID correlationId = UUID.randomUUID();
    SimpleSaga saga = SimpleSaga.builder()
        .correlationId(correlationId)
        .name("test")
        .steps(Arrays.asList(
            procStep("step1"),
            procStep("step2"),
            procStep("step3")))
        .build();

    sut.execute(saga, Input.builder()
        .input(Collections.emptyMap())
        .build());

    SagaEntity entity = repository.findDistinctByCorrelationId(correlationId);
    assertNotNull(entity);

    log.info("entity={}", entity);
  }

  private final Executor procExec = x -> Output.builder()
      .status(Status.PROCESSED)
      .output(x.getInput())
      .build();

  private SimpleStep procStep(String name) {
    return SimpleStep.builder()
        .name(name)
        .executor(procExec)
        .transformer(x -> x)
        .fallback((t, x) -> procExec.apply(x))
        .build();
  }
}