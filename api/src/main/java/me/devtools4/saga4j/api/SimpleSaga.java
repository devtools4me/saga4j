package me.devtools4.saga4j.api;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
@Value
@Builder
public class SimpleSaga implements Saga {

  @NonNull
  private String context;
  @NonNull
  private String name;
  @NonNull
  private List<Step> steps;

  @Override
  public Logger getLogger() {
    return log;
  }
}