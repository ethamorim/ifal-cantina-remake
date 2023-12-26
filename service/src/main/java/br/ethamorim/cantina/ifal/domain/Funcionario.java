package br.ethamorim.cantina.ifal.domain;

import br.ethamorim.cantina.ifal.cantina.Cargo;
import br.ethamorim.cantina.ifal.exceptions.EmptyParameterException;
import br.ethamorim.cantina.ifal.exceptions.InvalidParameterException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NaturalId;

@Entity
public class Funcionario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long codigoFuncionario;

    @NaturalId
    @Basic(optional = false)
    long cpf;

    @Basic(optional = false)
    String nome;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    Cargo cargo;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    Acesso acesso;

    public Funcionario() {}

    public Funcionario(long cpf, String nome, Cargo cargo) throws EmptyParameterException, InvalidParameterException {
        this.cpf = cpf;

        String mensagemNulo = "Funcionário contém parâmetros nulos não permitidos";
        try {
            if (nome.isEmpty()) {
                throw new EmptyParameterException("Nome não pode ser vazio");
            }
            if (cargo == null) {
                throw new InvalidParameterException(mensagemNulo);
            }
        } catch (NullPointerException e) {
            throw new InvalidParameterException(mensagemNulo);
        }

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

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }
}
