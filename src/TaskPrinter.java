public final class TaskPrinter {

    private TaskPrinter(){
        throw new UnsupportedOperationException("Utility class");
    }

    public static void printMenu() {

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
    }
}
