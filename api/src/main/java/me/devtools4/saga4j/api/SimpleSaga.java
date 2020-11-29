package me.devtools4.saga4j.api;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class SimpleSaga implements Saga {

  @NonNull
  private UUID correlationId;
  @NonNull
  private String name;
  @NonNull
  private List<Step> steps;

  @Builder
  public SimpleSaga(UUID correlationId, String name, List<Step> steps) {
    this.correlationId = correlationId;
    this.name = name;
    this.steps = steps;
  }

  @Override
  public Logger getLogger() {
    return log;
  }
}