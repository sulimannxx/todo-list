import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnector {

    private static final String URL = EnvLoader.get("DB_URL", "jdbc:postgresql://localhost:5432/todo_list");
    private static final String USER = EnvLoader.get("DB_USER", "postgres");
    private static final String PASSWORD = EnvLoader.get("DB_PASSWORD", "postgres");

    private DatabaseConnector() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
