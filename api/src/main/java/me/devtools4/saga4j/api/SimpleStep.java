package me.devtools4.saga4j.api;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
@Getter
@NoArgsConstructor
@EqualsAndHashCode
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

  @Builder
  public SimpleStep(String name, Executor executor, Transformer transformer, Fallback fallback) {
    this.name = name;
    this.executor = executor;
    this.transformer = transformer;
    this.fallback = fallback;
  }

  @Override
  public Logger getLogger() {
    return log;
  }
}