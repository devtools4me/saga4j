package me.devtools4.saga4j.api;

import io.vavr.collection.List;
import java.util.Optional;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
@Builder
public class Saga {

  @NonNull
  private String context;
  @NonNull
  private String name;
  @NonNull
  private List<Step> steps;

  public Output execute(Input input) {
    return execute(steps, input, Status.PROCESSING);
  }

  private static Output execute(List<Step> steps, Input input, Status status) {
    return Optional.of(steps)
        .filter(x -> !x.isEmpty())
        .map(x -> {
          Step step = x.head();
          log.info("Execute step={}", step);
          Output out = step.execute(input);
          return execute(x.tail(), Input.builder()
              .input(steps.head()
                  .execute(input)
                  .getOutput())
              .build(), out.getStatus());
        })
        .orElseGet(() -> {
          log.info("All steps executed, status={}", status);
          return Output.builder()
              .status(status)
              .output(input.getInput())
              .build();
        });
  }
}