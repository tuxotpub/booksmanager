package org.tuxotpub.booksmanager.config;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class JsonPostgreSQLDialect extends PostgreSQL94Dialect {

    public JsonPostgreSQLDialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
}
