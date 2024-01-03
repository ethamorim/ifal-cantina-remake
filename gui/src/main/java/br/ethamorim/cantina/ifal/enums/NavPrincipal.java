package br.ethamorim.cantina.ifal.enums;

public enum NavPrincipal {
    HISTORICO("Histórico"),
    CAIXA("Caixa");

    private final String repr;

    NavPrincipal(String repr) {
        this.repr = repr;
    }

    public String getRepr() {
        return repr;
    }
}
