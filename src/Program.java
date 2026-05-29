import java.util.Scanner;

public class Program {

    void main() {
        Program program = new Program();
        program.run();
    }

    public void run() {

        TaskHandler taskHandler = new TaskHandler();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Press number for command or 0 for exit");
            System.out.println("1. Add task");
            System.out.println("2. View tasks");
            System.out.println("3. Delete task");
            System.out.println("4. Mark task as done");
            System.out.println("5. Mark task as undone");
            System.out.println("6. View tasks with specific priority");
            System.out.println("7. View undone tasks");
            System.out.println("8. View done tasks");
            System.out.println("9. View tasks with specific category");
            System.out.println("0. Exit");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    taskHandler.addTask();
                    break;
                case "2":
                    taskHandler.viewTasks();
                    break;
                case "3":
                    taskHandler.deleteTask();
                    break;
                case "4":
                    taskHandler.markAsDone();
                    break;
                case "5":
                    taskHandler.markAsUndone();
                    break;
                case "6":
                    taskHandler.viewTasksWithSpecificPriority();
                    break;
                case "7":
                    taskHandler.viewTasks(false);
                    break;
                case "8":
                    taskHandler.viewTasks(true);
                case "9":
                    taskHandler.viewTasksWithCategory();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
