package br.ethamorim.cantina.ifal.domain;

import br.ethamorim.cantina.ifal.cantina.Cargo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.NaturalId;

@Entity
public class Funcionario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long codigoFuncionario;

    @NotNull
    @NaturalId
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

    public long getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public long getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public Cargo getCargo() {
        return cargo;
    }
}
