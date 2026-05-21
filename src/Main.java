import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class Task {
    private final int DaysToAdd = 2;
    private final LocalDate deadLineDate;

    private String description;
    private String category;
    private boolean isDone;
    private int priority;

    Task() {
        deadLineDate = LocalDate.now().plusDays(DaysToAdd);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public LocalDate getDeadLineDate() {
        return deadLineDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

class TodoList {
    private ArrayList<Task> tasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

}

class Program {

    Scanner scanner = new Scanner(System.in);

    void main() {

        Program program = new Program();
        program.run();
    }

    public void run() {

        TodoList list = new TodoList();

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
                    addTask(list);
                    break;
                case "2":
                    viewTasks(list);
                    break;
                case "3":
                    deleteTask(list);
                    break;
                case "4":
                    markAsDone(list);
                    break;
                case "5":
                    markAsUndone(list);
                    break;
                case "6":
                    viewTasksWithSpecificPriority(list);
                    break;
                case "7":
                    viewTasks(list, false);
                    break;
                case "8":
                    viewTasks(list, true);
                case "9":
                    viewTasksWithCategory(list);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void addTask(TodoList list) {
        System.out.println("Enter a task to add to the list:");
        Task task = new Task();
        task.setDescription(scanner.nextLine());

        System.out.println("Enter priority from 1 to 3: ");

        int priority = tryParseString(scanner.nextLine());

        while (priority < 1 || priority > 3) {
            System.out.println("Enter priority from 1 to 3: ");
            priority = tryParseString(scanner.nextLine());
        }

        System.out.println("Enter category:");

        String category = scanner.nextLine();

        task.setPriority(priority);
        task.setCategory(category);
        list.addTask(task);
    }

    private void viewTasks(TodoList list) {
        var allTasks = list.getTasks();
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

    private void viewTasks(TodoList list, boolean done) {

        if (done) {
            System.out.println("Done tasks:");

            for (Task task : list.getTasks()) {

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

            for (Task task : list.getTasks()) {

                if (task.isDone()) {
                    continue;
                }

                System.out.println(task.getDescription() + " " + task.getDeadLineDate() + " " + task.getPriority() + (task.isDone() ? " (x)" : " ()"));
            }
        }
    }

    private void viewTasksWithCategory(TodoList list) {

        System.out.println("Write category:");

        String category = scanner.nextLine();

        System.out.println("Current tasks:");
        for (Task task : list.getTasks()) {

            if (task.getCategory() != null && task.getCategory().equals(category)) {
                System.out.println(task.getDescription() + " " + task.getDeadLineDate() + " " + task.getPriority() + (task.isDone() ? " (x)" : " ()"));

            }
        }
    }

    private void viewTasksWithSpecificPriority(TodoList list) {
        System.out.println("Write priority:");

        int priority = tryParseString(scanner.nextLine());

        while (priority < 1 || priority > 3) {
            System.out.println("Enter priority from 1 to 3: ");
            priority = tryParseString(scanner.nextLine());
        }

        var tasks = list.getTasks();
        ArrayList<Task> sortedTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {

            if (tasks.get(i).getPriority() == priority) {
                sortedTasks.add(tasks.get(i));
            }
        }

        for (Task task : sortedTasks) {
            System.out.println(task.getDescription() + " " + task.getDeadLineDate() + " " + task.getPriority() + (task.isDone() ? " (x)" : " ()"));
        }
    }

    private void deleteTask(TodoList list) {

        System.out.println("Enter the task number to delete:");

        int taskNumber = Integer.parseInt(scanner.nextLine());

        if (taskNumber > 0 && taskNumber <= list.getTasks().size()) {
            list.getTasks().remove(taskNumber - 1);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private void markAsDone(TodoList list) {

        System.out.println("Enter the task number to mark as done:");

        int taskNumber = Integer.parseInt(scanner.nextLine());

        if (taskNumber > 0 && taskNumber <= list.getTasks().size()) {
            Task task = list.getTasks().get(taskNumber - 1);
            task.setDone(true);
            System.out.println("Task marked as done.");
        }
    }

    private void markAsUndone(TodoList list) {

        System.out.println("Enter the task number to mark as undone:");

        int taskNumber = Integer.parseInt(scanner.nextLine());

        if (taskNumber > 0 && taskNumber <= list.getTasks().size()) {
            Task task = list.getTasks().get(taskNumber - 1);
            task.setDone(false);
            System.out.println("Task marked as undone.");
        }
    }

    private int tryParseString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}