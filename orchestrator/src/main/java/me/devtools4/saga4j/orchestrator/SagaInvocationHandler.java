package me.devtools4.saga4j.orchestrator;

import static me.devtools4.saga4j.api.Status.STARTED;
import static me.devtools4.saga4j.orchestrator.Ops.toModel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import me.devtools4.saga4j.api.SimpleSaga;
import me.devtools4.saga4j.api.Status;
import me.devtools4.saga4j.model.SagaEntity;
import me.devtools4.saga4j.repository.SagaRepository;

@Slf4j
public class SagaInvocationHandler implements InvocationHandler {
  private final SagaRepository repository;

  public SagaInvocationHandler(SagaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    log.info("Invoked method: {}", method.getName());

    if (!method.getName().equals("apply")) {
      throw new UnsupportedOperationException("Unsupported method: " + method.getName());
    }

    SagaEntity entity = repository.save(toModel((SimpleSaga)proxy, STARTED, LocalDateTime.now()));

    Object res = method.invoke(proxy, args);

    repository.save(entity.withStatus(Status.PROCESSED, LocalDateTime.now()));

    return res;
  }
}