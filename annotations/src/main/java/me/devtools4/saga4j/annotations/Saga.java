package me.devtools4.saga4j.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Saga {
  String context();
  String name();
  Recovery recovery() default Recovery.BACKWARD;

  enum Recovery {
    BACKWARD,
    FORWARD,
    MANUAL,
    CUSTOM
  }
}