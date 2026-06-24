import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskHandler implements TaskHandler {

    private final List<Task> tasks = new ArrayList<>();

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<Task> getDoneTasks() {
        return tasks.stream()
                .filter(Task::isDone)
                .toList();
    }

    @Override
    public List<Task> getUndoneTasks() {
        return tasks.stream()
                .filter(task -> !task.isDone())
                .toList();
    }

    @Override
    public List<Task> getTasksWithCategory(String category) {
        return tasks.stream()
                .filter(task -> task.getCategory().equals(category))
                .toList();
    }

    @Override
    public boolean addTask(Task task) {
        tasks.add(task);
        return true;
    }

    @Override
    public boolean tryDeleteTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            tasks.remove(taskNumber - 1);
            return true;
        }

        return false;
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
        return tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .toList();
    }

    private boolean trySetDone(int taskNumber, boolean isDone) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.setDone(isDone);
            return true;
        }

        return false;
    }
}
