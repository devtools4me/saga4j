package me.devtools4.saga4j.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude = {"executor", "transformer", "fallback"})
public class SimpleStep implements Step {
  @NonNull
  private String name;
  @NonNull
  private Executor executor;
  @NonNull
  private Transformer transformer;
  @NonNull
  private Fallback fallback;
}