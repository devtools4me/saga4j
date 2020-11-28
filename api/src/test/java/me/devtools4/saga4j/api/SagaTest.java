package me.devtools4.saga4j.api;

import io.vavr.collection.List;
import java.util.Collections;
import org.junit.Test;

public class SagaTest {

  private final Executor procExec = x -> Output.builder()
      .status(Status.PROCESSED)
      .output(x.getInput())
      .build();

  @Test
  public void testExecute() {
    SimpleSaga saga = SimpleSaga.builder()
        .context("Test")
        .name("test")
        .steps(List.of(
            procStep("step1"),
            procStep("step2"),
            procStep("step3")))
        .build();
    Output out = saga.apply(Input.builder()
        .input(Collections.emptyMap())
        .build());
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