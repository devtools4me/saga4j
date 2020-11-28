package me.devtools4.saga4j.api;

import java.util.function.Function;

@FunctionalInterface
public interface Executor extends Function<Input, Output> {

}