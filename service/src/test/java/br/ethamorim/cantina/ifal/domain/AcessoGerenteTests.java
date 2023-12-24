package br.ethamorim.cantina.ifal.domain;

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
                .setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.SPEC_ACTION_DROP_AND_CREATE)
                .buildSessionFactory();
    }

    @Test
    public void persisteAcessoGerente_depoisVerificaInsercao_depoisRemoveAcesso_finalmenteVerificaRemocao() {
        sessionFactory.inTransaction(session -> {
            try {
                var acesso = new AcessoGerente("deisantix", "123456");
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

    @Test(expected = ConstraintViolationException.class)
    public void lancaErroAoTentarPersistirUsuariosComNomeIguais() {
        sessionFactory.inTransaction(session -> {
            try {
                var acesso1 = new AcessoGerente("deisantix", "123456");
                var acesso2 = new AcessoGerente("deisantix", "senha");

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
                session.persist(new AcessoGerente(null, "123456"));
            });

            Assert.assertThrows(InvalidParameterException.class, () -> {
                session.persist(new AcessoGerente("deisantix", null));
            });
        });
    }

    @Test
    public void lancaErroPorParametroVazio() {
        sessionFactory.inTransaction(session -> {
            Assert.assertThrows(EmptyParameterException.class, () -> {
                session.persist(new AcessoGerente("", "123456"));
            });

            Assert.assertThrows(EmptyParameterException.class, () -> {
                session.persist(new AcessoGerente("deisantix", ""));
            });
        });
    }
}
