package com.freebox.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "servico")
public class ServicoModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "nome", unique = false, nullable = false)
    private String nome;

    @Column(name = "preco", unique = false, nullable = false)
    private long preco;

    @Column(name = "inativo", unique = false, nullable = false, columnDefinition = "boolean default false")
    private boolean inativo = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_estabelecimento", referencedColumnName = "id")
    private EstabelecimentoModel businessService;

}
