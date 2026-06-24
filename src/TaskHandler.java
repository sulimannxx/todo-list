import java.util.List;

public interface TaskHandler {

    List<Task> getTasks();

    List<Task> getDoneTasks();

    List<Task> getUndoneTasks();

    List<Task> getTasksWithCategory(String category);

    List<Task> getTasksSortedByPriority(int priority);

    boolean addTask(Task task);

    boolean tryDeleteTask(int taskNumber);

    boolean tryMarkAsDone(int taskNumber);

    boolean tryMarkAsUnDone(int taskNumber);
}
