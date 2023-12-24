package br.ethamorim.cantina.ifal.domain;

import br.ethamorim.cantina.ifal.exceptions.EmptyParameterException;
import br.ethamorim.cantina.ifal.exceptions.InvalidParameterException;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class AcessoVendedor extends Acesso {

    int metaVendas;

    int quantidadeVendas;

    public AcessoVendedor() {}

    public AcessoVendedor(String nomeUsuario, String senha, int metaVendas, int quantidadeVendas)
            throws InvalidParameterException, EmptyParameterException {
        super(nomeUsuario, senha);

        if (metaVendas <= 0) {
            throw new InvalidParameterException("Meta de vendas não pode ser menor que zero");
        }
        this.metaVendas = metaVendas;
        this.quantidadeVendas = quantidadeVendas;
    }

    public int getMetaVendas() {
        return metaVendas;
    }

    public int getQuantidadeVendas() {
        return quantidadeVendas;
    }
}
