package edu.sebsx.model.node;

/**
 * This interface represents a node in a data structure.
 * It provides methods to set and get the element in the node,
 * compare the element with another element and clone the node.
 *
 * @param <E> the type of element stored in the node.
 * 
 * @author Lenin Javier Serrano Gil
 * @version 1.0.20231115
 */
public interface Node<E> {
  /**
   * Sets an element in the node.
   * 
   * @param element the element to be set in the node.
   */
  void set(E element);

  /**
   * Gets the element contained in the node.
   * 
   * @return the element in the node or null.
   */
  E get();

  /**
   * Gets a string representation of the node.
   * 
   * @return a string representation of the node.
   */
  String toString();
}