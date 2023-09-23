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
@Table(name = "estabelecimento")
public class EstabelecimentoModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "nome", unique = false, nullable = false)
    private String name;

    @Column(name = "cnpj", unique = true, nullable = false)
    private String cnpj;

    @Column(name = "endereco", unique = false, nullable = false)
    private String address;

    @Column(name = "telefone", unique = false, nullable = false)
    private String numberCellphone;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private UserModel responsible;

}