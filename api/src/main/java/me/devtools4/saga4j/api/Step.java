package me.devtools4.saga4j.api;

import java.util.UUID;
import org.slf4j.Logger;

public interface Step {

  Logger getLogger();

  String getName();

  Executor getExecutor();

  Transformer getTransformer();

  Fallback getFallback();

  default Output apply(UUID correlationId, Input input) {
    try {
      getLogger().info("[{}] Execute step={}", correlationId, getName());
      return getExecutor()
          .andThen(getTransformer())
          .apply(input);
    } catch (Throwable e) {
      getLogger().error("[{}] Step failed, error={}", correlationId, e.getMessage(), e);
      return getFallback().apply(e, input);
    }
  }
}