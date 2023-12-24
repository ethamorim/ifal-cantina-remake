package br.ethamorim.service.test;


import br.ethamorim.cantina.ifal.domain.Funcionario_;
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
                .setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.SPEC_ACTION_DROP_AND_CREATE)
                .buildSessionFactory();
    }

    @Test
    public void persisteFuncionario() {
        sessionFactory.inTransaction(session -> {
            session.persist(new Funcionario(12312312321L, "Ethaniel", Cargo.GERENTE));
            session.persist(new Funcionario(43243243256L, "Ytalo", Cargo.GERENTE));
        });

        sessionFactory.inSession(session -> {
           List<Funcionario> funcionarios = session.createSelectionQuery("from Funcionario", Funcionario.class)
                   .getResultList();
           int quantidadeEsperada = 2;
           Assert.assertEquals(quantidadeEsperada, funcionarios.size());

           funcionarios.forEach(Assert::assertNotNull);
        });
    }

    @Test
    public void verificaInformacaoFuncionario() {
        long cpf = 123123123L;
        String nome = "Ethaniel Amorim";
        Cargo cargo = Cargo.GERENTE;

        sessionFactory.inTransaction(session -> {
            session.persist(new Funcionario(cpf, nome, cargo));
        });

        sessionFactory.inSession(session -> {
            Funcionario funcionario = session.byNaturalId(Funcionario.class)
                    .using(Funcionario_.cpf, cpf)
                    .load();
            Assert.assertEquals(nome, funcionario.getNome());
            Assert.assertEquals(cargo, funcionario.getCargo());
        });
    }
}
