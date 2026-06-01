public class Program {

    //private final TaskHandler taskHandler = new TaskHandler();

    void main() {
        Program program = new Program();
        program.run();
    }

    private void run() {

        var taskManager = new TaskManager();

        while (true) {

            TaskPrinter.printMenu();
            taskManager.handleInput();
        }
    }
}
