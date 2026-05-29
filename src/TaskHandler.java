import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskHandler {
    private List<Task> tasks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getDoneTasks() {
        return tasks.stream().filter(task -> task.isDone()).toList();
    }

    public List<Task> getUndoneTasks() {
        return tasks.stream().filter(task -> !task.isDone()).toList();
    }

    public List<Task> getTasksWithCategory(String category){
        return tasks.stream().filter(task -> task.getCategory().equals(category)).toList();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

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
