package br.ethamorim.service.domain;

import br.ethamorim.cantina.ifal.cantina.Cargo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Funcionario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long codigoFuncionario;

    @NotNull
    long cpf;

    @NotNull
    String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    Cargo cargo;

    public Funcionario() {}

    public Funcionario(long cpf, String nome, Cargo cargo) {
        this.cpf = cpf;
        this.nome = nome;
        this.cargo = cargo;
    }
}
