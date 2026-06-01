import java.util.List;
import java.util.Scanner;

public class TaskManager {

    private final Scanner scanner = new Scanner(System.in);
    private final TaskHandler taskHandler = new TaskHandlerImpl();

    public void handleInput() {
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                addTask();
                break;
            case "2":
                viewAllTasks();
                break;
            case "3":
                deleteTask();
                break;
            case "4":
                markAsDone();
                break;
            case "5":
                markAsUndone();
                break;
            case "6":
                viewTasksWithSpecificPriority();
                break;
            case "7":
                viewAllUndoneTasks();
                break;
            case "8":
                viewAllDoneTasks();
            case "9":
                viewAllTasksWithCategory();
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid input. Please try again.");
        }
    }

    private void addTask() {
        System.out.println("Enter a task to add to the list:");
        Task task = new Task();
        task.setDescription(scanner.nextLine());

        System.out.println("Enter priority from 1 to 3: ");

        int priority = StringHelper.tryParseString(scanner.nextLine());

        while (priority < 1 || priority > 3) {
            System.out.println("Enter priority from 1 to 3: ");
            priority = StringHelper.tryParseString(scanner.nextLine());
        }

        System.out.println("Enter category:");

        String category = scanner.nextLine();

        System.out.println("Enter days deadline:");

        int daysToAdd = StringHelper.tryParseString(scanner.nextLine());

        while (daysToAdd < 0) {
            System.out.println("Enter days deadline:");
            daysToAdd = StringHelper.tryParseString(scanner.nextLine());
        }

        task.setPriority(priority);
        task.setCategory(category);
        task.setDeadLineDate(daysToAdd);
        taskHandler.addTask(task);
    }

    private void viewAllDoneTasks(){
        var tasks = taskHandler.getDoneTasks();

        if (tasks.isEmpty()) {
            System.out.println("No done tasks yet.");
            return;
        }

        viewTasks(tasks);
    }

    private void viewAllUndoneTasks(){
        var tasks = taskHandler.getUndoneTasks();

        if (tasks.isEmpty()) {
            System.out.println("No undone tasks yet.");
            return;
        }

        viewTasks(tasks);
    }

    private void viewAllTasksWithCategory(){
        System.out.println("Write category:");
        String category = scanner.nextLine();
        var tasks = taskHandler.getTasksWithCategory(category);

        if (tasks.isEmpty()) {
            System.out.println("No tasks with this category.");
            return;
        }

        viewTasks(tasks);
    }

    private void viewAllTasks(){
        var allTasks = taskHandler.getTasks();
        System.out.println("Total tasks " + allTasks.size() + ":");
        viewTasks(allTasks);
    }

    private void viewTasks(List<Task> tasks) {
        for (Task task : tasks) {
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

    private void deleteTask() {

        System.out.println("Enter the task number to delete:");

        int taskNumber = StringHelper.tryParseString(scanner.nextLine());

        if (taskHandler.tryDeleteTask(taskNumber)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private void markAsDone() {

        System.out.println("Enter the task number to mark as done:");

        int taskNumber = StringHelper.tryParseString(scanner.nextLine());

        if (taskHandler.tryMarkAsDone(taskNumber)) {
            System.out.println("Task marked as done.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private void markAsUndone() {

        System.out.println("Enter the task number to mark as undone:");

        int taskNumber = StringHelper.tryParseString(scanner.nextLine());

        if (taskHandler.tryMarkAsUnDone(taskNumber)) {
            System.out.println("Task marked as undone.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private void viewTasksWithSpecificPriority() {
        System.out.println("Write priority:");

        int priority = StringHelper.tryParseString(scanner.nextLine());

        while (priority < 1 || priority > 3) {
            System.out.println("Enter priority from 1 to 3: ");
            priority = StringHelper.tryParseString(scanner.nextLine());
        }

        var sortedTasks = taskHandler.getTasksSortedByPriority(priority);

        for (Task task : sortedTasks) {
            System.out.println(task.getDescription() + " " + task.getDeadLineDate() + " " + task.getPriority() + (task.isDone() ? " (x)" : " ()"));
        }
    }
}
