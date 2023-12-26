package br.ethamorim.cantina.ifal;

import br.ethamorim.cantina.ifal.domain.AcessoGerenteTests;
import br.ethamorim.cantina.ifal.domain.AcessoVendedorTests;
import br.ethamorim.cantina.ifal.domain.FuncionarioTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        FuncionarioTests.class,
        AcessoVendedorTests.class,
        AcessoGerenteTests.class
})
public class DomainSuiteTests {
}
