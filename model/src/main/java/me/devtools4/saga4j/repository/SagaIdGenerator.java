package me.devtools4.saga4j.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Supplier;
import javax.persistence.EntityManager;

public class SagaIdGenerator implements Supplier<Long> {

  private final EntityManager em;
  private final String nextValueQuery;

  public SagaIdGenerator(EntityManager em, String nextValueQuery) {
    this.em = em;
    this.nextValueQuery = nextValueQuery;
  }

  @Override
  public Long get() {
    Object obj = em.createNativeQuery(nextValueQuery).getSingleResult();
    if (obj instanceof BigInteger) {
      return ((BigInteger) obj).longValue();
    } else if (obj instanceof BigDecimal) {
      return ((BigDecimal) obj).longValue();
    } else {
      throw new IllegalArgumentException("Unsupported result format, obj=" + obj);
    }
  }
}