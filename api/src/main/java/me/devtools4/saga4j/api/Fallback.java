package me.devtools4.saga4j.api;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Fallback extends BiFunction<Throwable, Input, Output> {

}