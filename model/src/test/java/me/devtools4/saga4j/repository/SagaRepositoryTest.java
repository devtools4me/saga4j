package me.devtools4.saga4j.repository;

import static me.devtools4.saga4j.api.Recovery.FORWARD;
import static me.devtools4.saga4j.api.Status.STARTED;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import me.devtools4.saga4j.model.SagaEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = SagaRepository.class)
@EntityScan(basePackageClasses = SagaEntity.class)
public class SagaRepositoryTest {
  @Autowired
  private SagaRepository sut;
  @Autowired
  private IdGenerator<Long> idGenerator;

  @Test
  public void testFind() {
    UUID correlationId = UUID.randomUUID();
    SagaEntity entity = sut.save(SagaEntity.builder()
        .id(idGenerator.get())
        .correlationId(correlationId)
        .name("TEST")
        .status(STARTED)
        .recovery(FORWARD)
        .input(Collections.emptyMap())
        .steps(Collections.emptyList())
        .dateTime(LocalDateTime.now())
        .build());
    assertNotNull(entity);

    SagaEntity found = sut.findDistinctByCorrelationId(correlationId);
    assertThat(entity, is(found));
  }
}