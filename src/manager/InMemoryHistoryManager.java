package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;
public class InMemoryHistoryManager implements HistoryManager {
    private static final int LIMIT_HISTORY_TASKS = 10;
    private List<Task> historyTasks = new ArrayList<>();
    @Override
    public void add(Task task) {
        if (task != null) {
            if (historyTasks.size() >= LIMIT_HISTORY_TASKS) {
                historyTasks.remove(0);
                historyTasks.add(task);
            } else {
                historyTasks.add(task);
            }
        } else {
            System.out.println("Такой задачи нет");
        }
    }
    @Override
    public List<Task> getHistory() {
        return historyTasks;
    }
}