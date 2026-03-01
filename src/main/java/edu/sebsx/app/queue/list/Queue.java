package edu.sebsx.app.queue.list;

import java.util.function.Function;

import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.queue.AbstractQueue;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

public class Queue<E> extends AbstractQueue<E> {

  private SinglyLinkedList<E> list;

  public Queue(){
    this.list = new SinglyLinkedList<>();
  }

  @Override
  public E peek(){
    return list.peek();
  }

  @Override
  public E extract(){
    return list.poll();
  }

  @Override
  public boolean insert (E element){
    return list.add(element);
  }

  @Override
  public boolean clear() {
    return list.clear();
  }

  @Override
  public boolean contains(E element) {
    return list.contains(element);
  }

  @Override
  public boolean contains(E[] array) {
    return list.contains(array);
  }

  @Override
  public boolean contains(Collection<E> collection) {
    return list.contains(collection);
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean reverse() {
    return list.reverse();
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public void forEach(Function<E, Void> action) {
    list.forEach(action);
  }

  @Override
  public Iterator<E> iterator() {
    return list.iterator();
  }
}