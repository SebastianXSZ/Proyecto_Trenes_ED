package edu.sebsx.app.queue.array;

import java.util.function.Function;

import edu.sebsx.app.array.Array;
import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.queue.AbstractQueue;

public class Queue<E> extends AbstractQueue<E> {

  private Array<E> array;
  private int head;
  private int tail;

  public Queue(int capacity) {
    this.array = new Array<>(capacity);
    this.head = 0;
    this.tail = -1;
  }

  @Override
  public E peek() {
    if(isEmpty()) {
      return null;
    }
    return array.get(head);
  }

  @Override
  public E extract() {
    if(isEmpty()) {
      return null;
    }
    E element = array.get(head);
    if(head == array.size() - 1) {
      array.remove(head);
      head = 0;
    } else {
      head++;
    }
    tail--;
    return element;
  }

  @Override
  public boolean insert(E element) {
    if (isEmpty()) {
      array.add(element);
      tail++;
      return true;
    }
    if(tail == array.size() - 1 && head > 0){
      tail = 0;
      array.add(element);
      tail++;     
    } else if(tail < array.size() - 1) {
      array.add(element);
      tail++;
    }
    return false;
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