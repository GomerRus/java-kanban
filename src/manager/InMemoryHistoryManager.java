package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;
public class InMemoryHistoryManager implements HistoryManager {
    private static final int LIMIT_HISTORY_TASKS = 10;
    private List<Task> historyTasks = new ArrayList<>();
    @Override
    public void add(Task task) {
        if (task != null ) {
            historyTasks.add(task);
            if (historyTasks.size() > LIMIT_HISTORY_TASKS) {
                historyTasks.remove(0);
            }
        } else {
            System.out.println("Такой задачи нет");
        }
    }
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyTasks);
    }
}