package me.devtools4.saga4j.repository.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import me.devtools4.saga4j.repository.IdGenerator;

public class EntityManagerIdGenerator implements IdGenerator<Long> {

  private final EntityManager em;
  private final String nextValueQuery;

  public EntityManagerIdGenerator(EntityManager em, String nextValueQuery) {
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