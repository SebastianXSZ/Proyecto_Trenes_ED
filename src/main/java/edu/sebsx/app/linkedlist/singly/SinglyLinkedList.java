package edu.sebsx.app.linkedlist.singly;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import edu.sebsx.app.linkedlist.node.singly.LinkedNode;
import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.list.AbstractList;
import edu.sebsx.model.list.List;

public class SinglyLinkedList<E> extends AbstractList<E> {

  private transient LinkedNode<E> head;
  private transient LinkedNode<E> tail;
  private int size;

  public SinglyLinkedList() {
    this.head = null;
    this.tail = null;
    this.size = 0;
  }

  public SinglyLinkedList(E element) {
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
    LinkedNode<E> node = new LinkedNode<>(element);
    if (isEmpty()) {
      head = node;
      tail = node;
    } else {
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
    LinkedNode<E> node = new LinkedNode<>(element);
    if (isEmpty()) {
      head = node;
      tail = node;
    } else {
      node.setNext(head);
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
    E[] result = (E[]) new Object[limit];
    LinkedNode<E> current = head;
    for (int i = 0; i < limit; i++) {
      result[i] = current.get();
      current = current.getNext();
    }
    return result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] peekLastArray(int n) {
    if (isEmpty() || n <= 0) return (E[]) new Object[0];
    int limit = (n > size) ? size : n;
    LinkedNode<E> current = head;
    for (int i = 0; i < size - limit; i++) current = current.getNext();
    E[] result = (E[]) new Object[limit];
    for (int i = 0; i < limit; i++) {
      result[i] = current.get();
      current = current.getNext();
    }
    return result;
  }

  @Override
  public List<E> peekCollection(int n) {
    SinglyLinkedList<E> list = new SinglyLinkedList<>();
    list.add(peekArray(n));
    return list;
  }

  @Override
  public List<E> peekLastCollection(int n) {
    SinglyLinkedList<E> list = new SinglyLinkedList<>();
    list.add(peekLastArray(n));
    return list;
  }

  @Override
  public E poll() {
    if (isEmpty()) return null;
    E element = head.get();
    head = head.getNext();
    if (head == null) tail = null;
    size--;
    return element;
  }

  @Override
  public E pollLast() {
    if (isEmpty()) return null;
    E element = tail.get();
    if (head == tail) {
      head = null;
      tail = null;
    } else {
      LinkedNode<E> current = head;
      while (current.getNext() != tail) current = current.getNext();
      tail = current;
      tail.setNext(null);
    }
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
    if (head.get().equals(element)) {
      poll();
      return true;
    }
    LinkedNode<E> current = head;
    while (current.getNext() != null) {
      if (current.getNext().get().equals(element)) {
        if (current.getNext() == tail) tail = current;
        current.setNext(current.getNext().getNext());
        size--;
        return true;
      }
      current = current.getNext();
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
    while (head != null && filter.test(head.get())) {
      poll();
      changed = true;
    }
    if (isEmpty()) return changed;
    LinkedNode<E> current = head;
    while (current.getNext() != null) {
      if (filter.test(current.getNext().get())) {
        if (current.getNext() == tail) tail = current;
        current.setNext(current.getNext().getNext());
        size--;
        changed = true;
      } else {
        current = current.getNext();
      }
    }
    return changed;
  }

  @Override
  public boolean replace(E element, E newElement, Predicate<E> comparator) {
    LinkedNode<E> current = head;
    while (current != null) {
      if (comparator.test(current.get())) {
        current.set(newElement);
        return true;
      }
      current = current.getNext();
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
  public boolean replace(Collection<E> c1, Collection<E> c2, Predicate<E> comparator) {
    if (c1 == null || c2 == null || c1.size() != c2.size()) return false;
    Iterator<E> it1 = c1.iterator();
    Iterator<E> it2 = c2.iterator();
    boolean changed = false;
    while (it1.hasNext() && it2.hasNext()) {
      if (replace(it1.next(), it2.next(), comparator)) changed = true;
    }
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
    LinkedNode<E> current = head;
    while (current != null) {
      if (current.get().equals(index)) {
        current.set(element);
        return true;
      }
      current = current.getNext();
    }
    return false;
  }

  @Override
  public boolean sort(ToIntFunction<E> toInt) {
    if (this.size < 2) return false;
    boolean changed = false;
    for (int i = 0; i < this.size; i++) {
      LinkedNode<E> current = this.head;
      while (current != null && current.getNext() != null) {
        if (toInt.applyAsInt(current.get()) > toInt.applyAsInt(current.getNext().get())) {
          E temp = current.get();
          current.set(current.getNext().get());
          current.getNext().set(temp);
          changed = true;
        }
        current = current.getNext();
      }
    }
    return changed;
  }

  @Override
  public List<E> subList(E from, E to) {
    SinglyLinkedList<E> sub = new SinglyLinkedList<>();
    LinkedNode<E> current = head;
    boolean rec = false;
    while (current != null) {
      if (current.get().equals(from)) rec = true;
      if (rec) {
        if (current.get().equals(to)) break;
        sub.add(current.get());
      }
      current = current.getNext();
    }
    return sub;
  }

  @Override
  public boolean contains(E element) {
    LinkedNode<E> current = head;
    while (current != null) {
      if (current.get().equals(element)) return true;
      current = current.getNext();
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
    LinkedNode<E> prev = null;
    LinkedNode<E> current = head;
    tail = head;
    while (current != null) {
      LinkedNode<E> next = current.getNext();
      current.setNext(prev);
      prev = current;
      current = next;
    }
    head = prev;
    return true;
  }

  @Override
  public void forEach(Function<E, Void> action) {
    LinkedNode<E> current = head;
    while (current != null) {
      action.apply(current.get());
      current = current.getNext();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] toArray() {
    E[] array = (E[]) new Object[size];
    LinkedNode<E> current = head;
    for (int i = 0; i < size; i++) {
      array[i] = current.get();
      current = current.getNext();
    }
    return array;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      private LinkedNode<E> current = head;
      @Override public boolean hasNext() { return current != null; }
      @Override public E next() {
        E data = current.get();
        current = current.getNext();
        return data;
      }
    };
  }
}