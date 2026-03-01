package edu.sebsx.app.linkedlist.doubly;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import edu.sebsx.app.linkedlist.node.doubly.DoubleLinkedNode;
import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.list.AbstractList;
import edu.sebsx.model.list.List;

public class DoublyLinkedList<E> extends AbstractList<E> {

  private transient DoubleLinkedNode<E> head;
  private transient DoubleLinkedNode<E> tail;
  private int size;

  public DoublyLinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  public DoublyLinkedList(E element) {
    this();
    add(element);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean clear() {
    head = null;
    tail = null;
    size = 0;
    return true;
  }

  @Override
  public boolean add(E element) {
    DoubleLinkedNode<E> node = new DoubleLinkedNode<>(element);
    if (isEmpty()) {
      head = node;
      tail = node;
    } else {
      node.setPrevious(tail);
      tail.setNext(node);
      tail = node;
    }
    size++;
    return true;
  }

  @Override
  public boolean add(E[] array) {
    if (array == null) return false;
    for (int i = 0; i < array.length; i++) add(array[i]);
    return true;
  }

  @Override
  public boolean add(Collection<E> collection) {
    if (collection == null) return false;
    Iterator<E> it = collection.iterator();
    while (it.hasNext()) add(it.next());
    return true;
  }

  @Override
  public boolean addFirst(E element) {
    DoubleLinkedNode<E> node = new DoubleLinkedNode<>(element);
    if (isEmpty()) {
      head = node;
      tail = node;
    } else {
      node.setNext(head);
      head.setPrevious(node);
      head = node;
    }
    size++;
    return true;
  }

  @Override
  public boolean addFirst(E[] array) {
    if (array == null) return false;
    for (int i = array.length - 1; i >= 0; i--) addFirst(array[i]);
    return true;
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean addFirst(Collection<E> collection) {
    if (collection == null) return false;
    E[] temp = (E[]) new Object[collection.size()];
    Iterator<E> it = collection.iterator();
    int i = 0;
    while (it.hasNext()) temp[i++] = it.next();
    return addFirst(temp);
  }

  @Override
  public E peek() {
    return isEmpty() ? null : head.get();
  }

  @Override
  public E peekLast() {
    return isEmpty() ? null : tail.get();
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] peekArray(int n) {
    if (isEmpty() || n <= 0) return (E[]) new Object[0];
    int limit = (n > size) ? size : n;
    E[] res = (E[]) new Object[limit];
    DoubleLinkedNode<E> curr = head;
    for (int i = 0; i < limit; i++) {
      res[i] = curr.get();
      curr = curr.getNext();
    }
    return res;
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] peekLastArray(int n) {
    if (isEmpty() || n <= 0) return (E[]) new Object[0];
    int limit = (n > size) ? size : n;
    E[] res = (E[]) new Object[limit];
    DoubleLinkedNode<E> curr = tail;
    for (int i = limit - 1; i >= 0; i--) {
      res[i] = curr.get();
      curr = curr.getPrevious();
    }
    return res;
  }

  @Override
  public List<E> peekCollection(int n) {
    DoublyLinkedList<E> list = new DoublyLinkedList<>();
    list.add(peekArray(n));
    return list;
  }

  @Override
  public List<E> peekLastCollection(int n) {
    DoublyLinkedList<E> list = new DoublyLinkedList<>();
    list.add(peekLastArray(n));
    return list;
  }

  @Override
  public E poll() {
    if (isEmpty()) return null;
    E element = head.get();
    head = head.getNext();
    if (head == null) tail = null;
    else head.setPrevious(null);
    size--;
    return element;
  }

  @Override
  public E pollLast() {
    if (isEmpty()) return null;
    E element = tail.get();
    tail = tail.getPrevious();
    if (tail == null) head = null;
    else tail.setNext(null);
    size--;
    return element;
  }

  @Override
  public E[] pollArray(int n) {
    E[] data = peekArray(n);
    for (int i = 0; i < data.length; i++) poll();
    return data;
  }

  @Override
  public E[] pollLastArray(int n) {
    E[] data = peekLastArray(n);
    for (int i = 0; i < data.length; i++) pollLast();
    return data;
  }

  @Override
  public List<E> pollCollection(int n) {
    List<E> list = peekCollection(n);
    for (int i = 0; i < list.size(); i++) poll();
    return list;
  }

  @Override
  public List<E> pollLastCollection(int n) {
    List<E> list = peekLastCollection(n);
    for (int i = 0; i < list.size(); i++) pollLast();
    return list;
  }

  @Override
  public boolean remove(E element) {
    if (isEmpty()) return false;
    DoubleLinkedNode<E> curr = head;
    while (curr != null) {
      if (curr.get().equals(element)) {
        if (curr == head) poll();
        else if (curr == tail) pollLast();
        else {
          curr.getPrevious().setNext(curr.getNext());
          curr.getNext().setPrevious(curr.getPrevious());
          size--;
        }
        return true;
      }
      curr = curr.getNext();
    }
    return false;
  }

  @Override
  public boolean remove(E[] array) {
    if (array == null) return false;
    boolean changed = false;
    for (int i = 0; i < array.length; i++) if (remove(array[i])) changed = true;
    return changed;
  }

  @Override
  public boolean remove(Collection<E> collection) {
    if (collection == null) return false;
    boolean changed = false;
    Iterator<E> it = collection.iterator();
    while (it.hasNext()) if (remove(it.next())) changed = true;
    return changed;
  }

  @Override
  public boolean remove(Predicate<E> filter) {
    boolean changed = false;
    DoubleLinkedNode<E> curr = head;
    while (curr != null) {
      DoubleLinkedNode<E> next = curr.getNext();
      if (filter.test(curr.get())) {
        if (curr == head) poll();
        else if (curr == tail) pollLast();
        else {
          curr.getPrevious().setNext(curr.getNext());
          curr.getNext().setPrevious(curr.getPrevious());
          size--;
        }
        changed = true;
      }
      curr = next;
    }
    return changed;
  }

  @Override
  public boolean replace(E element, E newElement, Predicate<E> comparator) {
    DoubleLinkedNode<E> curr = head;
    while (curr != null) {
      if (comparator.test(curr.get())) {
        curr.set(newElement);
        return true;
      }
      curr = curr.getNext();
    }
    return false;
  }

  @Override
  public boolean replace(E[] array, E[] newArray, Predicate<E> comparator) {
    if (array == null || newArray == null || array.length != newArray.length) return false;
    boolean changed = false;
    for (int i = 0; i < array.length; i++) if (replace(array[i], newArray[i], comparator)) changed = true;
    return changed;
  }

  @Override
  public boolean replace(Collection<E> c, Collection<E> nc, Predicate<E> comp) {
    if (c == null || nc == null) return false;
    Iterator<E> itO = c.iterator();
    Iterator<E> itN = nc.iterator();
    boolean changed = false;
    while (itO.hasNext() && itN.hasNext()) if (replace(itO.next(), itN.next(), comp)) changed = true;
    return changed;
  }

  @Override
  public boolean retain(E[] array) {
    if (array == null) return false;
    return remove(item -> {
      for (int i = 0; i < array.length; i++) if (array[i].equals(item)) return false;
      return true;
    });
  }

  @Override
  public boolean retain(Collection<E> collection) {
    if (collection == null) return false;
    return remove(item -> !collection.contains(item));
  }

  @Override
  public boolean set(E index, E element) {
    DoubleLinkedNode<E> curr = head;
    while (curr != null) {
      if (curr.get().equals(index)) {
        curr.set(element);
        return true;
      }
      curr = curr.getNext();
    }
    return false;
  }

  @Override
  public boolean sort(ToIntFunction<E> toInt) {
    if (size < 2) return false;
    boolean changed = false;
    boolean swapped;
    do {
      swapped = false;
      DoubleLinkedNode<E> curr = head;
      while (curr != null && curr.getNext() != null) {
        if (toInt.applyAsInt(curr.get()) > toInt.applyAsInt(curr.getNext().get())) {
          E temp = curr.get();
          curr.set(curr.getNext().get());
          curr.getNext().set(temp);
          swapped = true;
          changed = true;
        }
        curr = curr.getNext();
      }
    } while (swapped);
    return changed;
  }

  @Override
  public List<E> subList(E from, E to) {
    DoublyLinkedList<E> sub = new DoublyLinkedList<>();
    DoubleLinkedNode<E> curr = head;
    boolean active = false;
    while (curr != null) {
      if (curr.get().equals(from)) active = true;
      if (active) {
        if (curr.get().equals(to)) break;
        sub.add(curr.get());
      }
      curr = curr.getNext();
    }
    return sub;
  }

  @Override
  public boolean contains(E element) {
    DoubleLinkedNode<E> curr = head;
    while (curr != null) {
      if (curr.get().equals(element)) return true;
      curr = curr.getNext();
    }
    return false;
  }

  @Override
  public boolean contains(E[] array) {
    if (array == null) return false;
    for (int i = 0; i < array.length; i++) if (!contains(array[i])) return false;
    return true;
  }

  @Override
  public boolean contains(Collection<E> collection) {
    if (collection == null) return false;
    Iterator<E> it = collection.iterator();
    while (it.hasNext()) if (!contains(it.next())) return false;
    return true;
  }

  @Override
  public boolean reverse() {
    if (size < 2) return false;
    DoubleLinkedNode<E> curr = head;
    DoubleLinkedNode<E> temp = null;
    tail = head;
    while (curr != null) {
      temp = curr.getPrevious();
      curr.setPrevious(curr.getNext());
      curr.setNext(temp);
      head = curr;
      curr = curr.getPrevious();
    }
    return true;
  }

  @Override
  public void forEach(Function<E, Void> action) {
    DoubleLinkedNode<E> curr = head;
    while (curr != null) {
      action.apply(curr.get());
      curr = curr.getNext();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] toArray() {
    E[] array = (E[]) new Object[size];
    DoubleLinkedNode<E> curr = head;
    for (int i = 0; i < size; i++) {
      array[i] = curr.get();
      curr = curr.getNext();
    }
    return array;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      private DoubleLinkedNode<E> curr = head;
      @Override public boolean hasNext() { return curr != null; }
      @Override public E next() {
        E data = curr.get();
        curr = curr.getNext();
        return data;
      }
    };
  }
}