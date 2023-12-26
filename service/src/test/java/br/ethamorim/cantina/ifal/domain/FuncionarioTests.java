package br.ethamorim.cantina.ifal.domain;


import br.ethamorim.cantina.ifal.domain.Funcionario_;
import br.ethamorim.cantina.ifal.exceptions.EmptyParameterException;
import br.ethamorim.cantina.ifal.exceptions.InvalidParameterException;
import org.hibernate.tool.schema.Action;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AvailableSettings;

import br.ethamorim.cantina.ifal.cantina.Cargo;
import br.ethamorim.cantina.ifal.domain.Funcionario;

import java.util.List;


public class FuncionarioTests {
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setUp() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Funcionario.class)
                .addAnnotatedClass(AcessoGerente.class)
                .setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.SPEC_ACTION_DROP_AND_CREATE)
                .buildSessionFactory();
    }

    @Test
    public void persisteFuncionarios_depoisVerificaInsercao_depoisRemoveFuncionarios_finalmenteVerificaRemocao() {
        sessionFactory.inTransaction(session -> {
            try {
                var funcionario1 = new Funcionario(12312312321L, "Ethaniel", Cargo.GERENTE);
                session.persist(funcionario1);

                var funcionario2 = new Funcionario(43243243256L, "Ytalo", Cargo.GERENTE);
                session.persist(funcionario2);
                session.flush();

                List<Funcionario> funcionarios = session.createSelectionQuery("from Funcionario", Funcionario.class)
                        .getResultList();
                int quantidadeEsperada = 2;
                Assert.assertEquals(quantidadeEsperada, funcionarios.size());
                funcionarios.forEach(Assert::assertNotNull);

                session.remove(funcionario1);
                session.flush();

                List<Funcionario> funcionarioAposExclusao = session.createSelectionQuery("from Funcionario", Funcionario.class)
                        .getResultList();
                int quantidadeEsperadaAposExclusao = 1;
                Assert.assertEquals(quantidadeEsperadaAposExclusao, funcionarioAposExclusao.size());

                session.remove(funcionario2);
                session.flush();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void verificaInformacaoFuncionario() {
        long cpf = 123123123L;
        String nome = "Ethaniel Amorim";
        Cargo cargo = Cargo.GERENTE;

        sessionFactory.inTransaction(session -> {
            try {
                session.persist(new Funcionario(cpf, nome, cargo));
                session.flush();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        sessionFactory.inSession(session -> {
            Funcionario funcionario = session.byNaturalId(Funcionario.class)
                    .using(Funcionario_.cpf, cpf)
                    .load();
            Assert.assertEquals(nome, funcionario.getNome());
            Assert.assertEquals(cargo, funcionario.getCargo());
        });

        sessionFactory.inTransaction(session -> {
            session.createMutationQuery("delete from Funcionario").executeUpdate();
        });
    }

    @Test
    public void persisteFuncionarioComAcesso_finalmenteVerificaPersistenciaAcesso() {
        sessionFactory.inTransaction(session -> {
            try {
                var funcionario = new Funcionario(12312312351L, "Ethaniel", Cargo.GERENTE);
                session.persist(funcionario);

                var acesso = new AcessoGerente("deisanti", "123456", funcionario);
                session.persist(acesso);

                funcionario.setAcesso(acesso);
                session.flush();

                Funcionario funcionarioAcessado = session.find(Funcionario.class, funcionario.getCodigoFuncionario());
                Assert.assertNotNull(funcionarioAcessado);
                Assert.assertEquals(funcionario, funcionarioAcessado);
                Assert.assertNotNull(funcionarioAcessado.getAcesso());
                Assert.assertEquals("deisanti", funcionarioAcessado.getAcesso().getNomeUsuario());
            } catch (EmptyParameterException | InvalidParameterException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void persisteFuncionarios_finalmenteVerificaValoresDiferentes() {
        sessionFactory.inTransaction(session -> {
            try {
                long cpf1 = 12312312345L;
                var funcionario1 = new Funcionario(cpf1, "Ethaniel", Cargo.GERENTE);
                session.persist(funcionario1);
                var acesso1 = new AcessoGerente("ethaniel", "123123", funcionario1);
                session.persist(acesso1);
                funcionario1.setAcesso(acesso1);

                long cpf2 = 12345678910L;
                var funcionario2 = new Funcionario(cpf2, "Ytalo", Cargo.GERENTE);
                session.persist(funcionario2);
                var acesso2 = new AcessoGerente("ytalo", "123456", funcionario2);
                session.persist(acesso2);
                funcionario2.setAcesso(acesso2);
                session.flush();

                Funcionario acessado1 = session.byNaturalId(Funcionario.class)
                        .using(Funcionario_.cpf, cpf1)
                        .load();
                Assert.assertNotNull(acessado1);
                Assert.assertEquals("ethaniel", acessado1.getAcesso().getNomeUsuario());

                Funcionario acessado2 = session.byNaturalId(Funcionario.class)
                        .using(Funcionario_.cpf, cpf2)
                        .load();
                Assert.assertNotNull(acessado2);
                Assert.assertEquals("ytalo", acessado2.getAcesso().getNomeUsuario());

                session.createMutationQuery("delete from Funcionario").executeUpdate();
            } catch (EmptyParameterException | InvalidParameterException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void lancaErroPorParametroNulo() {
        sessionFactory.inTransaction(session -> {
            Assert.assertThrows(InvalidParameterException.class, () -> {
                session.persist(new Funcionario(12345678910L, null, Cargo.GERENTE));
            });

            Assert.assertThrows(InvalidParameterException.class, () -> {
                session.persist(new Funcionario(1234123421L, "Ytalo Ethaniel", null));
            });
        });
    }

    @Test
    public void lancaErroPorParametroVazio() {
        sessionFactory.inTransaction(session -> {
            Assert.assertThrows(EmptyParameterException.class, () -> {
                session.persist(new Funcionario(12345678910L, "", Cargo.GERENTE));
            });
        });
    }
}
