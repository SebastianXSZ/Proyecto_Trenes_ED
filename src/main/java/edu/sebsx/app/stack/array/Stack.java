package edu.sebsx.app.stack.array;

import java.util.function.Function;

import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.stack.AbstractStack;
import edu.sebsx.model.array.Array;

public class Stack<E> extends AbstractStack<E> {

  private Array<E> array;
  private int top;

  public Stack(int capacity) {
    this.array = new Array<>(capacity);
    this.top = -1;
  }

  @Override
  public E peek() {
    if(isEmpty()) {
      return null;
    }
    return array.get(top);
  }

  @Override
  public E pop() {
    if(isEmpty()) {
      return null;
    }
    E element = array.get(top);
    array.remove(top);
    top--;
    return element;
  }

  @Override
  public boolean push(E element) {
    if(top == array.size() - 1) {
      return false;
    }
    array.add(element);
    top++;
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