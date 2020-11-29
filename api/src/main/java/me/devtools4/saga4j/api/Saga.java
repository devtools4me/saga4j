package me.devtools4.saga4j.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;

public interface Saga {

  UUID getCorrelationId();

  Logger getLogger();

  String getName();

  List<Step> getSteps();

  default Output apply(Input input) {
    getLogger().info("Execute saga, correlationId={}, name={}", getCorrelationId(), getName());
    return execute(io.vavr.collection.List.ofAll(getSteps()), input, Status.PROCESSING);
  }

  default Output execute(io.vavr.collection.List<Step> steps, Input input, Status status) {
    return Optional.of(steps)
        .filter(x -> !x.isEmpty())
        .map(x -> {
          Step step = x.head();
          getLogger().info("Execute step={}", step);
          Output out = step.apply(getCorrelationId(), input);
          return execute(x.tail(), Input.builder()
                  .input(out.getOutput())
                  .build(),
              out.getStatus());
        })
        .orElseGet(() -> {
          getLogger().info("All steps executed, status={}", status);
          return Output.builder()
              .status(status)
              .output(input.getInput())
              .build();
        });
  }
}