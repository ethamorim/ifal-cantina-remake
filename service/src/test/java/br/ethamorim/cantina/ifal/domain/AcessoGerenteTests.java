package br.ethamorim.cantina.ifal.domain;

import br.ethamorim.cantina.ifal.cantina.Cargo;
import br.ethamorim.cantina.ifal.exceptions.EmptyParameterException;
import br.ethamorim.cantina.ifal.exceptions.InvalidParameterException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.tool.schema.Action;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class AcessoGerenteTests {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void init() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(AcessoGerente.class)
                .addAnnotatedClass(Funcionario.class)
                .setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.SPEC_ACTION_DROP_AND_CREATE)
                .buildSessionFactory();
    }

    @Test
    public void persisteAcessoGerente_depoisVerificaInsercao_depoisRemoveAcesso_finalmenteVerificaRemocao() {
        sessionFactory.inTransaction(session -> {
            try {
                var acesso = new AcessoGerente("deisantix", "123456", new Funcionario());
                session.persist(acesso);
                session.flush();
                var acessoAdicionado = session.byNaturalId(AcessoGerente.class)
                        .using(AcessoGerente_.nomeUsuario, "deisantix")
                        .load();
                Assert.assertNotNull(acessoAdicionado);
                Assert.assertEquals(acesso, acessoAdicionado);

                session.remove(acesso);
                session.flush();
                List<AcessoGerente> acessos = session.createSelectionQuery("from AcessoGerente", AcessoGerente.class)
                        .getResultList();
                Assert.assertEquals(0, acessos.size());
            } catch (InvalidParameterException | EmptyParameterException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void persiteAcessoGerenteComFuncionarioVazio_finalmenteVerificaFaltaDeIdFuncionario() {
        sessionFactory.inTransaction(session -> {
            try {
                session.persist(new AcessoGerente("deisanti", "123456", new Funcionario()));
                session.flush();

                Funcionario funcionario = session.byNaturalId(AcessoGerente.class)
                        .using(AcessoGerente_.nomeUsuario, "deisanti")
                        .load()
                        .getFuncionario();

                long codigoPadrao = 0;
                Assert.assertEquals(codigoPadrao, funcionario.getCodigoFuncionario());

                session.createMutationQuery("delete from AcessoGerente").executeUpdate();
            } catch (EmptyParameterException | InvalidParameterException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void persisteAcessoGerente_depoisVerificaExistenciaDeFuncionario_depoisRemoveFuncionario_finalmenteVerificaEfeitoCascata() {
        sessionFactory.inTransaction(session -> {
            try {
                long cpf = 12312312321L;
                var funcionario = new Funcionario(cpf, "Ethaniel", "2003-11-10", Cargo.GERENTE, "2023-12-26");
                var acesso = new AcessoGerente("deisantix", "123456", funcionario);
                session.persist(funcionario);
                funcionario.setAcesso(acesso);
                session.persist(acesso);

                AcessoGerente acessoBuscado = session.byNaturalId(AcessoGerente.class)
                        .using(AcessoGerente_.nomeUsuario, "deisantix")
                        .load();
                Assert.assertNotNull(acessoBuscado.getFuncionario());
                Assert.assertEquals(cpf, acessoBuscado.getFuncionario().getCpf());

                session.remove(funcionario);
                session.flush();

                AcessoGerente acessoExcluidoPorCascata = session.byNaturalId(AcessoGerente.class)
                        .using(AcessoGerente_.nomeUsuario, "deisantix")
                        .load();
                Assert.assertNull(acessoExcluidoPorCascata);
            } catch (EmptyParameterException | InvalidParameterException e) {
                e.printStackTrace();
            }
        });
    }

    @Test(expected = ConstraintViolationException.class)
    public void lancaErroAoTentarPersistirUsuariosComNomeIguais() {
        sessionFactory.inTransaction(session -> {
            try {
                var acesso1 = new AcessoGerente("deisantix", "123456", new Funcionario());
                var acesso2 = new AcessoGerente("deisantix", "senha", new Funcionario());

                session.persist(acesso1);
                session.persist(acesso2);
                session.flush();
            } catch (InvalidParameterException | EmptyParameterException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void lancaErroPorParametroNulo() {
        sessionFactory.inTransaction((session) -> {
            Assert.assertThrows(InvalidParameterException.class, () -> {
                session.persist(new AcessoGerente(null, "123456", new Funcionario()));
            });

            Assert.assertThrows(InvalidParameterException.class, () -> {
                session.persist(new AcessoGerente("deisantix", null, new Funcionario()));
            });
        });
    }

    @Test
    public void lancaErroPorParametroVazio() {
        sessionFactory.inTransaction(session -> {
            Assert.assertThrows(EmptyParameterException.class, () -> {
                session.persist(new AcessoGerente("", "123456", new Funcionario()));
            });

            Assert.assertThrows(EmptyParameterException.class, () -> {
                session.persist(new AcessoGerente("deisantix", "", new Funcionario()));
            });
        });
    }
}
