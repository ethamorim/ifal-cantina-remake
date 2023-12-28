package br.ethamorim.cantina.ifal.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NaturalId
    String codigo;

    @NaturalId
    @Basic(optional = false)
    String nome;

    LocalDate dataFabricacao;

    @Basic(optional = false)
    LocalDate dataValidade;

    @Basic(optional = false)
    int quantidade;

    public Produto() {}

    public Produto(String nome, LocalDate dataValidade) {
        this(nome, null, dataValidade);
    }

    public Produto(String nome, String isoDataValidade) {
        this(nome, null, LocalDate.parse(isoDataValidade));
    }

    public Produto(String nome, String isoDataFabricacao, String isoDataValidade) {
        this(nome, LocalDate.parse(isoDataFabricacao), LocalDate.parse(isoDataValidade));
    }

    public Produto(String nome, LocalDate dataFabricacao, LocalDate dataValidade) {
        this.nome = nome;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
    }

    public UUID getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
