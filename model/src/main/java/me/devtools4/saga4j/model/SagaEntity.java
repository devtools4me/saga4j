package me.devtools4.saga4j.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import me.devtools4.saga4j.api.Recovery;
import me.devtools4.saga4j.api.Status;

@Entity
@Table(
    name = "SAGAS",
    indexes = {
        @Index(name = "IDX_CORR_ID", columnList = "CORR_ID")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "CNSTR_SAGAS_CORR_ID", columnNames = {"CORR_ID"})
    })
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class SagaEntity {

  @Id
  @NonNull
  @JsonIgnore
  private Long id;

  @Column(name = "CORR_ID")
  @NonNull
  private UUID correlationId;

  @Column(name = "NAME")
  @NonNull
  private String name;

  @Column(name = "RECOVERY_TYPE", length = 16)
  @Enumerated(EnumType.STRING)
  @NonNull
  private Recovery recovery;

  @Column(name = "STATUS_TYPE", length = 16)
  @Enumerated(EnumType.STRING)
  @NonNull
  private Status status;

  @ElementCollection(fetch = FetchType.EAGER)
  @NonNull
  private Map<String, String> input = new HashMap<>();

  @Column(name = "START_DATE_TIME")
  @Convert(converter = LocalDateTimeAttributeConverter.class)
  @NonNull
  private LocalDateTime startDateTime;

  @Column(name = "UPDATE_DATE_TIME")
  @Convert(converter = LocalDateTimeAttributeConverter.class)
  @NonNull
  private LocalDateTime updateDateTime;

  @OneToMany(
      mappedBy = "saga",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER
  )
  @NonNull
  private List<StepEntity> steps = new ArrayList<>();

  @Builder
  public SagaEntity(Long id, UUID correlationId, String name, Recovery recovery,
      Status status, Map<String, String> input, LocalDateTime dateTime,
      List<StepEntity> steps) {
    Objects.requireNonNull(id, "id");
    this.id = id;

    Objects.requireNonNull(correlationId, "correlationId");
    this.correlationId = correlationId;

    Objects.requireNonNull(name, "name");
    this.name = name;

    Objects.requireNonNull(recovery, "recovery");
    this.recovery = recovery;

    Objects.requireNonNull(status, "status");
    this.status = status;

    Objects.requireNonNull(input, "input");
    this.input = input;

    Objects.requireNonNull(dateTime, "dateTime");
    this.startDateTime = dateTime;
    this.updateDateTime = dateTime;

    withSteps(Optional.ofNullable(steps).orElseGet(Collections::emptyList));
  }

  public SagaEntity withStatus(Status status, LocalDateTime dateTime) {
    this.status = status;
    this.updateDateTime = dateTime;
    return this;
  }

  public SagaEntity withSteps(List<StepEntity> steps) {
    Objects.requireNonNull(steps, "steps");
    this.steps = steps;
    steps.forEach(x -> x.setSaga(this));
    return this;
  }

  public StepEntity stepWithName(String name) {
    return getSteps()
        .stream()
        .filter(x -> x.getName().equals(name))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException());
  }
}