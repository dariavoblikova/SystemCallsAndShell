import java.util.*;

// Implementation of monitor waiting for specific condition using SyncQueue
public class SyncQueue {

    private QueueNode[] condition_numbers_queue;
    private static int DEFAULT_ARRAY_SIZE = 10;
    private static int DEFAULT_TID = 0;

    public SyncQueue () {
        condition_numbers_queue = new QueueNode[DEFAULT_ARRAY_SIZE];
        for(int i = 0; i < DEFAULT_ARRAY_SIZE; i++) {
            condition_numbers_queue[i] = new QueueNode();
        }
    }

    public SyncQueue (int condMax) {
        condition_numbers_queue = new QueueNode[condMax];
        for(int i = 0; i < condMax; i++) {
            condition_numbers_queue[i] = new QueueNode();
        }
    }

    public int enqueueAndSleep(int condition) {
        return condition_numbers_queue[condition].sleep(); // returns child's tid that has woken the calling thread.
    }

    public void dequeueAndWakeup(int condition, int tid) {
        condition_numbers_queue[condition].wakeup(tid);
    }

    public void dequeueAndWakeup(int condition) {
        condition_numbers_queue[condition].wakeup(DEFAULT_TID); // If no 2nd argument is given for dequeueAndWakeup(), tid is 0

    }
}
