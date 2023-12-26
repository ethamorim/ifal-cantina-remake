package br.ethamorim.cantina.ifal.domain;

import br.ethamorim.cantina.ifal.exceptions.EmptyParameterException;
import br.ethamorim.cantina.ifal.exceptions.InvalidParameterException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.schema.Action;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class AcessoVendedorTests {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void init() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(AcessoVendedor.class)
                .addAnnotatedClass(Funcionario.class)
                .setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION, Action.SPEC_ACTION_DROP_AND_CREATE)
                .buildSessionFactory();
    }

    @Test
    public void persisteAcessoVendedor_depoisVerificaInsercao_depoisRemoveAcesso_finalmenteVerificaRemocao() {
        sessionFactory.inTransaction(session -> {
            int meta = 100;
            try {
                var acesso = new AcessoVendedor("deisantix", "123456", meta, 0, new Funcionario());
                session.persist(acesso);
                session.flush();
                var acessoAdicionado = session.byNaturalId(AcessoVendedor.class)
                        .using(AcessoVendedor_.nomeUsuario, "deisantix")
                        .load();
                Assert.assertNotNull(acessoAdicionado);
                Assert.assertEquals(acesso, acessoAdicionado);
                Assert.assertEquals(meta, acessoAdicionado.getMetaVendas());

                session.remove(acesso);
                session.flush();
                List<AcessoVendedor> acessos = session.createSelectionQuery("from AcessoVendedor", AcessoVendedor.class)
                        .getResultList();
                Assert.assertEquals(0, acessos.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test(expected = ConstraintViolationException.class)
    public void lancaErroAoTentarPersistirUsuariosComNomeIguais() {
        sessionFactory.inTransaction(session -> {
            try {
                var acesso1 = new AcessoVendedor("deisantix", "123456", 50, 0, new Funcionario());
                var acesso2 = new AcessoVendedor("deisantix", "senha", 100, 0, new Funcionario());

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
                session.persist(new AcessoVendedor(null, "123456", 50, 0, new Funcionario()));
            });

            Assert.assertThrows(InvalidParameterException.class, () -> {
               session.persist(new AcessoVendedor("deisantix", null, 50, 0, new Funcionario()));
            });
        });
    }

    @Test
    public void lancaErroPorParametroVazio() {
        sessionFactory.inTransaction(session -> {
            Assert.assertThrows(EmptyParameterException.class, () -> {
                session.persist(new AcessoVendedor("", "123456", 50, 0, new Funcionario()));
            });

            Assert.assertThrows(EmptyParameterException.class, () -> {
                session.persist(new AcessoVendedor("deisantix", "", 50, 0, new Funcionario()));
            });
        });
    }

    @Test
    public void lancaErroPorParametroInvalido() {
        sessionFactory.inTransaction(session -> {
            Assert.assertThrows(InvalidParameterException.class, () -> {
                session.persist(new AcessoVendedor("deisantix", "123456", 0, 0, new Funcionario()));
            });
        });
    }
}
