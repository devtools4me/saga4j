package me.devtools4.saga4j.api;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import org.junit.Test;

public class SagaTest {

  private final Executor procExec = x -> Output.builder()
      .status(Status.PROCESSED)
      .output(x.getInput())
      .build();

  @Test
  public void testExecute() {
    SimpleSaga saga = SimpleSaga.builder()
        .correlationId(UUID.randomUUID())
        .name("test")
        .steps(Arrays.asList(
            procStep("step1"),
            procStep("step2"),
            procStep("step3")))
        .build();
    Output out = saga.apply(Input.builder()
        .input(Collections.emptyMap())
        .build());
    assertNotNull(out);
  }

  private SimpleStep procStep(String name) {
    return SimpleStep.builder()
        .name(name)
        .executor(procExec)
        .transformer(x -> x)
        .fallback((t, x) -> procExec.apply(x))
        .build();
  }
}