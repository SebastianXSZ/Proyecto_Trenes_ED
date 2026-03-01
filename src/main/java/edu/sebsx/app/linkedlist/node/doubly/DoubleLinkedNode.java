package edu.sebsx.app.linkedlist.node.doubly;

import edu.sebsx.model.node.AbstractNode;

public class DoubleLinkedNode<E> extends AbstractNode<E> {

  private DoubleLinkedNode<E> next;
  private DoubleLinkedNode<E> previous;

  public DoubleLinkedNode() {
    super();
    this.next = null;
    this.previous = null;
  }

  public DoubleLinkedNode(E element) {
    super(element);
    this.next = null;
    this.previous = null;
  }

  public DoubleLinkedNode<E> getNext() {
    return this.next;
  }

  public void setNext(DoubleLinkedNode<E> next) {
    this.next = next;
  }

  public DoubleLinkedNode<E> getPrevious() {
    return this.previous;
  }

  public void setPrevious(DoubleLinkedNode<E> previous) {
    this.previous = previous;
  }

  @Override
  public String toString() {
    return "DoubleLinkedNode{" +
      "element=" + get().toString() +
      ", next=" + next +
      ", previous=" + previous +
	    '}';
  }
}