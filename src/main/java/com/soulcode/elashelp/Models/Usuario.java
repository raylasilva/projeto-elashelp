package com.soulcode.elashelp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "usuarios")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;

    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column
    private String sobrenome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column
    private String telefone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Ticket> tickets;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Login login;



}
