package io.github.alathra.alathraskills.db.flyway;

public class DatabaseMigrationException extends Exception {
    public DatabaseMigrationException(Throwable t) {
        super(t);
    }
}
