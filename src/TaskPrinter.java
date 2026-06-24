import java.util.List;
import java.util.Scanner;

public final class TaskPrinter {

    private TaskPrinter(){
        throw new UnsupportedOperationException("Utility class");
    }

    public static void printMenu() {
        String menu = """
                Press number for command or 0 for exit
                1. Add task
                2. View tasks
                3. Delete task
                4. Mark task as done
                5. Mark task as undone
                6. View tasks with specific priority
                7. View undone tasks
                8. View done tasks
                9. View tasks with specific category
                0. Exit
                """;

        System.out.print(menu);
    }

    public static void viewAllDoneTasks(TaskHandler taskHandler){
        var tasks = taskHandler.getDoneTasks();

        if (tasks.isEmpty()) {
            System.out.println("No done tasks yet.");
            return;
        }

        viewTasks(tasks);
    }

    public static void viewAllUndoneTasks(TaskHandler taskHandler){
        var tasks = taskHandler.getUndoneTasks();

        if (tasks.isEmpty()) {
            System.out.println("No undone tasks yet.");
            return;
        }

        viewTasks(tasks);
    }

    public static void viewAllTasksWithCategory(TaskHandler taskHandler, Scanner scanner){
        System.out.println("Write category:");
        String category = scanner.nextLine();
        var tasks = taskHandler.getTasksWithCategory(category);

        if (tasks.isEmpty()) {
            System.out.println("No tasks with this category.");
            return;
        }

        viewTasks(tasks);
    }

    public static void viewAllTasks(TaskHandler taskHandler){
        var allTasks = taskHandler.getTasks();
        System.out.println("Total tasks " + allTasks.size() + ":");
        viewTasks(allTasks);
    }

    public static void viewTasksWithSpecificPriority(TaskHandler taskHandler, Scanner scanner) {
        System.out.println("Write priority:");
        int priority = StringHelper.tryParseString(scanner.nextLine());

        while (priority < 1 || priority > 3) {
            System.out.println("Enter priority from 1 to 3: ");
            priority = StringHelper.tryParseString(scanner.nextLine());
        }

        var sortedTasks = taskHandler.getTasksSortedByPriority(priority);
        for (Task task : sortedTasks) {
            System.out.println(task);
        }
    }

    public static void viewTasks(List<Task> tasks) {
        for (Task task : tasks) {
            StringBuilder taskOutput = new StringBuilder();
            if (task.getPriority() == 1) {
                taskOutput.append("Srochno bleat! ");
            }
            taskOutput
                    .append("Task: ").append(task.getDescription()).append("\n")
                    .append("Deadline: ").append(task.getDeadLineDate()).append("\n")
                    .append("Category: ").append(task.getCategory()).append("\n")
                    .append("Priority: ").append(task.getPriority()).append("\n")
                    .append("Is Done").append(task.isDone() ? " (x)" : " ()");

            System.out.println(taskOutput);
        }
    }
}
