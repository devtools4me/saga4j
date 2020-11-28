package me.devtools4.saga4j.orchestrator;

import me.devtools4.saga4j.api.Saga;
import me.devtools4.saga4j.api.Step;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.model.StepEntity;

public class Ops {
  private Ops() {}

  public static Saga toApi(SagaEntity e) {
    return Saga.builder()
        .build();
  }

  public static Step toApi(StepEntity e) {
    return Step.builder()
        .build();
  }
}