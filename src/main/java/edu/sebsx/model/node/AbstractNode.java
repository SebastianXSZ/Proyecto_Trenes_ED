package edu.sebsx.model.node;

/**
 * An abstract implementation of the Node interface.
 * Provides common functionality for nodes in a data structure.
 *
 * @param <E> the type of element stored in the node.
 * 
 * @author Lenin Javier Serrano Gil
 * @version 1.0.20231115
 */
public abstract class AbstractNode<E> implements Node<E>, Cloneable {

  protected E element;

  /**
   * Constructs an AbstractNode with a null element.
   */
  protected AbstractNode() {
    this.element = null;
  }

  /**
   * Constructs an AbstractNode with the specified element.
   *
   * @param element the element to be stored in the node.
   */
  protected AbstractNode(E element) {
    this.element = element;
  }

  @Override
  public void set(E element) {
    this.element = element;
  }

  @Override
  public E get() {
    return this.element;
  }
}
