package manager;

public class Managers {
    public static HistoryManager getDefaultHistory () {
        return new InMemoryHistoryManager();
    }
    public static TaskManager getInMemoryTaskManager(HistoryManager historyManager) {
        return new InMemoryTaskManager();
    }
}