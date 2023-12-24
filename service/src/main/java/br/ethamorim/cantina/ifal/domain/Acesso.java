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

    public Acesso() {}

    public Acesso(String nomeUsuario, String senha)
            throws EmptyParameterException, InvalidParameterException {
        try {
            if (nomeUsuario.isEmpty() || senha.isEmpty()) {
                throw new EmptyParameterException("Parâmetros de Acesso vazios");
            }
            this.nomeUsuario = nomeUsuario;
            this.senha = senha;
        } catch (NullPointerException e) {
            throw new InvalidParameterException("Parâmetros de Acesso não pode ser nulos");
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
}
