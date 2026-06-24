import java.time.LocalDate;

public class Task {

    private LocalDate deadLineDate;
    private String description;
    private String category;
    private boolean isDone;
    private int priority;

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

    public void setDeadLineDate(int daysToAdd) {
        deadLineDate = LocalDate.now().plusDays(daysToAdd);
    }

    public void setDeadLineDate(LocalDate deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return description + " " + deadLineDate + " " + priority + (isDone ? " (x)" : " ()");
    }
}
