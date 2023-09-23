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
@Table(name = "usuario")
public class UserModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "nome", unique = false, nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "senha", unique = false, nullable = false)
    private String password;

    @Column(name = "celular", unique = false, nullable = false)
    private String numberCellphone;

    @Column(name = "cpf", unique = false, nullable = false)
    private String cpf;

    @Column(name = "data_nascimento", unique = false, nullable = false)
    private Date birthDate;

}
