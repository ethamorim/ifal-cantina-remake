package br.ethamorim.cantina.ifal.domain;

import br.ethamorim.cantina.ifal.exceptions.EmptyParameterException;
import br.ethamorim.cantina.ifal.exceptions.InvalidParameterException;
import jakarta.persistence.Entity;

@Entity
public class AcessoGerente extends Acesso {
    public AcessoGerente() {}

    public AcessoGerente(String nomeUsuario, String senha, Funcionario funcionario) throws InvalidParameterException, EmptyParameterException {
        super(nomeUsuario, senha, funcionario);
    }
}
