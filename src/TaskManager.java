import java.util.Scanner;

public class TaskManager {

    private final Scanner scanner;
    private final TaskHandler taskHandler;

    public TaskManager(TaskHandler taskHandler, Scanner scanner) {
        this.taskHandler = taskHandler;
        this.scanner = scanner;
    }

    public void handleInput() {
        String input = scanner.nextLine();

        switch (input) {
            case "1" -> addTask();
            case "2" -> TaskPrinter.viewAllTasks(taskHandler);
            case "3" -> deleteTask();
            case "4" -> markAsDone();
            case "5" -> markAsUndone();
            case "6" -> TaskPrinter.viewTasksWithSpecificPriority(taskHandler, scanner);
            case "7" -> TaskPrinter.viewAllUndoneTasks(taskHandler);
            case "8" -> TaskPrinter.viewAllDoneTasks(taskHandler);
            case "9" -> TaskPrinter.viewAllTasksWithCategory(taskHandler, scanner);
            case "0" -> System.exit(0);
            default -> System.out.println("Invalid input. Please try again.");
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

}
