package edu.sebsx.app.linkedlist.circular.singly;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import edu.sebsx.app.linkedlist.node.singly.LinkedNode;
import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.list.AbstractList;
import edu.sebsx.model.list.List;

public class SinglyCircularLinkedList<E> extends AbstractList<E> {

  private transient LinkedNode<E> head;
  private transient LinkedNode<E> tail;
  private int size;

  public SinglyCircularLinkedList() {
    this.head = null;
    this.tail = null;
    this.size = 0;
  }

  public SinglyCircularLinkedList(E element) {
    this();
    this.add(element);
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public boolean isEmpty() {
    return this.size == 0;
  }

  @Override
  public boolean clear() {
    this.head = null;
    this.tail = null;
    this.size = 0;
    return true;
  }

  @Override
  public boolean add(E element) {
    LinkedNode<E> node = new LinkedNode<>(element);
    if (this.isEmpty()) {
      this.head = node;
      this.tail = node;
      this.tail.setNext(this.head);
    } else {
      this.tail.setNext(node);
      this.tail = node;
      this.tail.setNext(this.head);
    }
    this.size++;
    return true;
  }

  @Override
  public boolean add(E[] array) {
    if (array == null) return false;
    for (int i = 0; i < array.length; i++) this.add(array[i]);
    return true;
  }

  @Override
  public boolean add(Collection<E> collection) {
    if (collection == null) return false;
    Iterator<E> it = collection.iterator();
    while (it.hasNext()) this.add(it.next());
    return true;
  }

  @Override
  public boolean addFirst(E element) {
    LinkedNode<E> node = new LinkedNode<>(element);
    if (this.isEmpty()) {
      this.head = node;
      this.tail = node;
      this.tail.setNext(this.head);
    } else {
      node.setNext(this.head);
      this.head = node;
      this.tail.setNext(this.head);
    }
    this.size++;
    return true;
  }

  @Override
  public boolean addFirst(E[] array) {
    if (array == null) return false;
    for (int i = array.length - 1; i >= 0; i--) this.addFirst(array[i]);
    return true;
  }

  @Override
  public boolean addFirst(Collection<E> collection) {
    if (collection == null) return false;
    @SuppressWarnings("unchecked")
    E[] temp = (E[]) new Object[collection.size()];
    Iterator<E> it = collection.iterator();
    int idx = 0;
    while (it.hasNext()) temp[idx++] = it.next();
    return this.addFirst(temp);
  }

  @Override
  public E peek() {
    if (this.isEmpty()) return null;
    return this.head.get();
  }

  @Override
  public E peekLast() {
    if (this.isEmpty()) return null;
    return this.tail.get();
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] peekArray(int n) {
    if (this.isEmpty() || n <= 0) return (E[]) new Object[0];
    int limit = (n > this.size) ? this.size : n;
    E[] res = (E[]) new Object[limit];
    LinkedNode<E> curr = this.head;
    for (int i = 0; i < limit; i++) {
      res[i] = curr.get();
      curr = curr.getNext();
    }
    return res;
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] peekLastArray(int n) {
    if (this.isEmpty() || n <= 0) return (E[]) new Object[0];
    int limit = (n > this.size) ? this.size : n;
    int skip = this.size - limit;
    LinkedNode<E> curr = this.head;
    for (int i = 0; i < skip; i++) curr = curr.getNext();
    E[] res = (E[]) new Object[limit];
    for (int i = 0; i < limit; i++) {
      res[i] = curr.get();
      curr = curr.getNext();
    }
    return res;
  }

  @Override
  public List<E> peekCollection(int n) {
    SinglyCircularLinkedList<E> list = new SinglyCircularLinkedList<>();
    E[] items = this.peekArray(n);
    for (int i = 0; i < items.length; i++) list.add(items[i]);
    return list;
  }

  @Override
  public List<E> peekLastCollection(int n) {
    SinglyCircularLinkedList<E> list = new SinglyCircularLinkedList<>();
    E[] items = this.peekLastArray(n);
    for (int i = 0; i < items.length; i++) list.add(items[i]);
    return list;
  }

  @Override
  public E poll() {
    if (this.isEmpty()) return null;
    E element = this.head.get();
    if (this.head == this.tail) {
      this.head = null;
      this.tail = null;
    } else {
      this.head = this.head.getNext();
      this.tail.setNext(this.head);
    }
    this.size--;
    return element;
  }

  @Override
  public E pollLast() {
    if (this.isEmpty()) return null;
    E element = this.tail.get();
    if (this.head == this.tail) {
      this.head = null;
      this.tail = null;
    } else {
      LinkedNode<E> curr = this.head;
      while (curr.getNext() != this.tail) curr = curr.getNext();
      this.tail = curr;
      this.tail.setNext(this.head);
    }
    this.size--;
    return element;
  }

  @Override
  public E[] pollArray(int n) {
    E[] data = this.peekArray(n);
    for (int i = 0; i < data.length; i++) this.poll();
    return data;
  }

  @Override
  public E[] pollLastArray(int n) {
    E[] data = this.peekLastArray(n);
    for (int i = 0; i < data.length; i++) this.pollLast();
    return data;
  }

  @Override
  public List<E> pollCollection(int n) {
    List<E> list = this.peekCollection(n);
    for (int i = 0; i < list.size(); i++) this.poll();
    return list;
  }

  @Override
  public List<E> pollLastCollection(int n) {
    List<E> list = this.peekLastCollection(n);
    for (int i = 0; i < list.size(); i++) this.pollLast();
    return list;
  }

  @Override
  public boolean remove(E element) {
    if (this.isEmpty()) return false;
    if (this.head.get().equals(element)) {
      this.poll();
      return true;
    }
    LinkedNode<E> curr = this.head;
    for (int i = 0; i < this.size - 1; i++) {
      if (curr.getNext().get().equals(element)) {
        if (curr.getNext() == this.tail) this.tail = curr;
        curr.setNext(curr.getNext().getNext());
        this.size--;
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
    for (int i = 0; i < array.length; i++) if (this.remove(array[i])) changed = true;
    return changed;
  }

  @Override
  public boolean remove(Collection<E> collection) {
    if (collection == null) return false;
    boolean changed = false;
    Iterator<E> it = collection.iterator();
    while (it.hasNext()) if (this.remove(it.next())) changed = true;
    return changed;
  }

  @Override
  public boolean remove(Predicate<E> filter) {
    boolean changed = false;
    int limit = this.size;
    for (int i = 0; i < limit; i++) {
      if (filter.test(this.head.get())) {
        this.poll();
        changed = true;
      } else {
        this.add(this.poll());
      }
    }
    return changed;
  }

  @Override
  public boolean replace(E element, E newElement, Predicate<E> comparator) {
    LinkedNode<E> curr = this.head;
    for (int i = 0; i < this.size; i++) {
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
    for (int i = 0; i < array.length; i++) if (this.replace(array[i], newArray[i], comparator)) changed = true;
    return changed;
  }

  @Override
  public boolean replace(Collection<E> c, Collection<E> nc, Predicate<E> comp) {
    if (c == null || nc == null) return false;
    Iterator<E> itO = c.iterator();
    Iterator<E> itN = nc.iterator();
    boolean changed = false;
    while (itO.hasNext() && itN.hasNext()) if (this.replace(itO.next(), itN.next(), comp)) changed = true;
    return changed;
  }

  @Override
  public boolean retain(E[] array) {
    if (array == null) return false;
    return this.remove(item -> {
      for (int i = 0; i < array.length; i++) if (array[i].equals(item)) return false;
      return true;
    });
  }

  @Override
  public boolean retain(Collection<E> collection) {
    if (collection == null) return false;
    return this.remove(item -> !collection.contains(item));
  }

  @Override
  public boolean set(E index, E element) {
    LinkedNode<E> curr = this.head;
    for (int i = 0; i < this.size; i++) {
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
    if (this.size < 2) return false;
    boolean changed = false;
    for (int i = 0; i < this.size; i++) {
      LinkedNode<E> curr = this.head;
      for (int j = 0; j < this.size - 1; j++) {
        if (toInt.applyAsInt(curr.get()) > toInt.applyAsInt(curr.getNext().get())) {
          E temp = curr.get();
          curr.set(curr.getNext().get());
          curr.getNext().set(temp);
          changed = true;
        }
        curr = curr.getNext();
      }
    }
    return changed;
  }

  @Override
  public List<E> subList(E from, E to) {
    SinglyCircularLinkedList<E> sub = new SinglyCircularLinkedList<>();
    LinkedNode<E> curr = this.head;
    boolean active = false;
    for (int i = 0; i < this.size; i++) {
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
    LinkedNode<E> curr = this.head;
    for (int i = 0; i < this.size; i++) {
      if (curr.get().equals(element)) return true;
      curr = curr.getNext();
    }
    return false;
  }

  @Override
  public boolean contains(E[] array) {
    if (array == null) return false;
    for (int i = 0; i < array.length; i++) if (!this.contains(array[i])) return false;
    return true;
  }

  @Override
  public boolean contains(Collection<E> collection) {
    if (collection == null) return false;
    Iterator<E> it = collection.iterator();
    while (it.hasNext()) if (!this.contains(it.next())) return false;
    return true;
  }

  @Override
  public boolean reverse() {
    if (this.size < 2) return false;
    LinkedNode<E> prev = this.tail;
    LinkedNode<E> curr = this.head;
    this.tail = this.head;
    for (int i = 0; i < this.size; i++) {
      LinkedNode<E> next = curr.getNext();
      curr.setNext(prev);
      prev = curr;
      curr = next;
    }
    this.head = prev;
    return true;
  }

  @Override
  public void forEach(Function<E, Void> action) {
    LinkedNode<E> curr = this.head;
    for (int i = 0; i < this.size; i++) {
      action.apply(curr.get());
      curr = curr.getNext();
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] toArray() {
    E[] array = (E[]) new Object[this.size];
    LinkedNode<E> curr = this.head;
    for (int i = 0; i < this.size; i++) {
      array[i] = curr.get();
      curr = curr.getNext();
    }
    return array;
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator<E>() {
      private LinkedNode<E> curr = head;
      private int count = 0;
      @Override public boolean hasNext() { return count < size; }
      @Override public E next() {
        E data = curr.get();
        curr = curr.getNext();
        count++;
        return data;
      }
    };
  }
}