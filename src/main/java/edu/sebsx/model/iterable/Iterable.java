package edu.sebsx.model.iterable;

import java.util.function.Function;

import edu.sebsx.model.iterator.Iterator;

/**
 * This interface represents an iterable object.
 * 
 * @param <E> the type of elements in the iterable object.
 * 
 * @author Lenin Javier Serrano Gil
 * @version 1.0.20231115
 */
public interface Iterable<E> {
  /**
   * For each element in the iterator, executes the specified action.
   *
   * @param action the action to be executed on each element.
   */
  void forEach(Function<E, Void> action);

  /**
   * Gets an iterator over the elements in the iterator.
   *
   * @return an iterator over the elements in the iterator.
   */
  Iterator<E> iterator();
}
