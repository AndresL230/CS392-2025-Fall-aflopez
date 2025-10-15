import Library.MyStack.*;

public class Assign03_03<T> extends MyQueueBase<T> {

    int nitm = -1;
    FnList<T> frnt = null;
    FnList<T> rear = null;

    public Assign03_03() {
		nitm = 0;
		frnt = new FnList<T>();
		rear = new FnList<T>();
    }

    public int size() {
		return nitm;
    }

    public boolean isFull() {
		return false;
    }

    public T top$raw() {
		if (frnt.isEmpty() && !rear.isEmpty()) {
			transferRearToFront();
		}
		return frnt.top$raw();
    }

    public T deque$raw() {
		if (frnt.isEmpty() && !rear.isEmpty()) {
			transferRearToFront();
		}
		T item = frnt.pop$raw();
		nitm -= 1;
		return item;
    }

    public void enque$raw(T itm) {
		rear.push$raw(itm);
		nitm += 1;
    }
    
    private void transferRearToFront() {
		while (!rea1r.isEmpty()) {
			T item = rear.pop$raw();
			frnt.push$raw(item);
		}
    }
}