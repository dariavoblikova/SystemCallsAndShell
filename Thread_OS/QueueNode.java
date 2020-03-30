import java.util.*;

public class QueueNode {
    private Vector<Integer> child_tids_queue;

    public QueueNode () {
        child_tids_queue = new Vector<Integer>();
    }

    public synchronized int sleep () {
        if (child_tids_queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                SysLib.cerr( "Failed to wait in QueueNode sleep()\n" );
            }
            return child_tids_queue.remove(0);
        }
        return -1;
    }

    public synchronized void wakeup(int tid) {
        child_tids_queue.add(tid);
        notify();
    }
   
}
