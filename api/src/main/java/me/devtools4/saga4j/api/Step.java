package me.devtools4.saga4j.api;

public interface Step {

  String getName();

  Executor getExecutor();

  Transformer getTransformer();

  Fallback getFallback();

  default Output apply(Input input) {
    try {
      return getExecutor()
          .andThen(getTransformer())
          .apply(input);
    } catch (Throwable e) {
      return getFallback().apply(e, input);
    }
  }
}