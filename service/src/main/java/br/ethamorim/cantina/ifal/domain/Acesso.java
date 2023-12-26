package br.ethamorim.cantina.ifal.domain;

import br.ethamorim.cantina.ifal.exceptions.EmptyParameterException;
import br.ethamorim.cantina.ifal.exceptions.InvalidParameterException;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
public abstract class Acesso {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @NaturalId
    @Basic(optional = false)
    String nomeUsuario;

    @Basic(optional = false)
    String senha;

    @OneToOne(optional = false, mappedBy = Funcionario_.ACESSO)
    Funcionario funcionario;

    public Acesso() {}

    public Acesso(String nomeUsuario, String senha, Funcionario funcionario)
            throws EmptyParameterException, InvalidParameterException {
        String mensagemInvalido = "Parâmetros de Acesso não podem ser nulos";
        try {
            if (nomeUsuario.isEmpty() || senha.isEmpty()) {
                throw new EmptyParameterException("Parâmetros de Acesso vazios");
            }
            if (funcionario == null) {
                throw new InvalidParameterException(mensagemInvalido);
            }
            this.nomeUsuario = nomeUsuario;
            this.senha = senha;
            this.funcionario = funcionario;
        } catch (NullPointerException e) {
            throw new InvalidParameterException(mensagemInvalido);
        }
    }

    public int getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
}
