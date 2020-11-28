package me.devtools4.saga4j.api;

import io.vavr.control.Try;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude = {"executor", "transformer", "fallback"})
public class Step {
  @NonNull
  private String name;
  @NonNull
  private Executor executor;
  @NonNull
  private Transformer transformer;
  @NonNull
  private Fallback fallback;

  public Output execute(Input input) {
    return Try.of(() -> executor.apply(input))
        .map(transformer)
        .getOrElseGet(e -> fallback.apply(e, input));
  }
}