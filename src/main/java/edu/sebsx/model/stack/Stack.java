package edu.sebsx.model.stack;

/**
 * A stack is a data structure that allows you to add and remove values from the
 * top of the stack.
 * This is a LIFO (Last In First Out) data structure.
 * 
 * @param <E> the type of value that the stack will hold.
 * @author Lenin Javier Serrano Gil
 * @version 1.0.20240219
 */
public interface Stack<E> {
  /**
   * Allows you to retrieve the value at the top of a stack without actually
   * removing it.
   * 
   * @return the value at the top of the stack.
   */
  public E peek();

  /**
   * Removes the value at the top of the stack and returns it.
   * 
   * @return the value at the top of the stack.
   */
  public E pop();

  /**
   * Adds a value to the top of the stack.
   * 
   * @param element the value to add to the top of the stack.
   * @return true if the value was added to the stack, false otherwise.
   */
  public boolean push(E element);
}