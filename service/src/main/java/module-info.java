module service.cantina {
    requires jakarta.persistence;
    requires jakarta.validation;
    requires org.hibernate.orm.core;

    exports br.ethamorim.service.domain to test.service.cantina;
    exports br.ethamorim.cantina.ifal.cantina to test.service.cantina;

    opens br.ethamorim.service.domain;
}