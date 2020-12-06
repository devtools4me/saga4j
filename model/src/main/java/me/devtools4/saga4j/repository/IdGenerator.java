package me.devtools4.saga4j.repository;

import java.util.function.Supplier;

public interface IdGenerator<T> extends Supplier<T> {}