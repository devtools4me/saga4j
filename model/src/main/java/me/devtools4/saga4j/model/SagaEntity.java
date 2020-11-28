package me.devtools4.saga4j.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import me.devtools4.saga4j.api.Recovery;
import me.devtools4.saga4j.api.Status;

@Entity
@Table(
    name = "SAGAS",
    indexes = {
        @Index(name = "IDX_SAGAS_CTX_NAME", columnList = "CONTEXT,NAME")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "CNSTR_SAGAS_CTX_NAME", columnNames = {"CONTEXT", "NAME"})
    })
@Getter
@NoArgsConstructor
public class SagaEntity {

  @Id
  @NonNull
  @JsonIgnore
  private Long id;

  @Column(name = "CONTEXT")
  @NonNull
  private String context;

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

  @Column(name = "DATE_TIME")
  @Convert(converter = LocalDateTimeAttributeConverter.class)
  @NonNull
  private LocalDateTime dateTime;

  @OneToMany(
      mappedBy = "saga",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER
  )
  private List<StepEntity> steps = new ArrayList<>();
}