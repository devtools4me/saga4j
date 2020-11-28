package me.devtools4.saga4j.api;

import java.util.Map;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Input {
  @NonNull
  private Map<String, String> input;
}