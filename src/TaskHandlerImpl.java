import java.util.ArrayList;
import java.util.List;

public class TaskHandlerImpl implements TaskHandler {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<Task> getDoneTasks() {
        return tasks.stream().filter(task -> task.isDone()).toList();
    }

    @Override
    public List<Task> getUndoneTasks() {
        return tasks.stream().filter(task -> !task.isDone()).toList();
    }

    @Override
    public List<Task> getTasksWithCategory(String category) {
        return tasks.stream().filter(task -> task.getCategory().equals(category)).toList();
    }

    @Override
    public boolean addTask(Task task) {
        this.tasks.add(task);
        return true;
    }

    @Override
    public boolean tryDeleteTask(int taskNumber) {

        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            tasks.remove(taskNumber - 1);
            return true;
        } else {
            return false;
        }
    }

    public boolean tryMarkAsDone(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.setDone(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean tryMarkAsUnDone(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.setDone(false);
            return true;
        } else {
            return false;
        }
    }

    public List<Task> getTasksSortedByPriority(int priority) {
        List<Task> sortedTasks = new ArrayList<>(tasks);

        for (Task value : tasks) {

            if (value.getPriority() == priority) {
                sortedTasks.add(value);
            }
        }

        return sortedTasks;
    }
}
