import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DatabaseTaskHandler implements TaskHandler {

    private final long userId;

    public DatabaseTaskHandler(UserService userService) {
        userId = userService.saveDefaultUser();
    }

    @Override
    public List<Task> getTasks() {
        return getTasksByQuery(
                """
                        SELECT description, deadline_date, category, is_done, priority
                        FROM task
                        WHERE user_id = ?
                        ORDER BY id
                        """,
                statement -> statement.setLong(1, userId)
        );
    }

    @Override
    public List<Task> getDoneTasks() {
        return getTasksByQuery(
                """
                        SELECT description, deadline_date, category, is_done, priority
                        FROM task
                        WHERE user_id = ? AND is_done = TRUE
                        ORDER BY id
                        """,
                statement -> statement.setLong(1, userId)
        );
    }

    @Override
    public List<Task> getUndoneTasks() {
        return getTasksByQuery(
                """
                        SELECT description, deadline_date, category, is_done, priority
                        FROM task
                        WHERE user_id = ? AND is_done = FALSE
                        ORDER BY id
                        """,
                statement -> statement.setLong(1, userId)
        );
    }

    @Override
    public List<Task> getTasksWithCategory(String category) {
        return getTasksByQuery(
                """
                        SELECT description, deadline_date, category, is_done, priority
                        FROM task
                        WHERE user_id = ? AND category = ?
                        ORDER BY id
                        """,
                statement -> {
                    statement.setLong(1, userId);
                    statement.setString(2, category);
                }
        );
    }

    @Override
    public boolean addTask(Task task) {
        String sql = """
                INSERT INTO task (description, deadline_date, category, is_done, priority, user_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getDescription());
            statement.setDate(2, Date.valueOf(task.getDeadLineDate()));
            statement.setString(3, task.getCategory());
            statement.setBoolean(4, task.isDone());
            statement.setInt(5, task.getPriority());
            statement.setLong(6, userId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not add task to database", e);
        }
    }

    @Override
    public boolean tryDeleteTask(int taskNumber) {
        Long taskId = getTaskIdByNumber(taskNumber);

        if (taskId == null) {
            return false;
        }

        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement("""
                     DELETE FROM task
                     WHERE id = ? AND user_id = ?
                     """)) {

            statement.setLong(1, taskId);
            statement.setLong(2, userId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not delete task from database", e);
        }
    }

    @Override
    public boolean tryMarkAsDone(int taskNumber) {
        return trySetDone(taskNumber, true);
    }

    @Override
    public boolean tryMarkAsUnDone(int taskNumber) {
        return trySetDone(taskNumber, false);
    }

    @Override
    public List<Task> getTasksSortedByPriority(int priority) {
        return getTasksByQuery(
                """
                        SELECT description, deadline_date, category, is_done, priority
                        FROM task
                        WHERE user_id = ? AND priority = ?
                        ORDER BY id
                        """,
                statement -> {
                    statement.setLong(1, userId);
                    statement.setInt(2, priority);
                }
        );
    }

    private boolean trySetDone(int taskNumber, boolean isDone) {
        Long taskId = getTaskIdByNumber(taskNumber);

        if (taskId == null) {
            return false;
        }

        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement("""
                     UPDATE task
                     SET is_done = ?
                     WHERE id = ? AND user_id = ?
                     """)) {

            statement.setBoolean(1, isDone);
            statement.setLong(2, taskId);
            statement.setLong(3, userId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not update task status in database", e);
        }
    }

    private Long getTaskIdByNumber(int taskNumber) {
        if (taskNumber <= 0) {
            return null;
        }

        String sql = """
                SELECT id
                FROM task
                WHERE user_id = ?
                ORDER BY id
                LIMIT 1 OFFSET ?
                """;

        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setInt(2, taskNumber - 1);

            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }

            return null;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not find task in database", e);
        }
    }

    private List<Task> getTasksByQuery(String sql, SqlParameterSetter parameterSetter) {
        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement(sql)) {

            parameterSetter.setParameters(statement);

            try (var resultSet = statement.executeQuery()) {
                return mapTasks(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not load tasks from database", e);
        }
    }

    private List<Task> mapTasks(ResultSet resultSet) throws SQLException {
        List<Task> tasks = new java.util.ArrayList<>();

        while (resultSet.next()) {
            tasks.add(mapTask(resultSet));
        }

        return tasks;
    }

    private Task mapTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();

        task.setDescription(resultSet.getString("description"));
        task.setDeadLineDate(resultSet.getDate("deadline_date").toLocalDate());
        task.setCategory(resultSet.getString("category"));
        task.setDone(resultSet.getBoolean("is_done"));
        task.setPriority(resultSet.getInt("priority"));

        return task;
    }

    @FunctionalInterface
    private interface SqlParameterSetter {
        void setParameters(PreparedStatement statement) throws SQLException;
    }
}
