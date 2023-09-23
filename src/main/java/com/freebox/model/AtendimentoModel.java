package com.freebox.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "atendimento")
public class AtendimentoModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_agendamento", referencedColumnName = "id")
    private AgendamentoModel agendamento;

}