package br.ethamorim.cantina.ifal.domain;

import br.ethamorim.cantina.ifal.cantina.Cargo;
import br.ethamorim.cantina.ifal.exceptions.EmptyParameterException;
import br.ethamorim.cantina.ifal.exceptions.InvalidParameterException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;

@Entity
public class Funcionario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long codigoFuncionario;

    @NaturalId
    @Basic(optional = false)
    long cpf;

    @Basic(optional = false)
    String nome;

    @Basic(optional = false)
    LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    Cargo cargo;

    @Basic(optional = false)
    LocalDate dataEntrada;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    Acesso acesso;

    public Funcionario() {}

    public Funcionario(long cpf, String nome, LocalDate dataNascimento, Cargo cargo, LocalDate dataEntrada)
        throws EmptyParameterException, InvalidParameterException {
        this.cpf = cpf;

        String mensagemNulo = "Funcionário contém parâmetros nulos não permitidos";
        try {
            if (nome.isEmpty()) {
                throw new EmptyParameterException("Nome não pode ser vazio");
            }
            if (cargo == null || dataNascimento == null || dataEntrada == null) {
                throw new InvalidParameterException(mensagemNulo);
            }
        } catch (NullPointerException e) {
            throw new InvalidParameterException(mensagemNulo);
        }

        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cargo = cargo;
        this.dataEntrada = dataEntrada;
    }

    public Funcionario(long cpf, String nome, String isoDataNascimento, Cargo cargo, String isoDataEntrada)
        throws EmptyParameterException, InvalidParameterException {
        this(cpf, nome, LocalDate.parse(isoDataNascimento), cargo, LocalDate.parse(isoDataEntrada));
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
