package com.study.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_beneficiary")
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private LocalDate birthday;
    private LocalDate inclusionDate;
    private LocalDate updateDate;
    private Boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> document;

    public long getId() {
        return id;
    }
}
