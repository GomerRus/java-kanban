package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static class CustomLinkedList {
        private final Map<Integer, Node> history = new HashMap<>();
        private Node head;
        private Node tail;

        private void linkLast(Task task) {
            Node node = new Node();
            node.setTask(task);
            tail = node;
            if (history.containsKey(task.getId())) {
                removeNode(history.get(task.getId()));
            }
            if (head == null) {
                head = node;
            } else {
                node.setPrev(tail);
            }
            history.put(task.getId(), node);
        }

        private List<Task> getTasks() {
            List<Task> result = new ArrayList<>();
            Node element = head;
            while (element != null) {
                result.add(element.getTask());
                element = element.getNext();
            }
            return result;
        }

        private void removeNode(Node node) {
            if (node != null && head != null) {
                history.remove(node.getTask().getId());
                Node prev = node.getPrev();
                Node next = node.getNext();

                if (head == node) {
                    head = node.getNext();
                    if (head != null) {
                        node.getNext().setPrev(null);
                    }
                }
                if (tail == node) {
                    tail = node.getPrev();
                    if (tail != null) {
                        node.getPrev().setNext(null);
                    }
                }
                if (prev != null) {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrev(prev);
                }
            }
        }

        private Node getNode(int id) {
            return history.get(id);
        }
    }

    private final CustomLinkedList list = new CustomLinkedList();

    @Override
    public void add(Task task) {
        list.linkLast(task);
    }

    @Override
    public void remove(int id) {

        list.removeNode(list.getNode(id));
    }

    @Override
    public List<Task> getHistory() {
        return list.getTasks();
    }
}
