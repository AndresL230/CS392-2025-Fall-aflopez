package Library.MyPQueue;

import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class MyPQueueArray<T> extends MyPQueueBase<T> {

    int nitm = -1;
    T[] itms = null;
    int[] prio = null;

    public MyPQueueArray(int cap) {
        assert (cap >= 1);
        nitm = 0;
        @SuppressWarnings("unchecked")
        T[] tempArray = (T[]) new Object[cap];
        itms = tempArray;
        prio = new int[cap];
    }

    @Override
    public int size() {
        return nitm;
    }

    @Override
    public boolean isFull() {
        return (nitm >= itms.length);
    }

    @Override
    public T top$raw() {
        return itms[0];
    }

    @Override
    public T deque$raw() {
        T result = itms[0];
        nitm -= 1;
        if (nitm > 0) {
            itms[0] = itms[nitm];
            prio[0] = prio[nitm];
            heapifyDown(0);
        }
        return result;
    }

    @Override
    public void enque$raw(T itm) {
        itms[nitm] = itm;
        int priority = getPriority(itm);
        prio[nitm] = priority;
        heapifyUp(nitm);
        nitm += 1;
    }

    private int getPriority(T itm) {
        try {
            java.lang.reflect.Method method = itm.getClass().getMethod("priority");
            Object result = method.invoke(itm);
            return (Integer) result;
        } catch (Exception e) {
            return 0;
        }
    }

    private void heapifyUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (prio[i] < prio[parent]) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int i) {
        while (true) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int smallest = i;
            if (leftChild < nitm && prio[leftChild] < prio[smallest]) {
                smallest = leftChild;
            }
            if (rightChild < nitm && prio[rightChild] < prio[smallest]) {
                smallest = rightChild;
            }
            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T tempItem = itms[i];
        int tempPrio = prio[i];
        itms[i] = itms[j];
        prio[i] = prio[j];
        itms[j] = tempItem;
        prio[j] = tempPrio;
    }
}
