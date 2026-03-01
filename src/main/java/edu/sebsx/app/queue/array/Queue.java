package edu.sebsx.app.queue.array;

import java.util.function.Function;

import edu.sebsx.model.array.Array;
import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.queue.AbstractQueue;

public class Queue<E> extends AbstractQueue<E> {

  private Array<E> array;
  private int front;
  private int end;

  public Queue(int capacity) {
    this.array = new Array<>(capacity);
    this.front = 0;
    this.end = -1;
  }

  @Override
  public E peek() {
    if(isEmpty()) {
      return null;
    }
    return array.get(front);
  }

  @Override
  public E extract() {
    if(isEmpty()) {
      return null;
    }
    E element = array.get(front);
    array.remove(front);
    end--;
    return element;
  }

  @Override
  public boolean insert(E element) {
    if(end == array.size() - 1){
        return false;
    }
    array.add(element);
    end++;
    return true;
  }

  @Override
  public boolean clear() {
    return array.clear();
  }

  @Override
  public boolean contains(E element) {
    return array.contains(element);
  }

  @Override
  public boolean contains(E[] array) {
    return this.array.contains(array);
  }

  @Override
  public boolean contains(Collection<E> collection) {
    return array.contains(collection);
  }

  @Override
  public boolean isEmpty() {
    return array.isEmpty();
  }

  @Override
  public boolean reverse() {
    return array.reverse();
  }

  @Override
  public int size() {
    return array.size();
  }

  @Override
  public void forEach(Function<E, Void> action) {
    array.forEach(action);
  }

  @Override
  public Iterator<E> iterator() {
    return array.iterator();
  }
}