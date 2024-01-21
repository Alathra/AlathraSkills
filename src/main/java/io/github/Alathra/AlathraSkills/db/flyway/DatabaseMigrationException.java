package io.github.Alathra.AlathraSkills.db.flyway;

public class DatabaseMigrationException extends Exception {
    public DatabaseMigrationException(Throwable t) {
        super(t);
    }
}
