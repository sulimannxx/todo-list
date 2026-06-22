import java.sql.SQLException;

public class UserService {

    public long saveDefaultUser() {
        long userId = Long.parseLong(EnvLoader.get("DB_DEFAULT_USER_ID", "1"));
        String userName = EnvLoader.get("DB_DEFAULT_USER_NAME", "Default user");

        saveUser(userId, userName);
        return userId;
    }

    public void saveUser(long userId, String userName) {
        String sql = """
                INSERT INTO "user" (id, name)
                VALUES (?, ?)
                ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name
                """;

        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setString(2, userName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not save user to database", e);
        }
    }
}
