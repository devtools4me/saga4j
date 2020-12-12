package me.devtools4.saga4j.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import me.devtools4.saga4j.api.Status;

@Entity
@Table(name = "STEPS")
@Data
@NoArgsConstructor
@ToString(exclude = "saga")
public class StepEntity {

  @Id
  @NonNull
  @JsonIgnore
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "SAGA_ID_FK")
  @JsonIgnore
  private SagaEntity saga;

  @Column(name = "NAME")
  @NonNull
  private String name;

  @Column(name = "STATUS", length = 16)
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

  @Builder
  public StepEntity(Long id, SagaEntity saga, String name, Status status, Map<String, String> input,
      LocalDateTime dateTime) {
    Objects.requireNonNull(id, "id");
    this.id = id;

    Objects.requireNonNull(saga, "saga");
    this.saga = saga;

    Objects.requireNonNull(name, "name");
    this.name = name;

    Objects.requireNonNull(status, "status");
    this.status = status;

    Objects.requireNonNull(input, "input");
    this.input = input;

    Objects.requireNonNull(dateTime, "dateTime");
    this.startDateTime = dateTime;
    this.updateDateTime = dateTime;
  }

  public StepEntity withStatus(Status status, LocalDateTime dateTime) {
    this.status = status;
    this.updateDateTime = dateTime;
    return this;
  }
}