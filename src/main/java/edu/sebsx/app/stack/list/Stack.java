package edu.sebsx.app.stack.list;

import java.util.function.Function;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.stack.AbstractStack;

public class Stack<E> extends AbstractStack<E> {

  private SinglyLinkedList<E> list;

  public Stack() {
    this.list = new SinglyLinkedList<>();
  }

  @Override
  public E peek() {
    return list.peekLast();
  }

  @Override
  public E pop() {
    return list.pollLast();
  }

  @Override
  public boolean push(E element) {
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