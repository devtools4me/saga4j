package me.devtools4.saga4j.api;

import java.util.function.Function;

@FunctionalInterface
public interface Transformer extends Function<Output, Output> {

}