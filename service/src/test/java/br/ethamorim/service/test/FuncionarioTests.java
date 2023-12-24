package br.ethamorim.service.test;


import org.hibernate.tool.schema.Action;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AvailableSettings;

import br.ethamorim.cantina.ifal.cantina.Cargo;
import br.ethamorim.service.domain.Funcionario;


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
    public void testPersisteFuncionario() {
        sessionFactory.inSession(session -> {
            session.persist(new Funcionario(12312312321L, "Ethaniel", Cargo.GERENTE));
        });

        sessionFactory.inSession(session -> {
           session.createSelectionQuery("from Funcionario", Funcionario.class)
                   .getResultList()
                   .forEach(Assert::assertNotNull);
        });
    }
}
