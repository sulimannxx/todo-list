import java.util.Scanner;

public class Program {

    private final Scanner scanner = new Scanner(System.in);

    void main() {
        Program program = new Program();
        program.run();
    }

    private void run() {
        TaskHandler taskHandler = chooseTaskHandler();
        var taskManager = new TaskManager(taskHandler, scanner);

        while (true) {

            TaskPrinter.printMenu();
            taskManager.handleInput();
        }
    }

    private TaskHandler chooseTaskHandler() {
        System.out.println("Store tasks in database? Write y or n:");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
            System.out.println("Write y or n:");
            input = scanner.nextLine();
        }

        if (input.equalsIgnoreCase("y")) {
            return new DatabaseTaskHandler(new UserService());
        }

        return new InMemoryTaskHandler();
    }
}