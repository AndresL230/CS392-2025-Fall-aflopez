import java.util.function.Consumer;
import java.util.function.BiConsumer;
import Library.MyQueue.*;
import Library.MyStack.*;

public class Assign04_01<T> extends MyQueueBase<T> {
//
    /*
      HX-2025-09-24:
      Please first copy your implementation of Assign03_03
      to this class.
     */
  int nitm = -1;
  MyStackList<T> frnt = null;
  MyStackList<T> rear = null;

  public Assign04_01() {
    nitm = 0;
    frnt = new MyStackList<T>();
    rear = new MyStackList<T>();
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
    while (!rear.isEmpty()) {
      T item = rear.pop$raw();
      frnt.push$raw(item);
    }
  }

    /*
      The following four higher-order methods are declared
      in MyQueueBase<T>:

      public void foritm(Consumer<? super T> action);
      public void iforitm(BiConsumer<Integer, ? super T> action);
      public rforitm(Consumer<? super T> action);
      public irforitm(BiConsumer<Integer, ? super T> action);

      Please implement them for your two list based queue.
    */

  public void foritm(Consumer<? super T> action) {
    frnt.foritm(action);
    rear.rforitm(action);
  }

  public void iforitm(BiConsumer<Integer, ? super T> action) {
    final int[] index = {0};
    frnt.foritm(item -> {
      action.accept(index[0], item);
      index[0]++;
    });
    rear.rforitm(item -> {
      action.accept(index[0], item);
      index[0]++;
    });
  }

  public void rforitm(Consumer<? super T> action) {
    rear.foritm(action);
    frnt.rforitm(action);
  }

  public void irforitm(BiConsumer<Integer, ? super T> action) {
    final int[] index = {0};
    rear.foritm(item -> {
      action.accept(index[0], item);
      index[0]++;
    });
    frnt.rforitm(item -> {
      action.accept(index[0], item);
      index[0]++;
    });
  }

} // end of [public class Assign04_01<T> extends MyQueueBase<T>{...}]
