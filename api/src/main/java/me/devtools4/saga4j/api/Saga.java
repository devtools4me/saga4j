package me.devtools4.saga4j.api;

import io.vavr.collection.List;
import java.util.Optional;
import org.slf4j.Logger;

public interface Saga {
  Logger getLogger();

  String getContext();

  String getName();

  io.vavr.collection.List<Step> getSteps();

  default Output apply(Input input) {
    return execute(getSteps(), input, Status.PROCESSING);
  }

  default Output execute(List<Step> steps, Input input, Status status) {
    return Optional.of(steps)
        .filter(x -> !x.isEmpty())
        .map(x -> {
          Step step = x.head();
          getLogger().info("Execute step={}", step);
          Output out = step.apply(input);
          return execute(x.tail(), Input.builder()
              .input(steps.head()
                  .apply(input)
                  .getOutput())
              .build(), out.getStatus());
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