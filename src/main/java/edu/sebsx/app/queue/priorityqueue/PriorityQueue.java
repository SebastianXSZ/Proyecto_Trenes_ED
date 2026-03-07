package edu.sebsx.app.queue.priorityqueue;

import java.util.function.Function;

import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.queue.AbstractPriorityQueue;
import edu.sebsx.app.queue.list.Queue;
import edu.sebsx.app.array.Array;

public class PriorityQueue<E> extends AbstractPriorityQueue<E> {

  private Array<Queue<E>> priorities;
  private int size;

  public PriorityQueue(int prio) {
    this.priorities = new Array<>(prio);
    for (int i = 0; i < prio; i++) {
      this.priorities.add(new Queue<>());
    }
    this.size = 0;
  }

  @Override
  public boolean insert(E element) {
    return insert(priorities.size() - 1, element);
  }

  @Override
  public boolean insert(int index, E element) {
    if (index < 0 || index >= priorities.size()) return false;
    Queue<E> col = priorities.get(index);
    if (col == null) return false;
    boolean added = col.insert(element);
    if (added) size++;
    return added;
  }

  @Override
  public E peek() {
    Queue<E> col = priorities.get(0);
    if (col != null && !col.isEmpty()) return col.peek();
    return null;
  }

  @Override
  public E extract() {
    Queue<E> col = priorities.get(0);
    if (col != null && !col.isEmpty()) {
      size--;
      return col.extract();
    }
    return null;
  }

  @Override
  public boolean clear() {
    for (int i = 0; i < priorities.size(); i++) {
      Queue<E> col = priorities.get(i);
      col.clear();
    }
    size = 0;
    return true;
  }

  @Override
  public boolean contains(E element) {
    for (int i = 0; i < priorities.size(); i++) {
      Queue<E> col = priorities.get(i);
      if (col != null && col.contains(element)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean contains(E[] array) {
    if (array == null) return false;
    for (E e : array) {
      if (!contains(e)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean contains(Collection<E> collection) {
    if (collection == null) return false;
    Iterator<E> it = collection.iterator();
    while (it.hasNext()) {
      if (!contains(it.next())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean reverse() {
    if (size < 2) {
      return false;
    }
    for (int i = 0; i < priorities.size(); i++) {
      Queue<E> col = priorities.get(i);
      if (col != null) {
        col.reverse();
      }
    }
    int n = priorities.size();
    for (int i = 0; i < n / 2; i++) {
      Queue<E> tmp = priorities.get(i);
      priorities.set(i, priorities.get(n - 1 - i));
      priorities.set(n - 1 - i, tmp);
    }
    return true;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void forEach(Function<E, Void> action) {
    for (int i = 0; i < priorities.size(); i++) {
      Queue<E> col = priorities.get(i);
      if (col != null) {
        col.forEach(action);
      }
    }
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      private int priIndex = 0;
      private Iterator<E> current = null;

      private void advance() {
        while ((current == null || !current.hasNext()) && priIndex < priorities.size()) {
          Queue<E> col = priorities.get(priIndex++);
          if (col != null) {
            current = col.iterator();
          }
        }
      }

      @Override
      public boolean hasNext() {
        advance();
        return current != null && current.hasNext();
      }

      @Override
      public E next() {
        advance();
        return current != null ? current.next() : null;
      }
    };
  }
}