import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskHandler {
    private List<Task> tasks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private StringHelper stringHelper = new StringHelper();

    public List<Task> getTasks() {
        return tasks;
    }

    private void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addTask() {
        System.out.println("Enter a task to add to the list:");
        Task task = new Task();
        task.setDescription(scanner.nextLine());

        System.out.println("Enter priority from 1 to 3: ");

        int priority = stringHelper.tryParseString(scanner.nextLine());

        while (priority < 1 || priority > 3) {
            System.out.println("Enter priority from 1 to 3: ");
            priority = stringHelper.tryParseString(scanner.nextLine());
        }

        System.out.println("Enter category:");

        String category = scanner.nextLine();

        System.out.println("Enter days deadline:");

        int daysToAdd = stringHelper.tryParseString(scanner.nextLine());

        while (daysToAdd < 0) {
            System.out.println("Enter days deadline:");
            daysToAdd = stringHelper.tryParseString(scanner.nextLine());
        }

        task.setPriority(priority);
        task.setCategory(category);
        task.setDeadLineDate(daysToAdd);
        addTask(task);
    }

    public void viewTasks() {
        var allTasks = getTasks();
        System.out.println("Total tasks " + allTasks.size() + ":");
        for (Task task : allTasks) {
            System.out.println(
                    (task.getPriority() == 1 ? "Srochno bleat! " : "") +
                            "Task: " + task.getDescription() + "\n" +
                            "Deadline: " + task.getDeadLineDate() + "\n" +
                            "Category: " + task.getCategory() + "\n" +
                            "Priority: " + task.getPriority() + "\n" +
                            "Is Done" + (task.isDone() ? " (x)" : " ()")
            );
        }
    }

    public void viewTasks(boolean done) {

        if (done) {
            System.out.println("Done tasks:");

            for (Task task : getTasks()) {

                if (!task.isDone()) {
                    continue;
                }

                System.out.println("Task: " + task.getDescription() + "\n" +
                        "Deadline: " + task.getDeadLineDate() + "\n" +
                        "Priority: " + task.getPriority() + "\n" +
                        "Is Done" + (task.isDone() ? " (x)" : " ()"));
            }
        } else {
            System.out.println("Undone tasks:");

            for (Task task : getTasks()) {

                if (task.isDone()) {
                    continue;
                }

                System.out.println(task.getDescription() + " " + task.getDeadLineDate() + " " + task.getPriority() + (task.isDone() ? " (x)" : " ()"));
            }
        }
    }

    public void viewTasksWithCategory() {

        System.out.println("Write category:");

        String category = scanner.nextLine();

        System.out.println("Current tasks:");
        for (Task task : getTasks()) {

            if (task.getCategory() != null && task.getCategory().equals(category)) {
                System.out.println(task.getDescription() + " " + task.getDeadLineDate() + " " + task.getPriority() + (task.isDone() ? " (x)" : " ()"));

            }
        }
    }

    public void viewTasksWithSpecificPriority() {
        System.out.println("Write priority:");

        int priority = stringHelper.tryParseString(scanner.nextLine());

        while (priority < 1 || priority > 3) {
            System.out.println("Enter priority from 1 to 3: ");
            priority = stringHelper.tryParseString(scanner.nextLine());
        }

        var tasks = getTasks();
        ArrayList<Task> sortedTasks = new ArrayList<>();

        for (Task value : tasks) {

            if (value.getPriority() == priority) {
                sortedTasks.add(value);
            }
        }

        for (Task task : sortedTasks) {
            System.out.println(task.getDescription() + " " + task.getDeadLineDate() + " " + task.getPriority() + (task.isDone() ? " (x)" : " ()"));
        }
    }

    public void deleteTask() {

        System.out.println("Enter the task number to delete:");

        int taskNumber = stringHelper.tryParseString(scanner.nextLine());

        if (taskNumber > 0 && taskNumber <= getTasks().size()) {
            getTasks().remove(taskNumber - 1);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void markAsDone() {

        System.out.println("Enter the task number to mark as done:");

        int taskNumber = stringHelper.tryParseString(scanner.nextLine());

        if (taskNumber > 0 && taskNumber <= getTasks().size()) {
            Task task = getTasks().get(taskNumber - 1);
            task.setDone(true);
            System.out.println("Task marked as done.");
        }
    }

    public void markAsUndone() {

        System.out.println("Enter the task number to mark as undone:");

        int taskNumber = stringHelper.tryParseString(scanner.nextLine());

        if (taskNumber > 0 && taskNumber <= getTasks().size()) {
            Task task = getTasks().get(taskNumber - 1);
            task.setDone(false);
            System.out.println("Task marked as undone.");
        }
    }

}
